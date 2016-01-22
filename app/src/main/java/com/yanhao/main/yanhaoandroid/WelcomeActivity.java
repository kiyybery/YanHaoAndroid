package com.yanhao.main.yanhaoandroid;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2016/1/13 0013.
 */
public class WelcomeActivity extends AppCompatActivity {


    private boolean isgetVersion = false;

    private final int SPLASH_DISPLAY_LENGHT = 3000; //延迟三秒

    private SharedPreferences share;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        share = getSharedPreferences("welcome", Activity.MODE_PRIVATE);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                if (!isgetVersion) {

                    share = getSharedPreferences("count", MODE_WORLD_READABLE);
                    int count = share.getInt("count", 0);
                    if (count == 0) {

                        Intent mainIntent = new Intent(WelcomeActivity.this, UserGuideActivity.class);
                        WelcomeActivity.this.startActivity(mainIntent);
                        WelcomeActivity.this.overridePendingTransition(R.anim.activity_open, 0);
                        SharedPreferences.Editor editor = share.edit();
                        editor.putInt("count", ++count);
                        editor.commit();
                        WelcomeActivity.this.finish();

                    } else {

                        setContentView(R.layout.activity_welcome);
                        Intent intent = new Intent();
                        intent.setClass(WelcomeActivity.this, MainActivity.class);
                        startActivity(intent);
                        WelcomeActivity.this.finish();
                    }
                }

            }

        }, SPLASH_DISPLAY_LENGHT);

    }
}
