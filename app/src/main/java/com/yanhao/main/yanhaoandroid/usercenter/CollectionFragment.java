package com.yanhao.main.yanhaoandroid.usercenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.WebViewActivity;
import com.yanhao.main.yanhaoandroid.YanHao;
import com.yanhao.main.yanhaoandroid.adapter.MyCollectionAdapter;
import com.yanhao.main.yanhaoandroid.adapter.ReadAdapter;
import com.yanhao.main.yanhaoandroid.bean.CollectionBean;
import com.yanhao.main.yanhaoandroid.bean.MyCollection;
import com.yanhao.main.yanhaoandroid.util.RelayoutViewTool;
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

    private TextView mTitle;
    private LinearLayout mBack;
    private MyCollectionAdapter mAdapter;
    ProgressBar progressbar;

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
                for (int i = 0; i < array.length(); i++) {
                    mBean = new MyCollection();
                    JSONObject job = (JSONObject) array.get(i);
                    mBean.title = job.getString("title");
                    mBean.imageUrl = job.getString("imageUrl");
                    mBean.contentUrl = job.getString("contentUrl");

                    mList.add(mBean);
                }
                mAdapter.notifyDataSetChanged();
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

        //getReadInfo();
        getFavorContent();
        mListView = (ListView) view.findViewById(R.id.list_collection);

        mList = new ArrayList<>();
        progressbar = (ProgressBar) view.findViewById(R.id.progressbar);
        mAdapter = new MyCollectionAdapter(mList, getActivity());
        mListView.setAdapter(mAdapter);
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
        OkHttpUtils
                .post()
                .url(url)
                .addParams("userId", "WbqhSt2gqUU=")
                .addParams("type", "0")
                .addParams("lastId", "0")
                .build()
                .execute(new GetFavorContent());
    }
}
