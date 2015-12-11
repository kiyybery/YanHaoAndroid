package com.yanhao.main.yanhaoandroid.test;

import android.content.Context;

/**
 * Created by Administrator on 2015/11/20 0020.
 */
public class ActionItem {

    //定义文本对象
    public CharSequence mTitle;

    public ActionItem(CharSequence mTitle) {
        this.mTitle = mTitle;
    }

    public ActionItem(Context context, int titleId) {
        this.mTitle = context.getResources().getText(titleId);
    }

    public ActionItem(Context context, CharSequence title) {
        this.mTitle = title;
    }
}
