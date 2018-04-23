package com.mifos.mifosxdroid;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.mifos.mifosxdroid.core.MifosBaseActivity;
import com.mifos.mifosxdroid.core.util.Toaster;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @BindView(R.id.sp_office_list) Spinner spOffice;

    @BindView(R.id.sp_select_debit) Spinner spDebit;
    @BindView(R.id.sp_select_credit) Spinner spCredit;

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
        if (validate()){
            Toaster.show(this.getCurrentFocus(), "Adding Journal");
        }
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
        if (spOffice.getSelectedItemPosition() == -1){
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
