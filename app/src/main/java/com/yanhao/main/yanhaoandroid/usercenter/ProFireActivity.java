package com.yanhao.main.yanhaoandroid.usercenter;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2015/11/12 0012.
 */
public class ProFireActivity extends AppCompatActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null){

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ProFireFrag productionResultFrg = ProFireFrag.newInstance();
            ft.add(android.R.id.content, productionResultFrg);
            ft.commit();
        }

    }
}
