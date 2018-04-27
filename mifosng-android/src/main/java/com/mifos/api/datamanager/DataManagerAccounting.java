package com.mifos.api.datamanager;

import com.mifos.api.BaseApiManager;
import com.mifos.api.local.databasehelper.DatabaseHelperAccounting;
import com.mifos.api.local.databasehelper.DatabaseHelperNote;
import com.mifos.objects.client.Client;
import com.mifos.objects.client.ClientDate;
import com.mifos.objects.journal.Journal;
import com.mifos.objects.noncore.Note;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Func0;

/**
 * This DataManager is for Managing Notes API, In which Request is going to Server
 * and In Response, We are getting Notes API Observable Response using Retrofit2
 * Created by rahul on 4/3/17.
 */
@Singleton
public class DataManagerAccounting {

    public final BaseApiManager mBaseApiManager;
    public final DatabaseHelperAccounting mDatabaseHelperAccounting;

    @Inject
    public DataManagerAccounting(BaseApiManager baseApiManager,
                                 DatabaseHelperAccounting databaseHelperAccounting) {
        mBaseApiManager = baseApiManager;
        mDatabaseHelperAccounting = databaseHelperAccounting;
    }


    public Observable<Journal> saveJournal(final Journal journal) {
        return Observable.defer(new Func0<Observable<Journal>>() {
            @Override
            public Observable<Journal> call() {
                //Saving Journal in Database
                journal.save();
                return Observable.just(journal);
            }
        });
    }

}
