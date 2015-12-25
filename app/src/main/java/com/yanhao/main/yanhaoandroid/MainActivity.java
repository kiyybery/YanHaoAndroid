package com.yanhao.main.yanhaoandroid;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

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
            {R.drawable.ic_home,
                    R.drawable.ic_consult,
                    R.drawable.ic_test,
                    R.drawable.ic_my};

    private int[] iconResidClick =
            {R.drawable.ic_home_pressed,
                    R.drawable.ic_consult_pressed,
                    R.drawable.ic_test_pressed,
                    R.drawable.ic_my_pressed};

    private ViewPager mViewPager;

    private PagerBottomTabStrip mPagerBottomTabStrip;

    private ClassifyWindow mClassifyWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_home);

        //透明状态栏
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        hideNavigationBar();
        initAdapter();
        mPagerBottomTabStrip = (PagerBottomTabStrip) findViewById(R.id.tab);
        mPagerBottomTabStrip.builder(mViewPager)
                .ColorMode()
                .TabIcon(iconResid)
                .TabBackground(R.drawable.background_tab)
                .TabClickIcon(iconResidClick)
                .TabPadding(5)
                .build();
    }

    public void initAdapter() {

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        List<Fragment> list = new ArrayList<Fragment>();
        /*for(int i = 0 ; i < 4 ; i++){
            list.add(new HomeFragment());
        }*/
        list.add(new HomeFragment());
        list.add(new ConsultFragment());
        list.add(new TestFragment());
        list.add(new MyPrefireFragment());
        Adapter adapter = new Adapter(getSupportFragmentManager(), list);
        mViewPager.setAdapter(adapter);
    }

    public void hideNavigationBar() {
        int uiFlags =
                 View.SYSTEM_UI_FLAG_HIDE_NAVIGATION ; // hide nav bar

        if (android.os.Build.VERSION.SDK_INT >= 19) {
            uiFlags |= 0x00001000;    //SYSTEM_UI_FLAG_IMMERSIVE_STICKY: hide navigation bars - compatibility: building API level is lower thatn 19, use magic number directly for higher API target level
        } else {
            uiFlags |= View.SYSTEM_UI_FLAG_LOW_PROFILE;
        }
        getWindow().getDecorView().setSystemUiVisibility(uiFlags);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideNavigationBar();
        }
    }
}
