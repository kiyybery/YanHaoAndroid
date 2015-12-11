package com.yanhao.main.yanhaoandroid.login;

import com.yanhao.main.yanhaoandroid.http.NHttpRequest;

/**
 * Created by Administrator on 2015/11/16 0016.
 */
public class LoginReqMessgae extends NHttpRequest {

    private String phone;
    private String password;

    //测试协议bean
    private String name;

    protected LoginReqMessgae(String url, String contentType) {
        super(url, contentType);
    }


    @Override
    protected String buildToCustomContent() {
        return null;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
