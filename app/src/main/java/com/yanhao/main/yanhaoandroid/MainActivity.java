package com.yanhao.main.yanhaoandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.squareup.okhttp.Request;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.service.XGPushService;
import com.yanhao.main.yanhaoandroid.bottomBar.library.PagerBottomTabStrip;
import com.yanhao.main.yanhaoandroid.consult.ConsultFragment;
import com.yanhao.main.yanhaoandroid.test.ClassifyWindow;
import com.yanhao.main.yanhaoandroid.test.TestFragment;
import com.yanhao.main.yanhaoandroid.usercenter.MyPrefireFragment;
import com.yanhao.main.yanhaoandroid.util.PrefHelper;
import com.yanhao.main.yanhaoandroid.util.ViewFindUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private int[] iconResid =
            {R.drawable.foot_icon_home,
                    R.drawable.foot_icon_ask,
                    R.drawable.foot_icon_test,
                    R.drawable.foot_icon_my};

    private int[] iconResidClick =
            {R.drawable.foot_icon_home_pressed,
                    R.drawable.foot_icon_ask_pressed,
                    R.drawable.foot_icon_test_pressed,
                    R.drawable.foot_icon_my_pressed};
    private String[] titles = {"燕好网", "咨询", "测试", "我的"};

    private ArrayList<Fragment> fragments2 = new ArrayList<>();
    private ArrayList<CustomTabEntity> tabs = new ArrayList<>();
    private CommonTabLayout tl;

    private ViewPager mViewPager;

    private PagerBottomTabStrip mPagerBottomTabStrip;

    private ClassifyWindow mClassifyWindow;

    private String userId;
    private View decorView;
    private Context context;
    Message m = null;
    String DEVICE_ID;

    private class SaveMessage extends StringCallback {


        @Override
        public void onError(Request request, Exception e) {

        }

        @Override
        public void onResponse(String s) {

            //Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
            try {
                JSONObject jsonObject = new JSONObject(s);
                Toast.makeText(MainActivity.this, jsonObject.getString("msg"), Toast.LENGTH_LONG).show();
                if (jsonObject.getString("msg").equals("success)")) {


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_home);

        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        DEVICE_ID = tm.getDeviceId();
        Log.i("DEVICE_ID", DEVICE_ID);

        context = this;
        XGPushConfig.enableDebug(this, true);

        Handler handler = new HandlerExtension(MainActivity.this);
        m = handler.obtainMessage();
        // 注册接口
        XGPushManager.registerPush(getApplicationContext(),
                new XGIOperateCallback() {
                    @Override
                    public void onSuccess(Object data, int flag) {
                        Log.w(Constants.LogTag,
                                "+++ register push sucess. token:" + data);
                        m.obj = "+++ register push sucess. token:" + data;
                        m.sendToTarget();

                        //getMessageForServer();
                    }

                    @Override
                    public void onFail(Object data, int errCode, String msg) {
                        Log.w(Constants.LogTag,
                                "+++ register push fail. token:" + data
                                        + ", errCode:" + errCode + ",msg:"
                                        + msg);

                        m.obj = "+++ register push fail. token:" + data
                                + ", errCode:" + errCode + ",msg:" + msg;
                        m.sendToTarget();
                    }
                });
        userId = getIntent().getStringExtra("userId");

        getMessageForServer();
        Intent service = new Intent(context, XGPushService.class);
        context.startService(service);

        for (int i = 0; i < titles.length; i++) {
            tabs.add(new TabEntity(titles[i], iconResidClick[i], iconResid[i]));
        }
        //透明状态栏
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        //hideNavigationBar();
        //initAdapter();
        /*mPagerBottomTabStrip = (PagerBottomTabStrip) findViewById(R.id.tab);
        mPagerBottomTabStrip.builder(mViewPager)
                .ColorMode()
                .TabIcon(iconResid)
                .TabBackground(R.drawable.background_tab)
                .TabClickIcon(iconResidClick)
                .TabPadding(5)
                .build();*/
        /*List<Fragment> list = new ArrayList<Fragment>();
        list.add(new HomeFragment());
        list.add(new ConsultFragment());
        list.add(new TestFragment());
        list.add(new MyPrefireFragment());*/
        fragments2.add(new HomeFragment());
        fragments2.add(new ConsultFragment());
        fragments2.add(new TestFragment());
        fragments2.add(new MyPrefireFragment());

        decorView = getWindow().getDecorView();
        tl = ViewFindUtils.find(decorView, R.id.tl_2);
        //tl = (CommonTabLayout) findViewById(R.id.tl_2);
        tl.setTabData(tabs, this, R.id.fl_change, fragments2);
    }

    public void initAdapter() {

        //mViewPager = (ViewPager) findViewById(R.id.viewpager);

        /*for(int i = 0 ; i < 4 ; i++){
            list.add(new HomeFragment());
        }*/

        //Adapter adapter = new Adapter(getSupportFragmentManager(), list);
        //mViewPager.setAdapter(adapter);
    }

    public void hideNavigationBar() {
        int uiFlags =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION; // hide nav bar

        if (android.os.Build.VERSION.SDK_INT >= 19) {
            uiFlags |= 0x00001000;    //SYSTEM_UI_FLAG_IMMERSIVE_STICKY: hide navigation bars - compatibility: building API level is lower thatn 19, use magic number directly for higher API target level
        } else {
            uiFlags |= View.SYSTEM_UI_FLAG_LOW_PROFILE;
        }
        getWindow().getDecorView().setSystemUiVisibility(uiFlags);
    }

    /*@Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideNavigationBar();
        }
    }*/

    @Override
    protected void onNewIntent(Intent intent) {
        // TODO Auto-generated method stub
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        XGPushClickedResult click = XGPushManager.onActivityStarted(this);
        Log.d("TPush", "onResumeXGPushClickedResult:" + click);
        if (click != null) { // 判断是否来自信鸽的打开方式
            Toast.makeText(this, "通知被点击:" + click.toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        XGPushManager.onActivityStoped(this);
    }

    @Override
    protected void onDestroy() {
        //unregisterReceiver(updateListViewReceiver);
        super.onDestroy();
    }

    private static class HandlerExtension extends Handler {
        WeakReference<MainActivity> mActivity;

        HandlerExtension(MainActivity activity) {
            mActivity = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MainActivity theActivity = mActivity.get();
            if (theActivity == null) {
                theActivity = new MainActivity();
            }
            if (msg != null) {
                Log.w(Constants.LogTag, msg.obj.toString());
                Log.i("push_token", XGPushConfig.getToken(theActivity));
            }
            // XGPushManager.registerCustomNotification(theActivity,
            // "BACKSTREET", "BOYS", System.currentTimeMillis() + 5000, 0);
        }
    }

    private void getMessageForServer() {

        String url = "http://210.51.190.27:8081/xinge/login.jspa";
        OkHttpUtils
                .post()
                .url(url)
                .addParams("deviceToken", XGPushConfig.getToken(context))
                .addParams("appVersion", "1.1")
                .addParams("deviceId", DEVICE_ID)
                .addParams("appId", "yanhao")
                .addParams("os", "ANDROID")
                .addParams("userId", PrefHelper.get().getString("userId", ""))
                .build()
                .execute(new SaveMessage());
    }
}
