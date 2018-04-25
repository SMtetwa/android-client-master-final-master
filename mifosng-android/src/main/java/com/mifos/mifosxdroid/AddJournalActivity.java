package com.mifos.mifosxdroid;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mifos.api.BaseApiManager;
import com.mifos.api.MifosInterceptor;
import com.mifos.api.local.databasehelper.DatabaseHelperOffices;
import com.mifos.mifosxdroid.core.MifosBaseActivity;
import com.mifos.mifosxdroid.core.util.Toaster;
import com.mifos.objects.journal.Journal;
import com.mifos.objects.organisation.Office;
import com.mifos.utils.PrefManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * author   samsoftx
 * email    gwokudasam@gmail.com
 * date     4/21/18
 *
 * Copyright (c) Samsoftx - All Rights Reserved
 * Written by Samuel Gwokuda
 */

public class AddJournalActivity extends MifosBaseActivity
        implements DialogInterface.OnClickListener, DatePickerDialog.OnDateSetListener {

    @BindView(R.id.et_debit) EditText etDebit;
    @BindView(R.id.et_credit) EditText etCredit;
    @BindView(R.id.tv_set_date) TextView tv_setDate;
    @BindView(R.id.et_reference) EditText etReference;
    @BindView(R.id.et_comments) EditText etComments;
    @BindView(R.id.btn_add_journal) Button btnAddJournal;
    @BindView(R.id.sp_currency) Spinner spCurrency;
    @BindView(R.id.sp_office_list) Spinner spOffices;

    @BindView(R.id.sp_select_debit) Spinner spDebit;
    @BindView(R.id.sp_select_credit) Spinner spCredit;
    @Inject
    DatabaseHelperOffices databaseHelperOffices = new DatabaseHelperOffices();

    private ArrayAdapter<String> officeAdapter;
    private ArrayList<String> officeNameList;

    private BaseApiManager baseApiManager = new BaseApiManager();

    static OkHttpClient client;
    MediaType JSON;

    static String dateSet = "";
    static Calendar calendar = Calendar.getInstance();
    private static final String TAG = AddJournalActivity.class.getSimpleName();

    public AddJournalActivity() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_journal);
        showBackButton();
        setToolbarTitle("Add Journal");
        ButterKnife.bind(this);

        final String[] offices={"Head Office","Rusape","Marondera"};
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, offices);
       /* ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, str);*/
        spinnerArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spOffices.setAdapter(spinnerArrayAdapter);

        spOffices.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "Selected item " + position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public static class GetTask extends AsyncTask {

        private Exception exception;
        @Override
        protected String doInBackground(Object[] objects) {
            Journal journal = new Journal();
            journal.setOfficeId(1);
            journal.setLocale("en");
            journal.setTransactionDate("");
            journal.setReferenceNumber("");
            journal.setCurrencyCode("");
            journal.setComments("");
            journal.setAccountingRule(2);
            journal.setAmount(Double.valueOf("0.00"));
            //
            try {
                String getResponse = getOffices("https://demo2.mifosx.net/fineract-provider/api/v1/offices?paged=true&offset=0&limit=100");
                Log.d(TAG, "");
                return getResponse;
            } catch (Exception e) {
                this.exception = e;
                return null;
            }
        }

        protected void onPostExecute(String getResponse) {
            System.out.println(getResponse);
        }

        public String getOffices(final String url) throws IOException {

            Request request = new Request.Builder()
                    .addHeader(MifosInterceptor.HEADER_TENANT, PrefManager.getTenant())
                    .addHeader(MifosInterceptor.HEADER_AUTH, PrefManager.getToken())
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();
        }
    }


    @OnClick(R.id.btn_select_date)
    public void selectDate(){
        DatePickerDialog dialog = new DatePickerDialog(this,
                R.style.MaterialDatePickerTheme,
                this, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        dialog.getDatePicker().setMaxDate(new Date().getTime());
        dialog.show();
    }

    @OnClick(R.id.btn_add_journal)
    public void submit(){
        GetTask task = new GetTask();
        task.execute();

       /* if (validate()){
            Toaster.show(this.getCurrentFocus(), "Adding Journal");
        }*/
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        Log.d("TAG", "Dialog {}" +  dialog.toString());
    }

    private boolean validate(){
       return validateOfficeSpinner() && validateDate() && validateDebitCreditSpinner() && validateCreditValue() && validateDebitValue();
    }

    private boolean validateDebitValue(){
        return true;
    }
    private boolean validateCreditValue(){
        return true;
    }

    private boolean validateDebitCreditSpinner(){
        if ((spDebit.getSelectedItemPosition() == -1) && (spCredit.getSelectedItemPosition() == -1)){
            Toaster.show(tv_setDate, "Please Select a Debit or Credit");
            return false;
        }
        return true;
    }

    private boolean validateDate(){
        return !dateSet.matches("");
    }

    private boolean validateOfficeSpinner(){
        if (spOffices.getSelectedItemPosition() == -1){
            Toaster.show(tv_setDate, "Please Select an Office");
            return false;
        }
        return true;
    }

    private boolean validateCurrencySpinner(){
        if (spCurrency.getSelectedItemPosition() == -1){
            Toaster.show(tv_setDate, "Please Select a Currency");
            return false;
        }
        return true;
    }

    private boolean validateReference(){
        return true;
    }

    private boolean validateComments(){
        return true;
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        dateSet = String.valueOf(year) + " " + String.valueOf(month) + " " + String.valueOf(dayOfMonth);
        Log.d("TAG: Date picked {}", dateSet);

        tv_setDate.setVisibility(View.VISIBLE);
        tv_setDate.setText(dateSet);
    }
}
