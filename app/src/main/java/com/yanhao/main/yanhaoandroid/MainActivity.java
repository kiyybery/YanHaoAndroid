package com.yanhao.main.yanhaoandroid;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.yanhao.main.yanhaoandroid.bottomBar.Adapter;
import com.yanhao.main.yanhaoandroid.bottomBar.library.PagerBottomTabStrip;
import com.yanhao.main.yanhaoandroid.consult.ConsultFragment;
import com.yanhao.main.yanhaoandroid.test.ActionItem;
import com.yanhao.main.yanhaoandroid.test.ClassifyWindow;
import com.yanhao.main.yanhaoandroid.test.TestFragment;
import com.yanhao.main.yanhaoandroid.usercenter.MyPrefireFragment;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_home);

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

        tl = (CommonTabLayout) findViewById(R.id.tl_2);
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
}
