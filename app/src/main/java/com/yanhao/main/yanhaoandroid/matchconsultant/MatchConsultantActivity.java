package com.yanhao.main.yanhaoandroid.matchconsultant;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.yanhao.main.yanhaoandroid.consult.ConsultListFragment;

/**
 * Created by Administrator on 2015/11/2 0002.
 */
public class MatchConsultantActivity extends AppCompatActivity{

    private static final String TAG = MatchConsultantActivity.class.getSimpleName();
    android.support.v4.app.FragmentManager fm = getSupportFragmentManager();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(android.R.id.content, MatchConstantFragment.newInstance());
            ft.commit();
        }
    }
}
