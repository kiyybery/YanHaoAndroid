package com.yanhao.main.yanhaoandroid.login;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2015/10/28 0028.
 */
public class FrogetPwdActivity extends AppCompatActivity {

    private static final String TAG = FrogetPwdActivity.class.getSimpleName();
    android.support.v4.app.FragmentManager fm = getSupportFragmentManager();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(android.R.id.content, FrogetFragment.newInstance());
            ft.commit();
        }
    }
}
