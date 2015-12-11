package com.yanhao.main.yanhaoandroid.usercenter;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2015/11/24 0024.
 */
public class ModifyOKActivity extends AppCompatActivity {

    private String titleName;
    private String content;
    private String info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        titleName = getIntent().getStringExtra("titleName");
        content = getIntent().getStringExtra("content");
        info = getIntent().getStringExtra("info");
        if (savedInstanceState == null) {

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ModifyIsOK productionResultFrg = ModifyIsOK.newInstance();
            Bundle bundle = new Bundle();
            bundle.putString("titleName", titleName);
            bundle.putString("info", info);
            bundle.putString("content", content);
            productionResultFrg.setArguments(bundle);
            ft.add(android.R.id.content, productionResultFrg);
            ft.commit();
        }
    }
}
