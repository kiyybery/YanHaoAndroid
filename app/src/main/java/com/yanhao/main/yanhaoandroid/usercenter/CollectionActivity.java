package com.yanhao.main.yanhaoandroid.usercenter;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2016/1/26 0026.
 */
public class CollectionActivity extends AppCompatActivity {
    FragmentManager fm = getSupportFragmentManager();
    String type;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            type = getIntent().getStringExtra("type");
            if (type.equals("1")) {

                FragmentTransaction ft = fm.beginTransaction();
                MyTestFragment productionResultFrg = MyTestFragment.newInstance();
                ft.add(android.R.id.content, productionResultFrg);
                ft.commit();
            } else {

                FragmentTransaction ft = fm.beginTransaction();
                CollectionFragment productionResultFrg = CollectionFragment.newInstance();
                ft.add(android.R.id.content, productionResultFrg);
                ft.commit();
            }

        }
    }

}
