package com.yanhao.main.yanhaoandroid.homepage;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.yanhao.main.yanhaoandroid.util.LogUtil;


/**
 * Created by Administrator on 2015/12/1 0001.
 */
public class HomePageActivity extends AppCompatActivity{

    private String userId;
    FragmentManager fm = getSupportFragmentManager();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {

            userId = getIntent().getStringExtra("userId");
            Log.i("consultorid",userId);
            FragmentTransaction ft = fm.beginTransaction();
            HomePageFragment productionResultFrg = HomePageFragment.newInstance();
            Bundle bundle = new Bundle();
            bundle.putString("userId", userId);
            productionResultFrg.setArguments(bundle);
            ft.add(android.R.id.content, productionResultFrg);
            ft.commit();
        }
    }
}
