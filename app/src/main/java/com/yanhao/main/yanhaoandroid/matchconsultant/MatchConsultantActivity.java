package com.yanhao.main.yanhaoandroid.matchconsultant;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.yanhao.main.yanhaoandroid.consult.ConsultListFragment;

/**
 * Created by Administrator on 2015/11/2 0002.
 */
public class MatchConsultantActivity extends AppCompatActivity {

    public String titleName;
    public String itemName;
    private static final String TAG = MatchConsultantActivity.class.getSimpleName();
    android.support.v4.app.FragmentManager fm = getSupportFragmentManager();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        titleName = getIntent().getStringExtra("titlename");
        itemName = getIntent().getStringExtra("item");
        if (savedInstanceState == null) {
            /*FragmentTransaction ft = fm.beginTransaction();
            ft.add(android.R.id.content, MatchConstantFragment.newInstance());
            ft.commit();*/

            //Toast.makeText(this, titleName + itemName, Toast.LENGTH_LONG).show();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            MatchConstantFragment productionResultFrg = MatchConstantFragment.newInstance();
            Bundle bundle = new Bundle();
            bundle.putString("titlename", titleName);
            bundle.putString("item", itemName);
            productionResultFrg.setArguments(bundle);
            ft.add(android.R.id.content, productionResultFrg);
            ft.commit();
        }
    }
}
