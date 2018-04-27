package com.mifos.mifosxdroid.online.createjournal;

import com.mifos.api.DataManager;
import com.mifos.mifosxdroid.base.BasePresenter;

import javax.inject.Inject;

import rx.Subscription;

/**
 * author   samsoftx
 * email    gwokudasam@gmail.com
 * date     4/26/18
 * <p>
 * Copyright (c) Samsoftx - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
public class AddJournalPresenter extends BasePresenter<AddJournalMvpView> {

    private DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public AddJournalPresenter(DataManager mDataManager, Subscription mSubscription) {
        this.mDataManager = mDataManager;
        this.mSubscription = mSubscription;
    }
}
