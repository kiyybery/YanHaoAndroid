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
    public static final String HOST_URL="http://118.144.79.221:8080";
    public static final String api_base = HOST_URL+ File.separator;

    public static final String QINIU_URL = "http://7xop51.com1.z0.glb.clouddn.com/";

    //本地地址
    public static final String TEST_URL = "http://192.168.0.143:8080/selectCounselorInfo.jspa?";

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
