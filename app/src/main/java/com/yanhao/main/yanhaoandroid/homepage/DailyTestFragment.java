package com.yanhao.main.yanhaoandroid.homepage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.YanHao;
import com.yanhao.main.yanhaoandroid.adapter.TestAdapter;
import com.yanhao.main.yanhaoandroid.bean.TestBean;
import com.yanhao.main.yanhaoandroid.test.WebViewTest;
import com.yanhao.main.yanhaoandroid.util.RelayoutViewTool;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/27 0027.
 */
public class DailyTestFragment extends Fragment {

    private View view;
    private ListView mTest_lv;
    private List<TestBean> mList;
    private TestBean mTestBean;
    private ImageView mIv_section_title_right, mIv_section_title_back;
    private RelativeLayout mTitleLayout;
    private LinearLayout mTitleBack;
    private TextView mTitle_tv;
    private TestAdapter mAdapter;
    private ProgressBar progressBar;
    SharedPreferences sp;
    private String userId;

    private class GetDailyTest extends StringCallback {


        @Override
        public void onError(Request request, Exception e) {

        }

        @Override
        public void onResponse(String s) {

            try {
                JSONObject jsonObject = new JSONObject(s);
                progressBar.setVisibility(View.GONE);
                JSONArray array = jsonObject.getJSONArray("dailyTestList");
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
                    mTest_lv.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
                //mAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static DailyTestFragment newInstance() {

        DailyTestFragment fragment = new DailyTestFragment();
        Bundle data = new Bundle();
        data.putString("title", " ");
        fragment.setArguments(data);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_test, container, false);
        RelayoutViewTool.relayoutViewWithScale(view, YanHao.screenWidthScale);

        getDailyTest();
        progressBar = (ProgressBar) view.findViewById(R.id.progressbar_test);
        mTitleLayout = (RelativeLayout) view.findViewById(R.id.rl_section_title);
        mTitle_tv = (TextView) view.findViewById(R.id.tv_section_title_title);
        mTitle_tv.setText("心理健康测试");

        mTitleBack = (LinearLayout) view.findViewById(R.id.ll_section_title_back);
        mTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        mList = new ArrayList<>();

        mTest_lv = (ListView) view.findViewById(R.id.test_listview);
        mAdapter = new TestAdapter(mList, getActivity());

        mTest_lv.setEmptyView(getActivity().findViewById(R.id.empty_rl));
        mTest_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra("webUrl", mList.get(i).webUrl);
                //intent.putExtra("webUrl", "http://test.izhipeng.com/html/Explain.html");
                intent.setClass(getActivity(), WebViewTest.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void getDailyTest() {

        String url = "http://210.51.190.27:8082/getDailyTest.jspa";
        OkHttpUtils
                .post()
                .url(url)
                .build()
                .execute(new GetDailyTest());
    }

}
