package com.yanhao.main.yanhaoandroid.usercenter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yanhao.main.yanhaoandroid.R;

/**
 * Created by Administrator on 2016/3/7 0007.
 */
public class AboutDetialActivity extends AppCompatActivity {

    private TextView info_tv, mTitle;
    private LinearLayout mBack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_detial);

        mBack = (LinearLayout) findViewById(R.id.ll_section_title_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mTitle = (TextView) findViewById(R.id.tv_section_title_title);
        mTitle.setText("软件介绍");

        info_tv = (TextView) findViewById(R.id.detial_tv);
        info_tv.setText(R.string.info_about);
    }
}
