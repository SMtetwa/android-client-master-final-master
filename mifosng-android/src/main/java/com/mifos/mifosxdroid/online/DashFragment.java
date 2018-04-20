package com.mifos.mifosxdroid.online;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mifos.mifosxdroid.R;
import com.mifos.utils.Constants;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author   samsoftx
 * email    gwokudasam@gmail.com
 * date     4/19/18
 * <p>
 * Copyright (c) Samsoftx - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
public class DashFragment extends Fragment{

    public DashFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dash, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

   @OnClick({R.id.btn_clients, R.id.btn_groups, R.id.btn_create_client, R.id.btn_collections, R.id.btn_accounting})
    public void click(View v){
       Intent intent = new Intent(getActivity(), DashboardActivity.class);
        switch (v.getId()){
            case R.id.btn_clients:
                intent.putExtra("VALUE", Constants.CLIENTS);
                break;
            case R.id.btn_groups:
                intent.putExtra("VALUE", Constants.GROUPS);
                break;
            case R.id.btn_create_client:
                intent.putExtra("VALUE", Constants.CREATE_CLIENTS);
                break;
            case R.id.btn_collections:
                intent.putExtra("VALUE", Constants.COLLECTIONS);
                break;
            case R.id.btn_accounting:
                intent.putExtra("VALUE", Constants.ACCOUNTING);
                break;
        }
       startActivity(intent);
    }

}