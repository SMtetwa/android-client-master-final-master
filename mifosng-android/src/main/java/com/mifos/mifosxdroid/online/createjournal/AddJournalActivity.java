package com.mifos.mifosxdroid.online.createjournal;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mifos.api.BaseApiManager;
import com.mifos.api.DataManager;
import com.mifos.api.GenericResponse;
import com.mifos.api.MifosInterceptor;
import com.mifos.api.local.databasehelper.DatabaseHelperOffices;
import com.mifos.mifosxdroid.R;
import com.mifos.mifosxdroid.core.util.Toaster;
import com.mifos.mifosxdroid.online.clientdetails.ClientDetailsPresenter;
import com.mifos.mifosxdroid.online.savingsaccount.SavingsAccountPresenter;
import com.mifos.objects.accounts.loan.LoanWithAssociations;
import com.mifos.objects.journal.Journal;
import com.mifos.objects.journal.TransactionAccount;
import com.mifos.utils.PrefManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Observable;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * author   samsoftx
 * email    gwokudasam@gmail.com
 * date     4/21/18
 *
 * Copyright (c) Samsoftx - All Rights Reserved
 * Written by Samuel Gwokuda
 */

public class AddJournalActivity extends AppCompatActivity
        implements DialogInterface.OnClickListener, DatePickerDialog.OnDateSetListener, AddJournalMvpView {

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

/*    Subscription mSubscription = new CompositeSubscription();
    BaseApiManager mDataManager = new BaseApiManager();*/

    @Inject
    SavingsAccountPresenter mSavingsAccountPresenter;

    DatabaseHelperOffices databaseHelperOffices = new DatabaseHelperOffices();
    private ArrayAdapter<String> officeAdapter;
    private ArrayList<String> officeNameList;

    static OkHttpClient client;

    static String dateSet = "";
    static Calendar calendar = Calendar.getInstance();
    private static final String TAG = AddJournalActivity.class.getSimpleName();

    public AddJournalActivity() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_journal);
        ButterKnife.bind(this);

        final String[] offices={"Head Office","Rusape","Marondera"};
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, offices);
        spinnerArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spOffices.setAdapter(spinnerArrayAdapter);

        spDebit.setAdapter(spinnerArrayAdapter);
        spCredit.setAdapter(spinnerArrayAdapter);
        spCurrency.setAdapter(spinnerArrayAdapter);


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

    @Override
    public void showProgressbar(boolean b) {

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
            List<TransactionAccount> creditsList = new ArrayList<>();
            TransactionAccount t1Credit = new TransactionAccount();
            t1Credit.setGlAccountId(1);
            t1Credit.setAmount(new Double("2.00"));

            TransactionAccount t2Credit = new TransactionAccount();
            t2Credit.setGlAccountId(2);
            t2Credit.setAmount(new Double("2.00"));

            creditsList.add(t1Credit);
            creditsList.add(t2Credit);
            journal.setCredits(creditsList);

            List<TransactionAccount> debitsList = new ArrayList<>();
            journal.setDebits(debitsList);
            TransactionAccount t1Debit = new TransactionAccount();
            t1Debit.setGlAccountId(3);
            t1Debit.setAmount(new Double("2.00"));

            TransactionAccount t2Debit = new TransactionAccount();
            t2Debit.setGlAccountId(4);
            t2Debit.setAmount(new Double("2.00"));

            creditsList.add(t1Credit);
            creditsList.add(t2Credit);

            debitsList.add(t1Debit);
            debitsList.add(t2Debit);

            journal.setCredits(creditsList);
            journal.setDebits(debitsList);


            journal.setPaymentTypeId(2);
            Log.d("JSON {}", new Gson().toJson(journal));

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
        Journal journal = new Journal();
        journal.setOfficeId(1);
        journal.setLocale("en");
        journal.setTransactionDate("");
        journal.setReferenceNumber("");
        journal.setCurrencyCode("");
        journal.setComments("test from app");
        journal.setAccountingRule(2);
        journal.setAmount(Double.valueOf("0.00"));
        List<TransactionAccount> creditsList = new ArrayList<>();
        TransactionAccount t1Credit = new TransactionAccount();
        t1Credit.setGlAccountId(1);
        t1Credit.setAmount(Double.valueOf("2.00"));

        TransactionAccount t2Credit = new TransactionAccount();
        t2Credit.setGlAccountId(2);
        t2Credit.setAmount(Double.valueOf("2.00"));

        creditsList.add(t1Credit);
        creditsList.add(t2Credit);
        journal.setCredits(creditsList);

        List<TransactionAccount> debitsList = new ArrayList<>();
        journal.setDebits(debitsList);
        TransactionAccount t1Debit = new TransactionAccount();
        t1Debit.setGlAccountId(3);
        t1Debit.setAmount(new Double("2.00"));

        TransactionAccount t2Debit = new TransactionAccount();
        t2Debit.setGlAccountId(4);
        t2Debit.setAmount(new Double("2.00"));

        creditsList.add(t1Credit);
        creditsList.add(t2Credit);

        debitsList.add(t1Debit);
        debitsList.add(t2Debit);

        journal.setCredits(creditsList);
        journal.setDebits(debitsList);


        journal.setPaymentTypeId(2);
        Log.d("JSON {}", new Gson().toJson(journal));

        BaseApiManager api = new BaseApiManager();

        rx.Observable<GenericResponse> responseObservable = api.getAccountingApi().postJournal(journal);
        Subscription disposable = responseObservable
                .subscribeOn(Schedulers.io()) // optional if you do not wish to override the default behavior
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GenericResponse>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("done");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toaster.show(btnAddJournal, "error " + e.getMessage());
                        //System.out.println("error " + e.getMessage());
                    }

                    @Override
                    public void onNext(GenericResponse genericResponse) {
                        System.out.println("ext");
                    }
                });
        //disposable.unsubscribe();
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
