package com.yanhao.main.yanhaoandroid.bean;

/**
 * Created by Administrator on 2015/12/22 0022.
 */
public class UserInfo {

    public String username ;
    public String password  ;

    public UserInfo() {
        // TODO Auto-generated constructor stub
    }

    public UserInfo(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString()
    {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
