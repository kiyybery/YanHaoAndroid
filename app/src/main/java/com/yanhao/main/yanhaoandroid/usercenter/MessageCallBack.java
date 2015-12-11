package com.yanhao.main.yanhaoandroid.usercenter;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.yanhao.main.yanhaoandroid.consult.ConsultListFragment;

/**
 * Created by Administrator on 2015/11/25 0025.
 */
public class MessageCallBack extends AppCompatActivity {

    FragmentManager fm = getSupportFragmentManager();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            MessageCallBackFragment productionResultFrg = MessageCallBackFragment.newInstance();
            ft.add(android.R.id.content, productionResultFrg);
            ft.commit();
        }
    }
}
