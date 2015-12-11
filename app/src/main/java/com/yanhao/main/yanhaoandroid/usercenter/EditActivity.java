package com.yanhao.main.yanhaoandroid.usercenter;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.yanhao.main.yanhaoandroid.matchconsultant.MatchConstantFragment;
import com.yanhao.main.yanhaoandroid.util.UIHelper;

/**
 * Created by Administrator on 2015/11/9 0009.
 */
public class EditActivity extends AppCompatActivity{

    private static final String TAG = EditActivity.class.getSimpleName();
    public String name;
    FragmentManager fm = getSupportFragmentManager();
    Dialog dialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState == null){

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

    @Override
    public void onBackPressed() {
        final Fragment fEdit = fm.findFragmentById(android.R.id.content);

        if (fEdit != null &&fEdit instanceof EditFragment && ((EditFragment) fEdit).isChanged()) {
            dialog = UIHelper.buildConfirm(EditActivity.this, "您的个人资料已经修改，退出前要保存吗?", "确定", "取消",
                    new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    //用户确认修改之后，写入缓存。。待做

                    //// TODO: 2015/11/13 0013  
                    finish();
                }
            }, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        }else {
            Log.i(TAG, "super.onBackPressed();");
            super.onBackPressed();
        }
    }
}
