package com.yanhao.main.yanhaoandroid.usercenter;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2015/11/9 0009.
 */
public class OrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            OrderFragment productionResultFrg = OrderFragment.newInstance();
            ft.add(android.R.id.content, productionResultFrg);
            ft.commit();

            /*FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            OrderPersonalFragment productionResultFrg = OrderPersonalFragment.newInstance();
            ft.add(android.R.id.content, productionResultFrg);
            ft.commit();*/
        }
    }
}
