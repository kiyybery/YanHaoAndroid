package com.yanhao.main.yanhaoandroid;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;

/**
 * Created by Administrator on 2015/10/29 0029.
 */
public class YanHaoApplication extends Application{

    public static String content;
    public static Context context;
    public static SharedPreferences preferences;
    public static DisplayMetrics displayMetrics;
    // 原始UI界面设计图的宽度(px)，用于后期对控件做缩放
    public static final float UI_Design_Width = 720;
    public static final float UI_Design_Height = 1136;
    // 屏幕宽度缩放比（相对于原设计图）
    public static float screenWidthScale = 1f;
    public static float screenHeightScale = 1f;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        displayMetrics = getResources().getDisplayMetrics();
        // 初始化屏幕宽度缩放比例
        screenWidthScale = displayMetrics.widthPixels / UI_Design_Width;
        screenHeightScale = displayMetrics.heightPixels / UI_Design_Height;
    }
}
