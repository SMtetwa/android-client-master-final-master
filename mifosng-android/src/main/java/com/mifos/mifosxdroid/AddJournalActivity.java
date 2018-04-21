package com.mifos.mifosxdroid;

import android.os.Bundle;

import com.mifos.mifosxdroid.core.MifosBaseActivity;
import com.mifos.mifosxdroid.online.groupslist.GroupsListFragment;

/**
 * author   samsoftx
 * email    gwokudasam@gmail.com
 * date     4/21/18
 * <p>
 * Copyright (c) Samsoftx - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

public class AddJournalActivity extends MifosBaseActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_journal);
        showBackButton();

        //replaceFragment(GroupsListFragment.newInstance(), false, R.id.container);
    }

}
