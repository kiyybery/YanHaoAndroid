package com.yanhao.main.yanhaoandroid.homepage;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by Administrator on 2015/12/1 0001.
 */
public class PayPageActivity extends AppCompatActivity {

    FragmentManager fm = getSupportFragmentManager();
    private int reservationId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            reservationId = getIntent().getIntExtra("reservationId", 0);
            FragmentTransaction ft = fm.beginTransaction();
            PayPageFragment productionResultFrg = PayPageFragment.newInstance();
            Bundle bundle = new Bundle();
            bundle.putInt("reservationId", reservationId);
            productionResultFrg.setArguments(bundle);
            ft.add(android.R.id.content, productionResultFrg);
            ft.commit();
        }
    }
}
