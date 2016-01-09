package com.yanhao.main.yanhaoandroid.matchconsultant;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by Administrator on 2015/12/1 0001.
 */
public class OrderContantActivity extends AppCompatActivity{

    FragmentManager fm = getSupportFragmentManager();

    String userId;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {

            userId = getIntent().getStringExtra("userId");
            FragmentTransaction ft = fm.beginTransaction();
            OrderContantFragment productionResultFrg = OrderContantFragment.newInstance();
            Bundle bundle = new Bundle();
            bundle.putString("userId",userId);
            productionResultFrg.setArguments(bundle);
            ft.add(android.R.id.content, productionResultFrg);
            ft.commit();
        }
    }
}
