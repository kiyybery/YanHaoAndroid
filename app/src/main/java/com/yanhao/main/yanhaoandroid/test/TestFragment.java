package com.yanhao.main.yanhaoandroid.test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.WebViewActivity;
import com.yanhao.main.yanhaoandroid.YanHao;
import com.yanhao.main.yanhaoandroid.adapter.SerachAdapter;
import com.yanhao.main.yanhaoandroid.adapter.TestAdapter;
import com.yanhao.main.yanhaoandroid.adapter.TestCategoryAdapter;
import com.yanhao.main.yanhaoandroid.banner.T;
import com.yanhao.main.yanhaoandroid.bean.TestBean;
import com.yanhao.main.yanhaoandroid.bean.UserInfo;
import com.yanhao.main.yanhaoandroid.task.PoolAsyncTask;
import com.yanhao.main.yanhaoandroid.util.PrefHelper;
import com.yanhao.main.yanhaoandroid.util.RelayoutViewTool;
import com.yanhao.main.yanhaoandroid.util.XListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/19 0019.
 */
public class TestFragment extends Fragment implements View.OnClickListener, XListView.IXListViewListener {

    private XListView mTest_lv;
    private List<TestBean> mList, mCategoryList;
    private TestBean mTestBean;
    private ImageView mIv_section_title_right, mIv_section_title_back;
    private RelativeLayout mTitleLayout;
    private TextView mTitle_tv;
    private TestAdapter mAdapter;
    private TestCategoryAdapter mCategoryAdapter;
    private ProgressBar progressBar;
    SharedPreferences sp;
    private String userId;
    private int pageId = 1;
    private Handler mHandler;
    private int start = 0;
    private static int refreshCnt = 0;
    private int count = 10;
    private int countPerPage;
    private JSONArray jsonArray;
    private String category;

