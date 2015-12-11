package com.yanhao.main.yanhaoandroid.bean;

/**
 * Created by Administrator on 2015/12/10 0010.
 */
public class SelectBean {

    public String text;

    public SelectBean(){

    }

    public SelectBean(String content){

        this.text = content;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
