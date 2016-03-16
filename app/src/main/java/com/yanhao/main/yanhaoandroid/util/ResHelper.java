package com.yanhao.main.yanhaoandroid.util;

import android.graphics.drawable.Drawable;

import com.yanhao.main.yanhaoandroid.YanHao;


public class ResHelper {

    public static String getString(int id) {
        return YanHao.get().getResources().getString(id);
    }

    public static String getString(int id, Object... formatArgs) {
        return YanHao.get().getString(id, formatArgs);
    }

    public static int getColor(int id) {
        return YanHao.get().getResources().getColor(id);
    }

    public static Drawable getDrawable(int id) {
        return YanHao.get().getResources().getDrawable(id);
    }

}
