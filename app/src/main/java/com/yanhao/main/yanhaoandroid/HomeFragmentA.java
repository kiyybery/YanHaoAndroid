package com.yanhao.main.yanhaoandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.lidroid.xutils.BitmapUtils;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.squareup.okhttp.Request;
import com.yanhao.main.yanhaoandroid.adapter.HomeListViewAapter;
import com.yanhao.main.yanhaoandroid.banner.BannerItem;
import com.yanhao.main.yanhaoandroid.bean.ADInfo;
import com.yanhao.main.yanhaoandroid.bean.BannerBean;
import com.yanhao.main.yanhaoandroid.bean.BannerList;
import com.yanhao.main.yanhaoandroid.bean.CounselorListBean;
import com.yanhao.main.yanhaoandroid.bean.HomeActivityBean;
import com.yanhao.main.yanhaoandroid.util.CycleViewPager;
import com.yanhao.main.yanhaoandroid.util.ImageCycleView;
import com.yanhao.main.yanhaoandroid.util.ViewFactory;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/13 0013.
 */
public class HomeFragmentA extends Fragment {

    private List<ImageView> views = new ArrayList<ImageView>();
    private List<ADInfo> infos = new ArrayList<ADInfo>();
    private CycleViewPager cycleViewPager;
    String message;

    ArrayList<BannerList> listViewDataRefresh = new ArrayList<BannerList>();
    String[] imageUrls;

    private BannerBean mBanner;
    private List<BannerBean> mBannerList = new ArrayList<>();
    private CounselorListBean mCounselor;
    private String constorId;
    private List<CounselorListBean> mCounselorList = new ArrayList<>();

    private List<HomeActivityBean> mList = new ArrayList<>();
    private HomeActivityBean mBean;

    private ListView mListview;
    private HomeListViewAapter homeListViewAapter;

    View view;

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
                for (int j = 0; j < counselorArray.length(); j++) {

                    mCounselor = new CounselorListBean();
                    JSONObject counselor_obj = (JSONObject) counselorArray.get(j);
                    constorId = counselor_obj.getString("userId");
                    mCounselor.setUserId(constorId);
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

               /* mListview.setAdapter(homeListViewAapter);
                homeListViewAapter.notifyDataSetChanged();*/
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.ui_main, container, false);

        getBannerFromServer();
        return view;
    }

    private void getBannerFromServer() {

        String url = "http://210.51.190.27:8082/getHome.jspa";
        //String url = YanHao.api_base + "getHome.jspa";
        OkHttpUtils
                .post()
                .url(url)
                .addParams("userId", "1")
                .build()
                .execute(new MyHomePageCallback());
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
        cycleViewPager.setWheel(false);

        // 设置轮播时间，默认5000ms
        cycleViewPager.setTime(2000);
        // 设置圆点指示图标组居中显示，默认靠右
        cycleViewPager.setIndicatorCenter();
    }

    private CycleViewPager.ImageCycleViewListener mAdCycleViewListener = new CycleViewPager.ImageCycleViewListener() {

        @Override
        public void onImageClick(ADInfo info, int position, View imageView) {
            if (cycleViewPager.isCycle()) {
                position = position - 1;
                Toast.makeText(getActivity(),
                        "position-->" + info.getContent(), Toast.LENGTH_SHORT)
                        .show();
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

}
