package com.yanhao.main.yanhaoandroid.usercenter;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Administrator on 2015/11/10 0010.
 */
public class UpdateProfireActivity extends AppCompatActivity{


    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        type = getIntent().getStringExtra("type");

        if(type.equals("1")){
            //Toast.makeText(UpdateProfireActivity.this,"type="+type,Toast.LENGTH_LONG).show();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            UpdateNameFragment productionResultFrg = UpdateNameFragment.newInstance();
            ft.add(android.R.id.content, productionResultFrg);
            ft.commit();
        }else {
            //Toast.makeText(UpdateProfireActivity.this,"type="+type,Toast.LENGTH_LONG).show();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            UpdateSexFragment productionResultFrg = UpdateSexFragment.newInstance();
            ft.add(android.R.id.content, productionResultFrg);
            ft.commit();
        }
    }
}
