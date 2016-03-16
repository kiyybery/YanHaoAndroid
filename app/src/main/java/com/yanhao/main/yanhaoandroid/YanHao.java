package com.yanhao.main.yanhaoandroid;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import com.squareup.leakcanary.LeakCanary;
import com.tencent.android.tpush.XGNotifaction;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.XGPushNotifactionCallback;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2015/10/29 0029.
 */
public class YanHao extends YanHaoApplication {

    public static final int PROTOCAL_STATUS_OK = 0;
    public static final int PROTOCAL_STATUS_TIMEOUT = 1;
    public static final int PROTOCAL_STATUS_NETWORK_SOCKETERROR = 2;
    public static final int PROTOCAL_STATUS_IOERROR = 3;
    public static final int PROTOCAL_STATUS_MALFORMEDURL = 4;
    public static final int PROTOCAL_STATUS_HTTPTASKERROR = 5;
    public static final int PROTOCAL_STATUS_INVALIDFORMAT = 6;

    public static final String APP_ID = "wx49293149c06c00f7";

    //测试地址
    public static final String HOST_URL = "http://118.144.79.221:8080";
    public static final String URL = "http://210.51.190.27:8082/";

    public static final String QINIU_URL = "http://7xop51.com1.z0.glb.clouddn.com/";

    //本地地址
    public static final String TEST_URL = "http://192.168.0.202:8080/";

    public static final String KEY = "zhipengapp!@#$%^&*";

    public static final String MAIN_URL = "http://yh.izhipeng.com";
    public static final String api_base = MAIN_URL + File.separator;

    private static YanHao APP;

    public static YanHao get() {
        return APP;
    }

    public boolean isMainProcess() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        APP = this;

        // 在主进程设置信鸽相关的内容
        if (isMainProcess()) {
            // 为保证弹出通知前一定调用本方法，需要在application的onCreate注册
            // 收到通知时，会调用本回调函数。
            // 相当于这个回调会拦截在信鸽的弹出通知之前被截取
            // 一般上针对需要获取通知内容、标题，设置通知点击的跳转逻辑等等
            XGPushManager
                    .setNotifactionCallback(new XGPushNotifactionCallback() {

                        @Override
                        public void handleNotify(XGNotifaction xGNotifaction) {
                            Log.i("test", "处理信鸽通知：" + xGNotifaction);
                            // 获取标签、内容、自定义内容
                            String title = xGNotifaction.getTitle();
                            String content = xGNotifaction.getContent();
                            String customContent = xGNotifaction
                                    .getCustomContent();
                            // 其它的处理
                            // 如果还要弹出通知，可直接调用以下代码或自己创建Notifaction，否则，本通知将不会弹出在通知栏中。
                            xGNotifaction.doNotify();
                        }
                    });
        }
    }
}
