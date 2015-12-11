package com.yanhao.main.yanhaoandroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.banner.widget.Banner.base.BaseBanner;
import com.yanhao.main.yanhaoandroid.adapter.HomeListViewAapter;
import com.yanhao.main.yanhaoandroid.banner.DataProvider;
import com.yanhao.main.yanhaoandroid.banner.SimpleImageBanner;
import com.yanhao.main.yanhaoandroid.banner.T;
import com.yanhao.main.yanhaoandroid.bean.HomeActivityBean;
import com.yanhao.main.yanhaoandroid.homepage.HomePageActivity;
import com.yanhao.main.yanhaoandroid.homepage.InYanHaoActivity;
import com.yanhao.main.yanhaoandroid.homepage.ReadActivity;
import com.yanhao.main.yanhaoandroid.homepage.RecommendActivity;
import com.yanhao.main.yanhaoandroid.homepage.RecommendFragment;
import com.yanhao.main.yanhaoandroid.serach.SerachActivity;
import com.yanhao.main.yanhaoandroid.serach.SerachFragment;
import com.yanhao.main.yanhaoandroid.util.RelayoutViewTool;
import com.yanhao.main.yanhaoandroid.util.TopBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/10/30 0030.
 */
public class HomeFragment extends Fragment {

    private TopBar mTopBar;

    private Context context = getActivity();
    private View decorView;
    private DisplayMetrics dm;
    private SimpleImageBanner sib, sib_recommended;
    private ListView mListview;
    private List<HomeActivityBean> mList;
    private HomeActivityBean mBean;
    private RelativeLayout mTitle, mRecommended_layout, mReadEvery;
    private TextView mTitle_tv, mTitle_tv_left, recommended_tv_right;
    private ImageView mIv_section_title_right;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_home, container, false);

        RelayoutViewTool.relayoutViewWithScale(view, YanHao.screenWidthScale);

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
        mTitle_tv_left = (TextView) view.findViewById(R.id.tv_home_title_left);
        mTitle_tv_left.setText("进驻");
        mTitle_tv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                T.show(getActivity(), "进驻", Toast.LENGTH_LONG);
                Intent i = new Intent();
                i.setClass(getActivity(), InYanHaoActivity.class);
                startActivity(i);
            }
        });
        mTitle_tv = (TextView) view.findViewById(R.id.tv_home_title_title);
        mTitle_tv.setText("燕好");
        mIv_section_title_right = (ImageView) view.findViewById(R.id.iv_home_title_right);
        mIv_section_title_right.setImageResource(R.drawable.shouye_sousuo_icon);
        mIv_section_title_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getActivity(),DialogActivity.class));
                T.show(getActivity(), "搜索", Toast.LENGTH_LONG);
                Intent intent = new Intent();
                intent.setClass(getActivity(), SerachActivity.class);
                startActivity(intent);
            }
        });

        sib = (SimpleImageBanner) view.findViewById(R.id.sib_simple_usage);

        sib.setSource(DataProvider.getList()).startScroll();

        sib.setOnItemClickL(new SimpleImageBanner.OnItemClickL() {
            @Override
            public void onItemClick(int position) {
                T.showShort(context, "position--->" + position);


            }
        });

        sib_recommended = (SimpleImageBanner) view.findViewById(R.id.sib_simple_usage_recommended);
        sib_recommended.setSource(DataProvider.getList()).startScroll();
        sib_recommended.setOnItemClickL(new BaseBanner.OnItemClickL() {
            @Override
            public void onItemClick(int i) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), HomePageActivity.class);
                startActivity(intent);
            }
        });

        mList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {

            mBean = new HomeActivityBean();
            mBean.setTitle("《我们和娃一起变二》大型公益活动" + i);
            mBean.setTeacher("讲师" + i);
            mBean.setPay(i + "");
            mList.add(mBean);
        }

        mListview = (ListView) view.findViewById(R.id.home_activity_listview);
        mListview.setAdapter(new HomeListViewAapter(mList, getActivity()));

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


        SharedPreferences sp = getActivity().getSharedPreferences("userInfo", getActivity().MODE_PRIVATE);
        sp.getString("username", "");
        sp.getString("password", "");
        Toast.makeText(getActivity(), "name" + sp.getString("username", "") + "password" + sp.getString("password", ""), Toast.LENGTH_LONG).show();

        return view;
    }

}
