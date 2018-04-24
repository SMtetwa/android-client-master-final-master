package com.mifos.objects.journal;

import android.os.Parcel;
import android.os.Parcelable;

import com.mifos.api.local.MifosBaseModel;
import com.mifos.api.local.MifosDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * author   samsoftx
 * email    gwokudasam@gmail.com
 * date     4/24/18
 * <p>
 * Copyright (c) Samsoftx - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
@Table(database = MifosDatabase.class)
@ModelContainer
public class Journal extends MifosBaseModel implements Parcelable {
    @PrimaryKey
    private int id;

    @Column
    private int officeId;

    @Column
    private String transactionDate;

    @Column
    private String comments;

    @Column
    private String locale;

    @Column
    private String currencyCode;

    @Column
    private String dateFormat;

    @Column
    private Integer paymentTypeId;

    @Column
    private String accountNumber;

    @Column
    private String checkNumber;

    @Column
    private String routingCode;

    @Column
    private String receiptNumber;

    @Column
    private String bankNumber;

    @Column
    private Integer accountingRule;

    @Column
    private String referenceNumber;

    @Column
    private Double amount;

    private List<TransactionAccount> credits = new ArrayList<>();

    private List<TransactionAccount> debits = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOfficeId() {
        return officeId;
    }

    public void setOfficeId(int officeId) {
        this.officeId = officeId;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public Integer getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setPaymentTypeId(Integer paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    public String getRoutingCode() {
        return routingCode;
    }

    public void setRoutingCode(String routingCode) {
        this.routingCode = routingCode;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public List<TransactionAccount> getCredits() {
        return credits;
    }

    public void setCredits(List<TransactionAccount> credits) {
        this.credits = credits;
    }

    public List<TransactionAccount> getDebits() {
        return debits;
    }

    public void setDebits(List<TransactionAccount> debits) {
        this.debits = debits;
    }

    public Integer getAccountingRule() {
        return accountingRule;
    }

    public void setAccountingRule(Integer accountingRule) {
        this.accountingRule = accountingRule;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public static Creator<Journal> getCREATOR() {
        return CREATOR;
    }

    public Journal(int id, int officeId, String transactionDate, String comments, String locale, String currencyCode, String dateFormat, Integer paymentTypeId, String accountNumber, String checkNumber, String routingCode, String receiptNumber, String bankNumber, List<TransactionAccount> credits, List<TransactionAccount> debits, Integer accountingRule, String referenceNumber, Double amount) {
        this.id = id;
        this.officeId = officeId;
        this.transactionDate = transactionDate;
        this.comments = comments;
        this.locale = locale;
        this.currencyCode = currencyCode;
        this.dateFormat = dateFormat;
        this.paymentTypeId = paymentTypeId;
        this.accountNumber = accountNumber;
        this.checkNumber = checkNumber;
        this.routingCode = routingCode;
        this.receiptNumber = receiptNumber;
        this.bankNumber = bankNumber;
        this.credits = credits;
        this.debits = debits;
        this.accountingRule = accountingRule;
        this.referenceNumber = referenceNumber;
        this.amount = amount;
    }

    public Journal() {
    }

    protected Journal(Parcel in) {
        id = in.readInt();
        officeId = in.readInt();
        transactionDate = in.readString();
        comments = in.readString();
        locale = in.readString();
        currencyCode = in.readString();
        dateFormat = in.readString();
        if (in.readByte() == 0) {
            paymentTypeId = null;
        } else {
            paymentTypeId = in.readInt();
        }
        accountNumber = in.readString();
        checkNumber = in.readString();
        routingCode = in.readString();
        receiptNumber = in.readString();
        bankNumber = in.readString();
        accountingRule = in.readInt();
        referenceNumber = in.readString();
        amount = in.readDouble();
    }

    public static final Creator<Journal> CREATOR = new Creator<Journal>() {
        @Override
        public Journal createFromParcel(Parcel in) {
            return new Journal(in);
        }

        @Override
        public Journal[] newArray(int size) {
            return new Journal[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.officeId);
        dest.writeString(this.transactionDate);
        dest.writeString(this.comments);
        dest.writeString(this.locale);
        dest.writeString(this.currencyCode);
        dest.writeString(this.dateFormat);
        dest.writeInt(this.paymentTypeId);
        dest.writeString(this.accountNumber);
        dest.writeString(this.checkNumber);
        dest.writeString(this.routingCode);
        dest.writeString(this.receiptNumber);
        dest.writeString(this.bankNumber);
        dest.writeList(this.credits);
        dest.writeList(this.debits);
        dest.writeInt(this.accountingRule);
        dest.writeString(this.referenceNumber);
        dest.writeDouble(this.amount);
    }
}