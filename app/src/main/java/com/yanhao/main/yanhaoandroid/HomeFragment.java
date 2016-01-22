package com.yanhao.main.yanhaoandroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.banner.widget.Banner.base.BaseBanner;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.yanhao.main.yanhaoandroid.adapter.HomeListViewAapter;
import com.yanhao.main.yanhaoandroid.banner.BannerItem;
import com.yanhao.main.yanhaoandroid.banner.DataProvider;
import com.yanhao.main.yanhaoandroid.banner.SimpleImageBanner;
import com.yanhao.main.yanhaoandroid.banner.SimpleImageBannerConstant;
import com.yanhao.main.yanhaoandroid.banner.T;
import com.yanhao.main.yanhaoandroid.bean.BannerBean;
import com.yanhao.main.yanhaoandroid.bean.CounselorListBean;
import com.yanhao.main.yanhaoandroid.bean.HomeActivityBean;
import com.yanhao.main.yanhaoandroid.bean.UserInfo;
import com.yanhao.main.yanhaoandroid.homepage.HomePageActivity;
import com.yanhao.main.yanhaoandroid.homepage.InYanHaoActivity;
import com.yanhao.main.yanhaoandroid.homepage.ReadActivity;
import com.yanhao.main.yanhaoandroid.homepage.RecommendActivity;
import com.yanhao.main.yanhaoandroid.homepage.RecommendFragment;
import com.yanhao.main.yanhaoandroid.serach.SerachActivity;
import com.yanhao.main.yanhaoandroid.serach.SerachFragment;
import com.yanhao.main.yanhaoandroid.util.RelayoutViewTool;
import com.yanhao.main.yanhaoandroid.util.TopBar;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/10/30 0030.
 */
public class HomeFragment extends Fragment implements android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener {

    private TopBar mTopBar;

    private Context context = getActivity();
    private View decorView;
    private DisplayMetrics dm;
    private SimpleImageBanner sib;
    private SimpleImageBannerConstant sib_recommended;
    private ListView mListview;
    private List<HomeActivityBean> mList;
    private HomeActivityBean mBean;
    private List<BannerBean> mBannerList = new ArrayList<>();
    private BannerBean mBanner;
    private List<CounselorListBean> mCounselorList = new ArrayList<>();
    private CounselorListBean mCounselor;
    private List<String> scrollBannerList = new ArrayList<>();
    ArrayList list;
    private RelativeLayout mTitle, mRecommended_layout, mReadEvery;
    private TextView mTitle_tv, mTitle_tv_left, recommended_tv_right;
    private ImageView mIv_section_title_right;
    private HomeListViewAapter homeListViewAapter;
    private ProgressBar progressBar;
    SwipeRefreshLayout swipe;


    private class MyHomePageCallback extends StringCallback {

        @Override
        public void onAfter() {
            super.onAfter();
        }

        @Override
        public void onBefore(Request request) {
            super.onBefore(request);

        }

        @Override
        public void onError(Request request, Exception e) {

        }

