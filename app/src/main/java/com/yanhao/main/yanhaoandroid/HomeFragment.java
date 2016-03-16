package com.yanhao.main.yanhaoandroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.squareup.okhttp.Request;
import com.yanhao.main.yanhaoandroid.adapter.HomeListViewAapter;
import com.yanhao.main.yanhaoandroid.banner.BannerItem;
import com.yanhao.main.yanhaoandroid.banner.DataProvider;
import com.yanhao.main.yanhaoandroid.banner.SimpleImageBanner;
import com.yanhao.main.yanhaoandroid.banner.SimpleImageBannerConstant;
import com.yanhao.main.yanhaoandroid.banner.T;
import com.yanhao.main.yanhaoandroid.bean.ADInfo;
import com.yanhao.main.yanhaoandroid.bean.BannerBean;
import com.yanhao.main.yanhaoandroid.bean.CounselorInfo;
import com.yanhao.main.yanhaoandroid.bean.CounselorListBean;
import com.yanhao.main.yanhaoandroid.bean.HomeActivityBean;
import com.yanhao.main.yanhaoandroid.bean.UserInfo;
import com.yanhao.main.yanhaoandroid.homepage.DailyTestActivity;
import com.yanhao.main.yanhaoandroid.homepage.HomePageActivity;
import com.yanhao.main.yanhaoandroid.homepage.InYanHaoActivity;
import com.yanhao.main.yanhaoandroid.homepage.ReadActivity;
import com.yanhao.main.yanhaoandroid.homepage.RecommendActivity;
import com.yanhao.main.yanhaoandroid.homepage.RecommendFragment;
import com.yanhao.main.yanhaoandroid.serach.SerachActivity;
import com.yanhao.main.yanhaoandroid.serach.SerachFragment;
import com.yanhao.main.yanhaoandroid.test.WebViewTest;
import com.yanhao.main.yanhaoandroid.util.CycleViewPager;
import com.yanhao.main.yanhaoandroid.util.ImageCycleView;
import com.yanhao.main.yanhaoandroid.util.PrefHelper;
import com.yanhao.main.yanhaoandroid.util.RelayoutViewTool;
import com.yanhao.main.yanhaoandroid.util.ViewFactory;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.lidroid.xutils.BitmapUtils;

/**
 * Created by Administrator on 2015/10/30 0030.
 */
public class HomeFragment extends Fragment {

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
    private BannerItem mItem;
    private List<CounselorListBean> mCounselorList = new ArrayList<>();
    private CounselorListBean mCounselor;
    private ArrayList<BannerItem> scrollBannerList = new ArrayList<>();
    private RelativeLayout mTitle, mRecommended_layout, mReadEvery, mTestEvery;
    private TextView mTitle_tv, mTitle_tv_left, recommended_tv_right;
    private ImageView mIv_section_title_right;
    private HomeListViewAapter homeListViewAapter;
    private ProgressBar progressBar;
    SwipeRefreshLayout swipe;
    private ViewPager viewPager;
    private ArrayList<View> pageview;
    private String constorId;

    private List<ImageView> views = new ArrayList<ImageView>();
    private List<ImageView> consutor_views = new ArrayList<ImageView>();
    private List<ADInfo> infos = new ArrayList<ADInfo>();
    private CycleViewPager cycleViewPager, cycleViewPager_conustor;
    String[] imageUrls, consutor_images;
    private List<CounselorInfo> counselorInfo = new ArrayList<>();

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
                JSONArray bannerArray = jo.getJSONArray("bannerList");
                imageUrls = new String[bannerArray.length()];
                progressBar.setVisibility(View.GONE);
                for (int b = 0; b < bannerArray.length(); b++) {

                    mBanner = new BannerBean();
                    JSONObject banner_obj = (JSONObject) bannerArray.get(b);
                    mBanner.setBannerParam(banner_obj.getString("actionParam"));
                    Log.i("actionParam", banner_obj.getString("actionParam"));
                    mBanner.setBannerType(banner_obj.getInt("actionType"));
                    mBanner.setBannerUrl(banner_obj.getString("bannerUrl"));
                    mBanner.setActionUrl(banner_obj.getString("actionUrl"));

                    imageUrls[b] = banner_obj.getString("bannerUrl");
                    mBannerList.add(mBanner);

                }
                configImageLoader();
                initialize();

                JSONArray counselorArray = jo.getJSONArray("counselorList");
                consutor_images = new String[counselorArray.length()];
                Log.i("image_urls", consutor_images.length + "");
                for (int j = 0; j < counselorArray.length(); j++) {

                    mCounselor = new CounselorListBean();
                    JSONObject counselor_obj = (JSONObject) counselorArray.get(j);
                    constorId = counselor_obj.getString("userId");
                    mCounselor.setUserId(constorId);
                    mCounselor.setImageUrl(counselor_obj.getString("imageUrl"));

                    consutor_images[j] = counselor_obj.getString("imageUrl");
                    mCounselorList.add(mCounselor);
                }
                //configImageLoader();
                initviewcounstor();

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

