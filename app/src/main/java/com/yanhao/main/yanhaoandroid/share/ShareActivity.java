package com.yanhao.main.yanhaoandroid.share;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yanhao.main.yanhaoandroid.R;

/**
 * Created by Administrator on 2015/12/11 0011.
 */
public class ShareActivity extends AppCompatActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_friend_share);

        SocialShareFragment f = SocialShareFragment.getInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.container_share, f, null).commit();
    }


}
