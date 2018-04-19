package com.mifos.mifosxdroid.online;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.mifos.mifosxdroid.ClientListActivity;
import com.mifos.mifosxdroid.R;
import com.mifos.mifosxdroid.core.MifosBaseFragment;
import com.mifos.mifosxdroid.core.RecyclerItemClickListener;
import com.mifos.mifosxdroid.online.clientlist.ClientListFragment;
import com.mifos.utils.Constants;

import butterknife.BindView;
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

   @OnClick({R.id.btn_clients, R.id.btn_groups, R.id.btn_loans, R.id.btn_collections, R.id.btn_accounting, R.id.btn_reports})
    public void click(View v){
       Intent intent = new Intent(getActivity(), DashboardActivity.class);
        switch (v.getId()){
            case R.id.btn_clients:
                Toast.makeText(getContext(), "Clients!", Toast.LENGTH_SHORT).show();
                intent.putExtra("VALUE", Constants.CLIENTS);
                break;
            case R.id.btn_groups:
                intent.putExtra("VALUE", Constants.GROUPS);
                break;
            case R.id.btn_loans:
                intent.putExtra("VALUE", Constants.LOANS);
                break;
            case R.id.btn_collections:
                intent.putExtra("VALUE", Constants.COLLECTIONS);
                break;
            case R.id.btn_accounting:
                intent.putExtra("VALUE", Constants.ACCOUNTING);
                break;
            case R.id.btn_reports:
                intent.putExtra("VALUE", Constants.REPORTS);
                break;
        }
       startActivity(intent);
    }

}