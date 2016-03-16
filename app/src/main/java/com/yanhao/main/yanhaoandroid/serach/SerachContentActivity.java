package com.yanhao.main.yanhaoandroid.serach;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.adapter.SerachAdapter;
import com.yanhao.main.yanhaoandroid.bean.ConstantBean;
import com.yanhao.main.yanhaoandroid.homepage.HomePageActivity;
import com.yanhao.main.yanhaoandroid.util.XListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/2/2 0002.
 */
public class SerachContentActivity extends AppCompatActivity implements XListView.IXListViewListener {

    private XListView mListView;
    private List<ConstantBean> mContent;
    private SerachAdapter mAdapter;
    private ConstantBean mBean;
    private Handler mHandler;
    private int start = 0;
    private static int refreshCnt = 0;
    private String mSpeciality;
    private int pageId = 1;
    private int count = 10;
    private int countPerPage;
    private JSONArray jsonArray;
    private String keywords;
    private LinearLayout mBack_layout;
    private TextView mTitle;
    private RelativeLayout empty_layout;

    private class SerachResult extends StringCallback {

        @Override
        public void onError(Request request, Exception e) {

        }

        @Override
        public void onResponse(String s) {
            //Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();

            //mContent.clear();
            try {
                JSONObject jsonObject = new JSONObject(s);
                countPerPage = jsonObject.getInt("countPerPage");
                jsonArray = jsonObject.getJSONArray("counselorList");
                if (jsonArray.length() <= countPerPage) {

                    mListView.setPullLoadEnable(false);
                }
                if (jsonArray.length() == 0) {

                    empty_layout.setVisibility(View.VISIBLE);
                }
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject job = (JSONObject) jsonArray.get(i);
                    String name = job.getString("name");
                    String photoUrl = job.getString("portraitUrl");
                    String userId = job.getString("userId");
                    JSONArray specialityArray = job.getJSONArray("speciality");
                    for (int j = 0; j < specialityArray.length(); j++) {

                        mSpeciality = specialityArray.optString(j);
                    }
                    int level = job.getInt("level");

                    mBean = new ConstantBean();
                    mBean.userid = userId;
                    mBean.constantName = name;
                    mBean.imageview = photoUrl;
                    mBean.level = level;
                    mBean.area = mSpeciality;

                    mContent.add(mBean);
                    mAdapter.notifyDataSetChanged();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.serach_content_layout);
        empty_layout = (RelativeLayout) findViewById(R.id.empty_layout);
        keywords = getIntent().getStringExtra("keywords");
        getSerachContent(keywords, pageId);
        mBack_layout = (LinearLayout) findViewById(R.id.ll_section_title_back);
        mBack_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mTitle = (TextView) findViewById(R.id.tv_section_title_title);
        mTitle.setText("搜索结果");
        mHandler = new Handler();
        mListView = (XListView) findViewById(R.id.serach_content_list);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra("userId", mContent.get(i - 1).getUserid());
                intent.setClass(SerachContentActivity.this, HomePageActivity.class);
                startActivity(intent);
            }
        });
        mListView.setPullLoadEnable(true);
        mListView.setXListViewListener(this);

        mContent = new ArrayList<>();

        mAdapter = new SerachAdapter(this, mContent);
        mListView.setAdapter(mAdapter);

    }

    private void getSerachContent(String keyWorlds, int pageId) {

        String url = "http://210.51.190.27:8082/search.jspa";
        OkHttpUtils
                .post()
                .url(url)
                .addParams("type", "1")
                .addParams("keyword", keyWorlds)
                .addParams("pageId", pageId + "")
                .build()
                .execute(new SerachResult());
    }

    private void onLoad() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
        mListView.setRefreshTime("刚刚");
    }

    @Override
    public void onRefresh() {

        new Handler().postDelayed(new Runnable() {
            public void run() {
                start = ++refreshCnt;
                mContent.clear();
                getSerachContent(keywords, pageId);
                mAdapter = new SerachAdapter(SerachContentActivity.this, mContent);
                mListView.setAdapter(mAdapter);
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

                    getSerachContent(keywords, ++pageId);
                    mAdapter.notifyDataSetChanged();
                    onLoad();
                } else {

                    onLoad();
                    Toast.makeText(SerachContentActivity.this, "已经到底了！", Toast.LENGTH_LONG).show();
                    mListView.setPullLoadEnable(false);
                }

            }
        }, 2000);
    }
}
