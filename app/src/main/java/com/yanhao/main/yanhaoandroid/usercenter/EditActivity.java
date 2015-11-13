package com.yanhao.main.yanhaoandroid.usercenter;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.yanhao.main.yanhaoandroid.matchconsultant.MatchConstantFragment;

/**
 * Created by Administrator on 2015/11/9 0009.
 */
public class EditActivity extends AppCompatActivity{

    public String name;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        name = getIntent().getStringExtra("username");
        Toast.makeText(this, name+"fragment say", Toast.LENGTH_LONG).show();
        if(savedInstanceState == null){
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            EditFragment productionResultFrg = EditFragment.newInstance();
            Bundle bundle = new Bundle();
            bundle.putString("username",name);
            productionResultFrg.setArguments(bundle);
            ft.add(android.R.id.content, productionResultFrg);
            ft.commit();

            /*FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            EditPersonalFragment productionResultFrg = EditPersonalFragment.newInstance();
            ft.add(android.R.id.content, productionResultFrg);
            ft.commit();*/
        }
    }
}