    private class MyTestCallback extends StringCallback {

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
                mList.clear();
                JSONObject jsonObject = new JSONObject(s);
                progressBar.setVisibility(View.GONE);
                jsonArray = jsonObject.getJSONArray("testScaleList");
                for (int i = 0; i < jsonArray.length(); i++) {

                    mTestBean = new TestBean();
                    JSONObject job = (JSONObject) jsonArray.get(i);
                    mTestBean.id = job.getInt("scaleId");
                    mTestBean.test_title = job.getString("title");
                    mTestBean.test_tag = job.getInt("type");
                    mTestBean.img = job.getString("imageUrl");
                    mTestBean.webUrl = job.getString("webUrl");
                    mTestBean.peopleNum = job.getString("userCount");

                    mList.add(mTestBean);
                    mTest_lv.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
                //mAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    private class MyCategoryCallback extends StringCallback {

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
                //mList.clear();
                JSONObject jsonObject = new JSONObject(s);
                countPerPage = jsonObject.getInt("countPerPage");
                progressBar.setVisibility(View.GONE);
                JSONArray array = jsonObject.getJSONArray("testScaleList");
                for (int i = 0; i < array.length(); i++) {

                    mTestBean = new TestBean();
                    JSONObject job = (JSONObject) array.get(i);
                    mTestBean.id = job.getInt("scaleId");
                    mTestBean.test_title = job.getString("title");
                    mTestBean.test_tag = job.getInt("type");
                    mTestBean.img = job.getString("imageUrl");
                    mTestBean.webUrl = job.getString("webUrl");
                    mTestBean.peopleNum = job.getString("userCount");

                    mList.add(mTestBean);

                    mTest_lv.setAdapter(mCategoryAdapter);
                    mCategoryAdapter.notifyDataSetChanged();
                }
                mAdapter.notifyDataSetChanged();
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

        sp = getActivity().getSharedPreferences("userInfo", getActivity().MODE_PRIVATE);
        userId = sp.getString("userId", "");
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        RelayoutViewTool.relayoutViewWithScale(view, YanHao.screenWidthScale);

        //initData();

        // getItemInfo();
        getTestItem(userId);
        mHandler = new Handler();
        progressBar = (ProgressBar) view.findViewById(R.id.progressbar_test);
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
        mCategoryList = new ArrayList<>();
        /*for (int i = 0; i < 10; i++) {
            mTestBean = new TestBean();
            mTestBean.setTest_title("测试题标题" + i);
            mTestBean.setTime("201" + i);
            mList.add(mTestBean);
        }*/
        mTest_lv = (XListView) view.findViewById(R.id.test_listview);
        mAdapter = new TestAdapter(mList, getActivity());
        mCategoryAdapter = new TestCategoryAdapter(mList, getActivity());
        mTest_lv.setEmptyView(getActivity().findViewById(R.id.empty_rl));
        mTest_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra("titleName", mList.get(i - 1).test_title);
                intent.putExtra("webUrl", mList.get(i - 1).webUrl);
                Log.i("test_wenUrl", mList.get(i - 1).webUrl);
                //intent.putExtra("webUrl", "http://210.51.190.27:8082/getScaleInfo.jspa?scaleId=45&userId=WbqhSt2gqUU%3D");
                intent.setClass(getActivity(), WebViewTest.class);
                startActivity(intent);
            }
        });
        mTest_lv.setXListViewListener(this);
        mTest_lv.setPullLoadEnable(true);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            default:
                break;
        }
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
        TextView all_tv = (TextView) v.findViewById(R.id.all_tv);
        all_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTestItem(userId);
                pw.dismiss();
            }
        });
        TextView marry_tv = (TextView) v.findViewById(R.id.marry_tv);
        marry_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = "恋爱婚姻";
                getCategory(category, pageId);
                mList.clear();
                pw.dismiss();
            }
        });
        TextView my_tv = (TextView) v.findViewById(R.id.my_tv);
        my_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = "自我意识";
                getCategory(category, pageId);
                mList.clear();
                pw.dismiss();
            }
        });
        TextView eduction_tv = (TextView) v.findViewById(R.id.eduction_tv);
        eduction_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = "成长教育";
                getCategory(category, pageId);
                mList.clear();
                pw.dismiss();
            }
        });
        TextView famliy_tv = (TextView) v.findViewById(R.id.famliy_tv);
        famliy_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = "家庭与人际关系";
                getCategory(category, pageId);
                mList.clear();
                pw.dismiss();
            }
        });
        TextView jiankang_tv = (TextView) v.findViewById(R.id.jiankang_tv);
        jiankang_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = "一般健康量表";
                getCategory(category, pageId);
                mList.clear();
                pw.dismiss();
            }
        });
    }

    private void getTestItem(String userId) {

        String url = "http://210.51.190.27:8082/getTestHome.jspa";
        //String url = YanHao.api_base + "getTestHome.jspa";
        String id = PrefHelper.get().getString("userId", "");
        //Toast.makeText(getActivity(), id + " is test", Toast.LENGTH_LONG).show();
        Log.i("test_id", PrefHelper.get().getString("userId", ""));
        OkHttpUtils
                .post()
                .url(url)
                .addParams("userId", id)
                .addParams("pageId", pageId + "")
                .build()
                .execute(new MyTestCallback());

    }

    private void getCategory(String category, int pageId) {

        progressBar.setVisibility(View.VISIBLE);
        //mList.clear();
        String url = "http://210.51.190.27:8082/getCategoryScale.jspa";
        //String url = YanHao.api_base + "getCategoryScale.jspa";
        OkHttpUtils
                .post()
                .url(url)
                .addParams("category", category)
                .addParams("pageId", pageId + "")
                .addParams("userId", PrefHelper.get().getString("userId", ""))
                .build()
                .execute(new MyCategoryCallback());
    }

    private void onLoad() {
        mTest_lv.stopRefresh();
        mTest_lv.stopLoadMore();
        mTest_lv.setRefreshTime("刚刚");
    }

    @Override
    public void onRefresh() {

        new Handler().postDelayed(new Runnable() {
            public void run() {
                start = ++refreshCnt;
                //mList.clear();
                getTestItem(PrefHelper.get().getString("userId", ""));
                mAdapter = new TestAdapter(mList, getActivity());
                mTest_lv.setAdapter(mAdapter);
                onLoad();
            }
        }, 1500);
    }

    @Override
    public void onLoadMore() {

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (jsonArray.length() == countPerPage) {

                    //getTestItem(PrefHelper.get().getString("userId", ""));
                    getCategory(category, ++pageId);
                    mAdapter.notifyDataSetChanged();
                    onLoad();
                } else {

                    onLoad();
                    Toast.makeText(getActivity(), "已经到底了！", Toast.LENGTH_LONG).show();
                    //mTest_lv.setPullLoadEnable(false);
                }

            }
        }, 2000);

    }
}