        //list.add(new ImageCycleView.ImageInfo(scrollBannerList, "aa", "bb"));
        //Log.i("scrollBannerList", "list = " + list.size() + "");
        //mImageCycleView = (ImageCycleView) view.findViewById(R.id.icv_topView);
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
        mTestEvery = (RelativeLayout) view.findViewById(R.id.lecture_everyday);
        mTestEvery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setClass(getActivity(), DailyTestActivity.class);
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

        //sib = (SimpleImageBanner) view.findViewById(R.id.sib_simple_usage);

        //sib.setSource(DataProvider.getList()).startScroll();

        //sib.setSource(scrollBannerList).startScroll();

        /*sib.setOnItemClickL(new SimpleImageBanner.OnItemClickL() {
            @Override
            public void onItemClick(int position) {

                if (mBannerList.get(position).getBannerType() == 0) {

                    Intent intent = new Intent(getActivity(), WebViewTest.class);
                    intent.putExtra("webUrl", mBannerList.get(position).getActionUrl());
                    startActivity(intent);
                } else {

                    Intent intent = new Intent();
                    intent.putExtra("userId", mBannerList.get(position).getBannerParam());
                    intent.setClass(getActivity(), HomePageActivity.class);
                    startActivity(intent);
                }
            }
        });*/

        /*sib_recommended = (SimpleImageBannerConstant) view.findViewById(R.id.sib_simple_usage_recommended);
        sib_recommended.setSource(DataProvider.getConstantList()).startScroll();
        sib_recommended.setOnItemClickL(new BaseBanner.OnItemClickL() {
            @Override
            public void onItemClick(int i) {
                Intent intent = new Intent();
                intent.putExtra("userId", mCounselorList.get(i).getUserId());
                intent.setClass(getActivity(), HomePageActivity.class);
                startActivity(intent);
            }
        });*/

        /*viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        //inflater = getLayoutInflater()

        View view1 = inflater.inflate(R.layout.item01, null);
        ImageView img = (ImageView) view1.findViewById(R.id.view1_img);
        img.setImageResource(R.drawable.zhuanjia_lilu);
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(), "view1", Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                intent.putExtra("userId", "pqOPSaXVE7o=");
                intent.setClass(getActivity(), HomePageActivity.class);
                startActivity(intent);
            }
        });

        View view2 = inflater.inflate(R.layout.item02, null);
        ImageView img2 = (ImageView) view2.findViewById(R.id.view2_img);
        img2.setImageResource(R.drawable.zhuanjia_songyumei);
        view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(), "view2", Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                intent.putExtra("userId", "HPveYa90QW4=");
                intent.setClass(getActivity(), HomePageActivity.class);
                startActivity(intent);
            }
        });
        pageview = new ArrayList<View>();
        pageview.add(view1);
        pageview.add(view2);

        PagerAdapter mPagerAdapter = new PagerAdapter() {

            @Override
            // ???????????????
            public int getCount() {
                // TODO Auto-generated method stub
                return pageview.size();
            }

            @Override
            // ????????????????
            public boolean isViewFromObject(View arg0, Object arg1) {
                // TODO Auto-generated method stub
                return arg0 == arg1;
            }

            // ???ViewGroup????????View
            public void destroyItem(View arg0, int arg1, Object arg2) {
                ((ViewPager) arg0).removeView(pageview.get(arg1));
            }

            // ???????????????????????PagerAdapter???????????????????????ViewPager??
            public Object instantiateItem(View arg0, int arg1) {
                ((ViewPager) arg0).addView(pageview.get(arg1));
                return pageview.get(arg1);
            }

        };

        // ????????
        viewPager.setAdapter(mPagerAdapter);*/


