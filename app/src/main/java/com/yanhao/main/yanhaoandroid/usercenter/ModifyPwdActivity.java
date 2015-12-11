package com.yanhao.main.yanhaoandroid.usercenter;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2015/11/24 0024.
 */
public class ModifyPwdActivity extends AppCompatActivity {

    FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {

            FragmentTransaction ft = fm.beginTransaction();
            ModifyPwdFragment mpf = ModifyPwdFragment.newInstance();
            ft.add(android.R.id.content, mpf);
            ft.commit();
        }
    }
}
