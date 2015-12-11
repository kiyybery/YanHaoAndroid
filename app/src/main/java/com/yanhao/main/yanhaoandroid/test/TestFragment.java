package com.yanhao.main.yanhaoandroid.test;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.YanHao;
import com.yanhao.main.yanhaoandroid.adapter.TestAdapter;
import com.yanhao.main.yanhaoandroid.bean.TestBean;
import com.yanhao.main.yanhaoandroid.util.RelayoutViewTool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/19 0019.
 */
public class TestFragment extends Fragment implements View.OnClickListener {

    private ListView mTest_lv;
    private List<TestBean> mList;
    private TestBean mTestBean;
    private ImageView mIv_section_title_right, mIv_section_title_back;
    private RelativeLayout mTitleLayout;
    private TextView mTitle_tv;
    private ClassifyWindow mClassifyWindow;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mClassifyWindow = new ClassifyWindow(getActivity(), ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_test, container, false);
        RelayoutViewTool.relayoutViewWithScale(view, YanHao.screenWidthScale);

        initData();

        mTitleLayout = (RelativeLayout) view.findViewById(R.id.rl_section_title);
        mTitleLayout.setBackgroundColor(0xff0a82e1);
        mTitle_tv = (TextView) view.findViewById(R.id.tv_section_title_title);
        mTitle_tv.setText("测试");
        mTitle_tv.setTextColor(0xffffffff);
        mIv_section_title_right = (ImageView) view.findViewById(R.id.iv_section_title_right);
        mIv_section_title_right.setImageResource(R.drawable.ceshi_fenlei_icon);
        mIv_section_title_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getActivity(),DialogActivity.class));
                mClassifyWindow.show(view);
            }
        });
        mIv_section_title_back = (ImageView) view.findViewById(R.id.iv_section_title_back);
        mIv_section_title_back.setVisibility(View.GONE);
        mList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mTestBean = new TestBean();
            mTestBean.setTest_title("测试题标题" + i);
            mTestBean.setTime("201" + i);
            mList.add(mTestBean);
        }
        mTest_lv = (ListView) view.findViewById(R.id.test_listview);
        mTest_lv.setAdapter(new TestAdapter(mList, getActivity()));
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {


            default:
                break;
        }
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //给标题栏弹窗添加子类
        mClassifyWindow.addAction(new ActionItem(getActivity(), "恋爱婚姻"));
        mClassifyWindow.addAction(new ActionItem(getActivity(), "自我意识"));
        mClassifyWindow.addAction(new ActionItem(getActivity(), "成长教育"));
        mClassifyWindow.addAction(new ActionItem(getActivity(), "家庭与人际关系"));
        mClassifyWindow.addAction(new ActionItem(getActivity(), "一般健康量表"));
    }
}
