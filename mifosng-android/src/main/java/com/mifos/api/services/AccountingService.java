package com.mifos.api.services;

import com.mifos.api.GenericResponse;
import com.mifos.api.model.APIEndPoint;
import com.mifos.objects.client.ActivatePayload;
import com.mifos.objects.journal.Journal;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * author   samsoftx
 * email    gwokudasam@gmail.com
 * date     4/26/18
 *
 * Copyright (c) Samsoftx - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
public interface AccountingService {

    @POST(APIEndPoint.ACCOUNTING_JOURNAL)
    Observable<GenericResponse> postJournal(@Body Journal journal);
}
