package com.yanhao.main.yanhaoandroid.usercenter;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.yanhao.main.yanhaoandroid.util.SecurityUtil;

import java.net.URLEncoder;

/**
 * Created by Administrator on 2015/11/11 0011.
 */
public class PersonalOrderActivity extends AppCompatActivity {

    private String consultTypeName, userName, reservationTime, portraitUrl, issue, mobile, note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userName = getIntent().getStringExtra("userName");
        consultTypeName = getIntent().getStringExtra("consultTypeName");
        reservationTime = getIntent().getStringExtra("reservationTime");
        portraitUrl = getIntent().getStringExtra("portraitUrl");
        issue = getIntent().getStringExtra("issue");
        mobile = getIntent().getStringExtra("mobile");
        note = getIntent().getStringExtra("note");

        if (savedInstanceState == null) {

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            PersonalOrderFragment productionResultFrg = PersonalOrderFragment.newInstance();
            Bundle bundle = new Bundle();
            bundle.putString("userName", userName);
            bundle.putString("consultTypeName", consultTypeName);
            bundle.putString("reservationTime", reservationTime);
            bundle.putString("portraitUrl", portraitUrl);
            bundle.putString("issue", issue);
            bundle.putString("mobile", mobile);
            bundle.putString("note", note);
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