        @Override
        public void onResponse(String s) {

            try {
                JSONObject jo = new JSONObject(s);
                progressBar.setVisibility(View.GONE);
                JSONArray bannerArray = jo.getJSONArray("bannerList");
                for (int b = 0; b < bannerArray.length(); b++) {

                    mBanner = new BannerBean();
                    JSONObject banner_obj = (JSONObject) bannerArray.get(b);
                    mBanner.setBannerParam(banner_obj.getString("actionParam"));
                    mBanner.setBannerType(banner_obj.getInt("actionType"));
                    mBanner.setBannerUrl(banner_obj.getString("bannerUrl"));
                    mBanner.setActionUrl(banner_obj.getString("actionUrl"));

                    mBannerList.add(mBanner);
                    scrollBannerList.add(mBanner.getBannerUrl());
                    Log.i("scrollBannerList", scrollBannerList.size() + "");
                }

                JSONArray counselorArray = jo.getJSONArray("counselorList");
                for (int j = 0; j < counselorArray.length(); j++) {

                    mCounselor = new CounselorListBean();
                    JSONObject counselor_obj = (JSONObject) counselorArray.get(j);
                    mCounselor.setUserId(counselor_obj.getString("userId"));
                    mCounselor.setImageUrl(counselor_obj.getString("imageUrl"));

                    mCounselorList.add(mCounselor);
                }

                JSONArray ja = jo.getJSONArray("actiList");
                for (int i = 0; i < ja.length(); i++) {

                    mBean = new HomeActivityBean();
                    JSONObject jsobj = (JSONObject) ja.get(i);
                    String photoUrl = jsobj.getString("imageUrl");
                    String name = jsobj.getString("name");
                    String speaker = jsobj.getString("speaker");
                    String webUrl = jsobj.getString("webUrl");
                    String charges = jsobj.getString("charges");

                    mBean.title = name;
                    mBean.teacher = speaker;
                    mBean.image = photoUrl;
                    mBean.webUrl = webUrl;
                    mBean.pay = charges;
                    mList.add(mBean);
                }

                mListview.setAdapter(homeListViewAapter);
                homeListViewAapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_home, container, false);

        RelayoutViewTool.relayoutViewWithScale(view, YanHao.screenWidthScale);

        //postString();
        postHomeInfo();

        progressBar = (ProgressBar) view.findViewById(R.id.progressbar_home);
        mList = new ArrayList<>();
        mTitle = (RelativeLayout) view.findViewById(R.id.rl_home_title);
        mTitle.setBackgroundColor(0xff0a82e1);
        mReadEvery = (RelativeLayout) view.findViewById(R.id.read_everyday);
        mReadEvery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setClass(getActivity(), ReadActivity.class);
                startActivity(intent);
            }
        });
        /*mTitle_tv_left = (TextView) view.findViewById(R.id.tv_home_title_left);
        mTitle_tv_left.setText("进驻");
        mTitle_tv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                *//*Intent i = new Intent();
                i.setClass(getActivity(), InYanHaoActivity.class);
                startActivity(i);*//*
                T.show(getActivity(), "敬请期待！", 1000);
            }
        });*/
        mTitle_tv = (TextView) view.findViewById(R.id.tv_home_title_title);
        mTitle_tv.setText("燕好");
        mIv_section_title_right = (ImageView) view.findViewById(R.id.iv_home_title_right);
        mIv_section_title_right.setImageResource(R.drawable.shouye_sousuo_icon);
        mIv_section_title_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getActivity(),DialogActivity.class));
                Intent intent = new Intent();
                intent.setClass(getActivity(), SerachActivity.class);
                startActivity(intent);

                //T.show(getActivity(), "敬请期待！", 1000);
            }
        });

        sib = (SimpleImageBanner) view.findViewById(R.id.sib_simple_usage);

        sib.setSource(DataProvider.getList()).startScroll();

        sib.setOnItemClickL(new SimpleImageBanner.OnItemClickL() {
            @Override
            public void onItemClick(int position) {

                if (mBannerList.get(position).getBannerType() == 0) {

                    Intent intent = new Intent(getActivity(), WebViewActivity.class);
                    intent.putExtra("webUrl", mBannerList.get(position).getActionUrl());
                    startActivity(intent);
                } else {

                    Intent intent = new Intent();
                    intent.putExtra("userId", mBannerList.get(position).getBannerParam());
                    intent.setClass(getActivity(), HomePageActivity.class);
                    startActivity(intent);
                }
            }
        });

        sib_recommended = (SimpleImageBannerConstant) view.findViewById(R.id.sib_simple_usage_recommended);
        sib_recommended.setSource(DataProvider.getConstantList()).startScroll();
        sib_recommended.setOnItemClickL(new BaseBanner.OnItemClickL() {
            @Override
            public void onItemClick(int i) {
                Intent intent = new Intent();
                intent.putExtra("userId", mCounselorList.get(i).getUserId());
                intent.setClass(getActivity(), HomePageActivity.class);
                startActivity(intent);
            }
        });

        /*mList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {

            mBean = new HomeActivityBean();
            mBean.setTitle("《我们和娃一起变二》大型公益活动" + i);
            mBean.setTeacher("讲师" + i);
            mBean.setPay(i + "");
            mList.add(mBean);
        }*/

        mListview = (ListView) view.findViewById(R.id.home_activity_listview);
        homeListViewAapter = new HomeListViewAapter(mList, getActivity());

        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("webUrl", mList.get(i).webUrl);
                startActivity(intent);
            }
        });

        recommended_tv_right = (TextView) view.findViewById(R.id.recommended_tv_right);
        recommended_tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), RecommendActivity.class);
                startActivity(intent);

            }
        });
        //TopBar..FrogetFragment详情注释
        /*mTopBar = (TopBar) view.findViewById(R.id.topBar_home);
        mTopBar.setOnTopbarClickListener(new TopBar.topbarClickListener() {
            @Override
            public void leftClick() {

            }

            @Override
            public void rightClick() {
                Toast.makeText(getActivity(), "go to Serach", Toast.LENGTH_LONG).show();
            }
        });

        mTopBar.setButtonVisable(0, false);
        mTopBar.setButtonVisable(1, true);*/

        swipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        swipe.setOnRefreshListener(this);
        // 顶部刷新的样式
        swipe.setColorSchemeResources(android.R.color.holo_red_light,
                android.R.color.holo_green_light,
                android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light);


        SharedPreferences sp = getActivity().getSharedPreferences("userId", getActivity().MODE_PRIVATE);
        sp.getString("userId", "");
        //Toast.makeText(getActivity(), "userId" + sp.getString("userId", ""), Toast.LENGTH_LONG).show();

        return view;
    }

    private void getHomePageInfo() {

        String url = YanHao.QINIU_URL + "json_home_1.txt";
        OkHttpUtils.postString()
                .url(url)
                .build()
                .execute(new MyHomePageCallback());
    }

    public void postString() {
        String url = "http://7xop51.com1.z0.glb.clouddn.com/json_home_1_05.txt";
        OkHttpUtils
                .postString()
                .url(url)
                .content(new Gson().toJson(new UserInfo("zhy", "123")))
                .build()
                .execute(new MyHomePageCallback());

    }

    public void postHomeInfo() {

        String url = "http://210.51.190.27:8082/getHome.jspa";
        OkHttpUtils
                .post()
                .url(url)
                .addParams("userId", "1")
                .build()
                .execute(new MyHomePageCallback());
    }

    public ArrayList<BannerItem> getScrollList() {

        ArrayList list = new ArrayList();

        BannerItem bannerItem = new BannerItem();
        return list;
    }

    @Override
    public void onRefresh() {

        new Handler().postDelayed(new Runnable() {
            public void run() {
                postHomeInfo();
                swipe.setRefreshing(false);
            }
        }, 1500);
    }
}
