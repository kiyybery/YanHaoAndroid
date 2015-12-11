package com.yanhao.main.yanhaoandroid.serach;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by Administrator on 2015/12/1 0001.
 */
public class SerachActivity extends AppCompatActivity{

    FragmentManager fm = getSupportFragmentManager();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            FragmentTransaction ft = fm.beginTransaction();
            SerachFragment productionResultFrg = SerachFragment.newInstance();
            ft.add(android.R.id.content, productionResultFrg);
            ft.commit();
        }
    }
}
