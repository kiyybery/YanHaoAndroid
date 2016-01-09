package com.yanhao.main.yanhaoandroid.matchconsultant;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.matchconsultant.listener.OnTabSelectListener;
import com.yanhao.main.yanhaoandroid.util.ViewFindUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/12/8 0008.
 */
public class OrderTimeActivity extends AppCompatActivity implements OnTabSelectListener {

    private Context context = this;
    private TextView mTitle;
    private LinearLayout mBack;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ArrayList<Fragment> fragments_data = new ArrayList<>();
    private final String[] titles = {
            "日", "二", "三", "四"
            , "五", "六", "一"
    };

    private final String[] titles_data = {
            "10-11", "10-12", "10-13", "10-14"
            , "10-15", "10-16", "10-17"
    };

    private class GetReservationSchedule extends StringCallback {


        @Override
        public void onError(Request request, Exception e) {

        }

        @Override
        public void onResponse(String s) {

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray reservationArray = jsonObject.getJSONArray("reservationList");
                for (int i = 0; i < reservationArray.length(); i++) {

                    JSONObject job = (JSONObject) reservationArray.get(i);
                    String data = job.getString("data");
                    int timeMillis = job.getInt("timeMillis");
                    JSONArray scheduleArray = job.getJSONArray("scheduleList");
                    for (int k = 0; k < scheduleArray.length(); k++) {

                        JSONObject scheduleObj = (JSONObject) scheduleArray.get(k);
                        String period = scheduleObj.getString("period");
                        int reservationId = scheduleObj.getInt("reservationId");
                        int status = scheduleObj.getInt("status");
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_tab);

        mTitle = (TextView) findViewById(R.id.tv_section_title_title);
        mTitle.setText("预约时间");
        mBack = (LinearLayout) findViewById(R.id.ll_section_title_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        for (String title : titles_data) {
            fragments.add(SimpleCardFragment.getInstance(title));
            //fragments_data.add(SimpleCardFragment.getInstance(title));
        }

        View decorView = getWindow().getDecorView();
        ViewPager vp = ViewFindUtils.find(decorView, R.id.vp);

        vp.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        SlidingTabLayout tl_2 = ViewFindUtils.find(decorView, R.id.tl_2);
        /** 字体加粗,大写 */
        SlidingTabLayout tl_3 = ViewFindUtils.find(decorView, R.id.tl_3);

        tl_2.setViewPager(vp);
        tl_2.setOnTabSelectListener(this);
        tl_3.setViewPager(vp, titles_data);

        vp.setCurrentItem(4);
    }

    @Override
    public void onTabSelect(int position) {

        Toast.makeText(context, "onTabSelect&position--->" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTabReselect(int position) {
        Toast.makeText(context, "onTabReselect&position--->" + position, Toast.LENGTH_SHORT).show();
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
    }

    private void getReservationSchedule() {

        String url = "http://210.51.190.27:8082/getReservationSchedule.jspa";

        OkHttpUtils
                .post()
                .url(url)
                .addParams("userId", "WbqhSt2gqUU=")
                .build()
                .execute(new GetReservationSchedule());
    }
}
