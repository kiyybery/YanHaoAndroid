package com.yanhao.main.yanhaoandroid.bottomBar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2015/10/30 0030.
 */
public class Adapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;
    private String[] titles = {"燕好网","测试","咨询","我的"};

    public Adapter(FragmentManager fm,List<Fragment> fragList ) {
        super(fm );
        mFragmentList=fragList ;
    }

    @Override
    public Fragment getItem( int arg0 ) {
        return mFragmentList .get(arg0 );
    }
    @Override
    public int getCount() {
        return mFragmentList .size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
