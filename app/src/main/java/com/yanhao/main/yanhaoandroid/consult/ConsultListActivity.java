package com.yanhao.main.yanhaoandroid.consult;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


/**
 * Created by Administrator on 2015/11/2 0002.
 */
public class ConsultListActivity extends AppCompatActivity{

    public int mItemId;
    public String mItemName;

    private static final String TAG = ConsultListActivity.class.getSimpleName();
    android.support.v4.app.FragmentManager fm = getSupportFragmentManager();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mItemId = getIntent().getIntExtra("itemId",1);
        mItemName = getIntent().getStringExtra("itemName");

        Toast.makeText(this,"adapter tell activity :"+mItemId+","+mItemName,Toast.LENGTH_LONG).show();
        if (savedInstanceState == null) {
            /*FragmentTransaction ft = fm.beginTransaction();
            Bundle bundle = new Bundle();
            bundle.putInt("itemId",mItemId);
            bundle.putString("itemName",mItemName);
            ft.add(android.R.id.content, ConsultListFragment.newInstance());
            ft.commit();*/

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ConsultListFragment productionResultFrg = ConsultListFragment.newInstance();
            Bundle bundle = new Bundle();
            bundle.putInt("itemId",mItemId);
            bundle.putString("itemName",mItemName);
            productionResultFrg.setArguments(bundle);
            ft.add(android.R.id.content, productionResultFrg);
            ft.commit();
        }
    }

}
