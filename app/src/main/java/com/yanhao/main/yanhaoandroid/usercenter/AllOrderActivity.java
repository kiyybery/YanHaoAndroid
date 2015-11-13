package com.yanhao.main.yanhaoandroid.usercenter;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2015/11/11 0011.
 */
public class AllOrderActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState == null){
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            PersonalOrderFragment productionResultFrg = PersonalOrderFragment.newInstance();
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
