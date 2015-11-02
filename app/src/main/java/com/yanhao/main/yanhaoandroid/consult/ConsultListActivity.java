package com.yanhao.main.yanhaoandroid.consult;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by Administrator on 2015/11/2 0002.
 */
public class ConsultListActivity extends AppCompatActivity{


    private static final String TAG = ConsultListActivity.class.getSimpleName();
    android.support.v4.app.FragmentManager fm = getSupportFragmentManager();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(android.R.id.content, ConsultListFragment.newInstance());
            ft.commit();
        }
    }

}
