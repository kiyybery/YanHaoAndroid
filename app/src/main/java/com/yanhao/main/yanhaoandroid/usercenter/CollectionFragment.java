package com.yanhao.main.yanhaoandroid.usercenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.yanhao.main.yanhaoandroid.WebViewActivity;
import com.yanhao.main.yanhaoandroid.YanHao;
import com.yanhao.main.yanhaoandroid.adapter.MyCollectionAdapter;
import com.yanhao.main.yanhaoandroid.adapter.ReadAdapter;
import com.yanhao.main.yanhaoandroid.banner.MyTest;
import com.yanhao.main.yanhaoandroid.bean.CollectionBean;
import com.yanhao.main.yanhaoandroid.bean.MyCollection;
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
 * Created by Administrator on 2016/1/25 0025.
 */
public class CollectionFragment extends Fragment {

    private List<MyCollection> mList;
    private ListView mListView;
    private MyCollection mBean;
    private MySwipeAdapater mSwipeAdapater;
    private TextView mTitle;
    private LinearLayout mBack;
    private MyCollectionAdapter mAdapter;
    ProgressBar progressbar;
    private RelativeLayout empty_layout;

    private class GetFavorContent extends StringCallback {


        @Override
        public void onError(Request request, Exception e) {

        }

        @Override
        public void onResponse(String s) {

            try {
                JSONObject jsonObject = new JSONObject(s);
                progressbar.setVisibility(View.GONE);
                JSONArray array = jsonObject.getJSONArray("favorContentList");
                if (array.length() == 0) {

                    empty_layout.setVisibility(View.VISIBLE);
                }
                for (int i = 0; i < array.length(); i++) {
                    mBean = new MyCollection();
                    JSONObject job = (JSONObject) array.get(i);
                    mBean.title = job.getString("title");
                    mBean.imageUrl = job.getString("imageUrl");
                    mBean.contentUrl = job.getString("contentUrl");

                    mList.add(mBean);
                }
                mSwipeAdapater.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public static CollectionFragment newInstance() {

        CollectionFragment fragment = new CollectionFragment();
        Bundle data = new Bundle();
        data.putString("title", " ");
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collection_my, container, false);
        RelayoutViewTool.relayoutViewWithScale(view, YanHao.screenWidthScale);

        empty_layout = (RelativeLayout) view.findViewById(R.id.empty_layout);
        //getReadInfo();
        getFavorContent();
        mListView = (ListView) view.findViewById(R.id.list_collection);

        mList = new ArrayList<>();
        progressbar = (ProgressBar) view.findViewById(R.id.progressbar);
        //mAdapter = new MyCollectionAdapter(mList, getActivity());
        mSwipeAdapater = new MySwipeAdapater(getActivity(), R.layout.mycollection_item, R.layout.item_action, mList);
        mListView.setAdapter(mSwipeAdapater);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent();
                intent.putExtra("webUrl", mList.get(i).getContentUrl());
                intent.setClass(getActivity(), WebViewActivity.class);
                startActivity(intent);
            }
        });


        mTitle = (TextView) view.findViewById(R.id.tv_section_title_title);
        mTitle.setText("我的收藏");
        mBack = (LinearLayout) view.findViewById(R.id.ll_section_title_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        return view;
    }

    private void getFavorContent() {

        String url = "http://210.51.190.27:8082/getFavorContent.jspa";
        //String url = YanHao.api_base + "getFavorContent.jspa";
        String id = PrefHelper.get().getString("userId", "");
        Log.i("collection_userid", id);
        OkHttpUtils
                .post()
                .url(url)
                .addParams("userId", PrefHelper.get().getString("userId", ""))
                .addParams("type", "0")
                .addParams("lastId", "0")
                .build()
                .execute(new GetFavorContent());
    }

    class MySwipeAdapater extends SwipeLayoutAdapter<MyCollection> {
        private List<MyCollection> _data;

        public MySwipeAdapater(Activity context, int contentViewResourceId, int actionViewResourceId, List<MyCollection> objects) {
            super(context, contentViewResourceId, actionViewResourceId, objects);
            _data = objects;
        }

        //实现setContentView方法
        @Override
        public void setContentView(View contentView, final int position, HorizontalScrollView parent) {

            ImageView imageView = (ImageView) contentView.findViewById(R.id.mycollection_img);
            TextView textView = (TextView) contentView.findViewById(R.id.title_tv);

            textView.setText(_data.get(position).title);
            Glide.with(getActivity()).load(_data.get(position).imageUrl).into(imageView);

            contentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent();
                    intent.putExtra("webUrl", mList.get(position).getContentUrl());
                    intent.setClass(getActivity(), WebViewActivity.class);
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
