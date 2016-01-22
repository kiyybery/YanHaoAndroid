package com.yanhao.main.yanhaoandroid.util;

/**
 * Created by zihua on 2015/9/17.
 */
public class Type {

    private String msg;
    private String pwd;

    public Type(String message, String password) {

        this.msg = message;
        this.pwd = password;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
