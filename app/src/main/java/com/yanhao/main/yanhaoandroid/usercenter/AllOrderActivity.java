package com.yanhao.main.yanhaoandroid.usercenter;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2015/11/11 0011.
 */
public class AllOrderActivity extends AppCompatActivity {

    private String consultTypeName, counselorName, reservationTime, portraitUrl, address, serviceTel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        counselorName = getIntent().getStringExtra("counselorName");
        consultTypeName = getIntent().getStringExtra("consultTypeName");
        reservationTime = getIntent().getStringExtra("reservationTime");
        portraitUrl = getIntent().getStringExtra("portraitUrl");
        address = getIntent().getStringExtra("address");
        serviceTel = getIntent().getStringExtra("servicetel");

        if (savedInstanceState == null) {

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ConsultantOrderFragment productionResultFrg = ConsultantOrderFragment.newInstance();
            Bundle bundle = new Bundle();
            bundle.putString("counselorName", counselorName);
            bundle.putString("consultTypeName", consultTypeName);
            bundle.putString("reservationTime", reservationTime);
            bundle.putString("portraitUrl", portraitUrl);
            bundle.putString("address", address);
            bundle.putString("servicetel", serviceTel);
            productionResultFrg.setArguments(bundle);
            ft.add(android.R.id.content, productionResultFrg);
            ft.commit();

            /*FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            EditPersonalFragment productionResultFrg = EditPersonalFragment.newInstance();
            ft.add(android.R.id.content, productionResultFrg);
            ft.commit();*/
        }
    }
}
