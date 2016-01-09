package com.yanhao.main.yanhaoandroid.test;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.WebViewActivity;
import com.yanhao.main.yanhaoandroid.YanHao;
import com.yanhao.main.yanhaoandroid.adapter.TestAdapter;
import com.yanhao.main.yanhaoandroid.bean.TestBean;
import com.yanhao.main.yanhaoandroid.bean.UserInfo;
import com.yanhao.main.yanhaoandroid.util.RelayoutViewTool;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private TestAdapter mAdapter;

    private class MyTestCallback extends StringCallback {


        @Override
        public void onError(Request request, Exception e) {

        }

        @Override
        public void onResponse(String s) {

            try {
                mList.clear();
                JSONObject jsonObject = new JSONObject(s);
                JSONArray array = jsonObject.getJSONArray("testList");
                for (int i = 0; i < array.length(); i++) {

                    mTestBean = new TestBean();
                    JSONObject job = (JSONObject) array.get(i);
                    mTestBean.id = job.getInt("id");
                    mTestBean.test_title = job.getString("name");
                    mTestBean.test_tag = job.getInt("type");
                    mTestBean.img = job.getString("photoUrl");
                    mTestBean.webUrl = job.getString("webUrl");
                    mTestBean.peopleNum = job.getString("peopleNum");

                    mList.add(mTestBean);
                    mAdapter.notifyDataSetChanged();
                }
                //mAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mClassifyWindow = new ClassifyWindow(getActivity(), ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_test, container, false);
        RelayoutViewTool.relayoutViewWithScale(view, YanHao.screenWidthScale);

        //initData();

        getItemInfo();
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
                //mClassifyWindow.show(view);
                showSpinner();
            }
        });
        mIv_section_title_back = (ImageView) view.findViewById(R.id.iv_section_title_back);
        mIv_section_title_back.setVisibility(View.GONE);
        mList = new ArrayList<>();
        /*for (int i = 0; i < 10; i++) {
            mTestBean = new TestBean();
            mTestBean.setTest_title("测试题标题" + i);
            mTestBean.setTime("201" + i);
            mList.add(mTestBean);
        }*/
        mTest_lv = (ListView) view.findViewById(R.id.test_listview);
        mAdapter = new TestAdapter(mList, getActivity());
        mTest_lv.setAdapter(mAdapter);
        mTest_lv.setEmptyView(getActivity().findViewById(R.id.empty_rl));
        mTest_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra("webUrl", mList.get(i).webUrl);
                intent.setClass(getActivity(), WebViewActivity.class);
                startActivity(intent);
            }
        });
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

    private void showSpinner() {

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.frgment_test_spinner, null);
        RelayoutViewTool.relayoutViewWithScale(v, YanHao.screenWidthScale);
        final PopupWindow pw = new PopupWindow(v, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        pw.setContentView(v);
        pw.setOutsideTouchable(true);
        pw.setFocusable(true);
        pw.setBackgroundDrawable(new BitmapDrawable());
        pw.showAsDropDown(getActivity().findViewById(R.id.iv_section_title_right));
        TextView marry_tv = (TextView) v.findViewById(R.id.marry_tv);
        marry_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getItemInfo();
                pw.dismiss();
            }
        });
        TextView my_tv = (TextView) v.findViewById(R.id.my_tv);
        my_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getItemInfo_My();
                pw.dismiss();
            }
        });
        TextView eduction_tv = (TextView) v.findViewById(R.id.eduction_tv);
        eduction_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getItemInfo_eduction();
                pw.dismiss();
            }
        });
        TextView famliy_tv = (TextView) v.findViewById(R.id.famliy_tv);
        famliy_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getItemInfo_renji();
                pw.dismiss();
            }
        });
        TextView jiankang_tv = (TextView) v.findViewById(R.id.jiankang_tv);
        jiankang_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getItemInfo_jiankang();
                pw.dismiss();
            }
        });
    }

    private void getItemInfo() {

        String url = "http://7xop51.com1.z0.glb.clouddn.com/test_list.txt";
        OkHttpUtils
                .postString()
                .url(url)
                .content(new Gson().toJson(new UserInfo("zhy", "123")))
                .build()
                .execute(new MyTestCallback());
    }

    private void getItemInfo_My() {

        //mList.clear();
        String url = "http://7xop51.com1.z0.glb.clouddn.com/test_list_my.txt";
        OkHttpUtils
                .postString()
                .url(url)
                .content(new Gson().toJson(new UserInfo("zhy", "123")))
                .build()
                .execute(new MyTestCallback());
    }

    private void getItemInfo_eduction() {

        String url = "http://7xop51.com1.z0.glb.clouddn.com/test_list_eduction.txt";
        OkHttpUtils
                .postString()
                .url(url)
                .content(new Gson().toJson(new UserInfo("zhy", "123")))
                .build()
                .execute(new MyTestCallback());
    }

    private void getItemInfo_renji() {

        String url = "http://7xop51.com1.z0.glb.clouddn.com/test_list_renji.txt";
        OkHttpUtils
                .postString()
                .url(url)
                .content(new Gson().toJson(new UserInfo("zhy", "123")))
                .build()
                .execute(new MyTestCallback());
    }

    private void getItemInfo_jiankang() {

        String url = "http://7xop51.com1.z0.glb.clouddn.com/test_list_jiankang.txt";
        OkHttpUtils
                .postString()
                .url(url)
                .content(new Gson().toJson(new UserInfo("zhy", "123")))
                .build()
                .execute(new MyTestCallback());
    }
}
