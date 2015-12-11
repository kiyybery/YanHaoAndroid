package com.yanhao.main.yanhaoandroid;

import java.io.File;

/**
 * Created by Administrator on 2015/10/29 0029.
 */
public class YanHao extends YanHaoApplication{

    public static final int PROTOCAL_STATUS_OK = 0;
    public static final int PROTOCAL_STATUS_TIMEOUT = 1;
    public static final int PROTOCAL_STATUS_NETWORK_SOCKETERROR = 2;
    public static final int PROTOCAL_STATUS_IOERROR = 3;
    public static final int PROTOCAL_STATUS_MALFORMEDURL =4;
    public static final int PROTOCAL_STATUS_HTTPTASKERROR = 5;
    public static final int PROTOCAL_STATUS_INVALIDFORMAT = 6;

    //测试地址
    public static final String HOST_URL="http://118.144.79.221:8091";
    public static final String api_base = HOST_URL+ File.separator;

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
