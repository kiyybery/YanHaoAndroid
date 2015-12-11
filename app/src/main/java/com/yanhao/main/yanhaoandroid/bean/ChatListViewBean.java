package com.yanhao.main.yanhaoandroid.bean;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2015/11/25 0025.
 */
public class ChatListViewBean {

    private int type;
    private String text;
    private Bitmap icon;

    public ChatListViewBean() {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }
}
