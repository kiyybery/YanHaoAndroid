package com.yanhao.main.yanhaoandroid.usercenter;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.okhttp.Request;
import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.YanHao;
import com.yanhao.main.yanhaoandroid.adapter.MyTestAdapter;
import com.yanhao.main.yanhaoandroid.banner.MyTest;
import com.yanhao.main.yanhaoandroid.test.WebViewTest;
import com.yanhao.main.yanhaoandroid.util.MyLetterListView;
import com.yanhao.main.yanhaoandroid.util.PrefHelper;
import com.yanhao.main.yanhaoandroid.util.RelayoutViewTool;
import com.yanhao.main.yanhaoandroid.util.SwipeLayoutAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/1/28 0028.
 */
public class MyTestFragment extends Fragment {

    private View view;
    private List<MyTest> mList;
    private MyTest mBean;
    private ListView mListView;
    private MyTestAdapter mAdapter;
    private MySwipeAdapater mSwipeAdapater;
    private LinearLayout mBack;
    private TextView mTitle;
    private ProgressBar mProgressBar;
    private GridView mGridView;
    private RelativeLayout empty_layout;

    private class GetContent extends StringCallback {

        @Override
        public void onError(Request request, Exception e) {

        }

        @Override
        public void onResponse(String s) {

            try {
                JSONObject jsonObject = new JSONObject(s);
                mProgressBar.setVisibility(View.GONE);
                JSONArray array = jsonObject.getJSONArray("favorContentList");
                if (array.length() == 0) {

                    empty_layout.setVisibility(View.VISIBLE);
                }
                for (int i = 0; i < array.length(); i++) {

                    JSONObject job = (JSONObject) array.get(i);
                    mBean = new MyTest();
                    mBean.id = job.getInt("id");
                    mBean.imageUrl = job.getString("imageUrl");
                    mBean.name = job.getString("name");
                    mBean.scaleId = job.getInt("scaleId");
                    mBean.score = job.getInt("score");
                    mBean.subScaleId = job.getInt("subScaleId");
                    mBean.createTime = job.getString("createTime");

                    mList.add(mBean);
                }
                mSwipeAdapater.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static MyTestFragment newInstance() {
        MyTestFragment fragment = new MyTestFragment();
        Bundle data = new Bundle();
        data.putString("title", " ");
        fragment.setArguments(data);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_test_my, container, false);
        RelayoutViewTool.relayoutViewWithScale(view, YanHao.screenWidthScale);
        empty_layout = (RelativeLayout) view.findViewById(R.id.empty_layout);
        mList = new ArrayList<>();
        getContent();
        mListView = (ListView) view.findViewById(R.id.my_test_lv);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String webUrl = "http://210.51.190.27:8082/viewScaleFeedback.jspa?" +
                        "id=" + mList.get(i).id + "&" +
                        "userId=" + PrefHelper.get().getString("userId", "") + "&" +
                        "scaleId=" + mList.get(i).scaleId + "&" +
                        "subScaleId=" + mList.get(i).subScaleId + "&" +
                        "score=" + mList.get(i).score;

                Intent intent = new Intent();
                intent.putExtra("webUrl", webUrl);
                //intent.putExtra("webUrl", "http://test.izhipeng.com/html/Explain.html");
                intent.setClass(getActivity(), WebViewTest.class);
                startActivity(intent);
            }
        });
        /*mAdapter = new MyTestAdapter(getActivity(), mList);
        mListView.setAdapter(mAdapter);*/

        mSwipeAdapater = new MySwipeAdapater(getActivity(), R.layout.test_item, R.layout.item_action, mList);
        mListView.setAdapter(mSwipeAdapater);
        mBack = (LinearLayout) view.findViewById(R.id.ll_section_title_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        mTitle = (TextView) view.findViewById(R.id.tv_section_title_title);
        mTitle.setText("我的测试");
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressbar_my_test);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void getContent() {

        String url = "http://210.51.190.27:8082/getFavorContent.jspa";
        OkHttpUtils
                .post()
                .url(url)
                .addParams("userId", PrefHelper.get().getString("userId", ""))
                .addParams("type", "1")
                .addParams("lastId", "0")
                .build()
                .execute(new GetContent());
    }

    class MySwipeAdapater extends SwipeLayoutAdapter<MyTest> {
        private List<MyTest> _data;

        public MySwipeAdapater(Activity context, int contentViewResourceId, int actionViewResourceId, List<MyTest> objects) {
            super(context, contentViewResourceId, actionViewResourceId, objects);
            _data = objects;
        }

        //实现setContentView方法
        @Override
        public void setContentView(View contentView, final int position, HorizontalScrollView parent) {

            ImageView imageView = (ImageView) contentView.findViewById(R.id.img_test_my);
            TextView textView = (TextView) contentView.findViewById(R.id.title_tv_my);
            TextView time_tv = (TextView) contentView.findViewById(R.id.answer_num_my);

            Glide.with(getActivity()).load(_data.get(position).imageUrl).into(imageView);
            textView.setText(_data.get(position).name);
            time_tv.setText(_data.get(position).createTime);

            contentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String webUrl = "http://210.51.190.27:8082/viewScaleFeedback.jspa?" +
                            "id=" + mList.get(position).id + "&" +
                            "userId=" + PrefHelper.get().getString("userId", "") + "&" +
                            "scaleId=" + mList.get(position).scaleId + "&" +
                            "subScaleId=" + mList.get(position).subScaleId + "&" +
                            "score=" + mList.get(position).score;

                    Intent intent = new Intent();
                    intent.putExtra("webUrl", webUrl);
                    //intent.putExtra("webUrl", "http://test.izhipeng.com/html/Explain.html");
                    intent.setClass(getActivity(), WebViewTest.class);
                    startActivity(intent);
                }
            });
        }

        //实现setActionView方法
        @Override
        public void setActionView(View actionView, final int position, final HorizontalScrollView parent) {

            actionView.findViewById(R.id.star).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parent.scrollTo(0, 0);
                    _data.remove(position);
                    notifyDataSetChanged();
                }
            });

            /*actionView.findViewById(R.id.star).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this,"star item - " + position,Toast.LENGTH_SHORT).show();
                }
            });*/

        }
    }
}
