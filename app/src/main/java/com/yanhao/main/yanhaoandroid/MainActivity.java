package com.yanhao.main.yanhaoandroid;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.yanhao.main.yanhaoandroid.bottomBar.Adapter;
import com.yanhao.main.yanhaoandroid.bottomBar.library.PagerBottomTabStrip;
import com.yanhao.main.yanhaoandroid.consult.ConsultFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int[] iconResid =
            {R.drawable.ic_bookmark_outline_grey600_24dp,
                    R.drawable.ic_search_black_24dp,
                    R.drawable.ic_notifications_none_grey600_24dp,
                    R.drawable.ic_favorite_outline_grey600_24dp};

    private int[] iconResidClick =
            {R.drawable.ic_bookmark_black_24dp,
                    R.drawable.ic_search_black_24dp,
                    R.drawable.ic_notifications_black_24dp,
                    R.drawable.ic_favorite_grey600_24dp};

    private ViewPager mViewPager;

    private PagerBottomTabStrip mPagerBottomTabStrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_home);

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

    public void initAdapter(){

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        List<Fragment> list = new ArrayList<Fragment>();
        /*for(int i = 0 ; i < 4 ; i++){
            list.add(new HomeFragment());
        }*/
        list.add(new HomeFragment());
        list.add(new ConsultFragment());
        list.add(new HomeFragment());
        list.add(new HomeFragment());
        Adapter adapter = new Adapter(getSupportFragmentManager(), list);
        mViewPager.setAdapter(adapter);
    }
}