        mListview = (ListView) view.findViewById(R.id.home_activity_listview);
        homeListViewAapter = new HomeListViewAapter(mList, getActivity());

        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getActivity(), WebViewTest.class);
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


        SharedPreferences sp = getActivity().getSharedPreferences("userId", getActivity().MODE_PRIVATE);
        sp.getString("userId", "");
        //Toast.makeText(getActivity(), "userId" + sp.getString("userId", ""), Toast.LENGTH_LONG).show();

        return view;
    }

    private void initialize() {

        cycleViewPager = (CycleViewPager) getActivity().getFragmentManager()
                .findFragmentById(R.id.fragment_cycle_viewpager_content);

        for (int i = 0; i < imageUrls.length; i++) {
            ADInfo info = new ADInfo();
            info.setUrl(imageUrls[i]);
            info.setContent("图片-->" + i);
            infos.add(info);
        }

        // 将最后一个ImageView添加进来
        views.add(ViewFactory.getImageView(getActivity(), infos.get(infos.size() - 1)
                .getUrl()));
        for (int i = 0; i < infos.size(); i++) {
            views.add(ViewFactory.getImageView(getActivity(), infos.get(i).getUrl()));
        }
        // 将第一个ImageView添加进来
        views.add(ViewFactory.getImageView(getActivity(), infos.get(0).getUrl()));

        // 设置循环，在调用setData方法前调用
        cycleViewPager.setCycle(true);

        // 在加载数据前设置是否循环
        cycleViewPager.setData(views, infos, mAdCycleViewListener);
        // 设置轮播
        cycleViewPager.setWheel(true);

        // 设置轮播时间，默认5000ms
        cycleViewPager.setTime(2000);
        // 设置圆点指示图标组居中显示，默认靠右
        cycleViewPager.setIndicatorCenter();
    }

    private void initviewcounstor() {

        cycleViewPager_conustor = (CycleViewPager) getActivity().getFragmentManager()
                .findFragmentById(R.id.fragment_cycle_viewpager_conustor);

        for (int i = 0; i < consutor_images.length; i++) {
            Log.i("image_urls", consutor_images.length + "");
            CounselorInfo info = new CounselorInfo();
            info.setUrl(consutor_images[i]);
            info.setContent("图片-->" + i);
            counselorInfo.add(info);
        }

        // 将最后一个ImageView添加进来
        consutor_views.add(ViewFactory.getImageView(getActivity(), counselorInfo.get(counselorInfo.size() - 1)
                .getUrl()));
        Log.i("counselorInfosize", counselorInfo.size() + "");
        for (int i = 0; i < counselorInfo.size(); i++) {
            consutor_views.add(ViewFactory.getImageView(getActivity(), counselorInfo.get(i).getUrl()));
        }
        // 将第一个ImageView添加进来
        consutor_views.add(ViewFactory.getImageView(getActivity(), counselorInfo.get(0).getUrl()));

        // 设置循环，在调用setData方法前调用
        cycleViewPager_conustor.setCycle(false);

        // 在加载数据前设置是否循环
        cycleViewPager_conustor.setData(consutor_views, counselorInfo, mCycleListener);
        // 设置轮播
        cycleViewPager_conustor.setWheel(false);

        // 设置轮播时间，默认5000ms
        cycleViewPager_conustor.setTime(2000);
        // 设置圆点指示图标组居中显示，默认靠右
        cycleViewPager_conustor.setIndicatorCenter();
    }

    private CycleViewPager.ImageCycleViewListener mAdCycleViewListener = new CycleViewPager.ImageCycleViewListener() {

        @Override
        public void onImageClick(ADInfo info, int position, View imageView) {
            if (cycleViewPager.isCycle()) {
                position = position - 1;
                /*Toast.makeText(getActivity(),
                        "position-->" + info.getContent(), Toast.LENGTH_SHORT)
                        .show();*/
                if (mBannerList.get(position).getBannerType() == 0) {

                    Intent intent = new Intent(getActivity(), WebViewTest.class);
                    intent.putExtra("webUrl", mBannerList.get(position).getActionUrl());
                    startActivity(intent);
                } else {

                    Intent intent = new Intent();
                    intent.putExtra("userId", mBannerList.get(position).getBannerParam());
                    intent.setClass(getActivity(), HomePageActivity.class);
                    startActivity(intent);
                }
            }
        }
    };

    private CycleViewPager.ImageCycleListener mCycleListener = new CycleViewPager.ImageCycleListener() {
        @Override
        public void onImageClickL(CounselorInfo info, int postion, View imageView) {
            if (cycleViewPager_conustor.isCycle()) {
                //postion = postion - 2;
                Intent intent = new Intent();
                intent.putExtra("userId", mCounselorList.get(postion).getUserId());
                intent.setClass(getActivity(), HomePageActivity.class);
                startActivity(intent);
            }
        }
    };

    /**
     * 配置ImageLoder
     */
    private void configImageLoader() {
        // 初始化ImageLoader
        @SuppressWarnings("deprecation")
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.icon_stub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.icon_empty) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.icon_error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                        // .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
                .build(); // 创建配置过得DisplayImageOption对象

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getActivity()).defaultDisplayImageOptions(options)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }

    public void postHomeInfo() {

        String url = "http://210.51.190.27:8082/getHome.jspa";
        //String url = YanHao.api_base + "getHome.jspa";
        OkHttpUtils
                .post()
                .url(url)
                .addParams("userId", PrefHelper.get().getString("userId", ""))
                .build()
                .execute(new MyHomePageCallback());
    }
}
