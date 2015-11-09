package com.yanhao.main.yanhaoandroid.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.yanhao.main.yanhaoandroid.R;

/**
 * Created by Administrator on 2015/11/4 0004.
 */
public class CustomProgressDialog extends ProgressDialog{

    private AnimationDrawable mAnimation;
    private ImageView mImageView;
    private TextView mLoadingTv;
    private String mLoadingTip;
    private Context mContext;
    private int mResid;

    public CustomProgressDialog(Context context,String content,int id) {
        super(context);
        this.mContext = context;
        this.mLoadingTip = content;
        this.mResid = id;
        setCanceledOnTouchOutside(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initview();
        initData();
    }

    public void initData(){
        mImageView.setBackgroundResource(mResid);
        mAnimation = (AnimationDrawable) mImageView.getBackground();
        mImageView.post(new Runnable() {
            @Override
            public void run() {
                mAnimation.start();
            }
        });
        mLoadingTv.setText(mLoadingTip);
    }

    public void setContent(String str) {
        mLoadingTv.setText(str);
    }

    public void initview(){
        setContentView(R.layout.progress_dialog);
        mLoadingTv = (TextView) findViewById(R.id.loadingTv);
        mImageView = (ImageView) findViewById(R.id.loadingIv);
    }
}
