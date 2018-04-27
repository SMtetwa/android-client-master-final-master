package com.mifos.objects.journal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * author   samsoftx
 * email    gwokudasam@gmail.com
 * date     4/24/18
 *
 * Copyright (c) Samsoftx - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

public class TransactionAccount implements Parcelable {

    private Integer glAccountId;

    @SerializedName("amount")
    private Double amount;

    public Integer getGlAccountId() {
        return glAccountId;
    }

    public void setGlAccountId(Integer glAccountId) {
        this.glAccountId = glAccountId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public TransactionAccount() {
    }

    protected TransactionAccount(Parcel in) {
        if (in.readByte() == 0) {
            glAccountId = null;
        } else {
            glAccountId = in.readInt();
        }
        if (in.readByte() == 0) {
            amount = null;
        } else {
            amount = in.readDouble();
        }
    }

    public static final Creator<TransactionAccount> CREATOR = new Creator<TransactionAccount>() {
        @Override
        public TransactionAccount createFromParcel(Parcel in) {
            return new TransactionAccount(in);
        }

        @Override
        public TransactionAccount[] newArray(int size) {
            return new TransactionAccount[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (glAccountId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(glAccountId);
        }
        if (amount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(amount);
        }
    }
    /**
     *  "debits":[{"glAccountId":3,
     "amount":5000},
     {"glAccountId":4,
     "amount":5000}
     * */
}
