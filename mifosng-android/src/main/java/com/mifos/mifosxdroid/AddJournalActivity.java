package com.mifos.mifosxdroid;

import android.os.Bundle;

import com.mifos.mifosxdroid.core.MifosBaseActivity;
import com.mifos.mifosxdroid.online.groupslist.GroupsListFragment;

/**
 * author   samsoftx
 * email    gwokudasam@gmail.com
 * date     4/21/18
 *
 * Copyright (c) Samsoftx - All Rights Reserved
 * Written by Samuel Gwokuda
 */

public class AddJournalActivity extends MifosBaseActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_journal);
        showBackButton();

        //replaceFragment(GroupsListFragment.newInstance(), false, R.id.container);
    }

}
