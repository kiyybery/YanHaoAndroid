package com.yanhao.main.yanhaoandroid.homepage;

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

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.WebViewActivity;
import com.yanhao.main.yanhaoandroid.YanHao;
import com.yanhao.main.yanhaoandroid.adapter.ReadAdapter;
import com.yanhao.main.yanhaoandroid.bean.CollectionBean;
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
 * Created by Administrator on 2015/12/7 0007.
 */
public class ReadFragment extends Fragment {

    private List<CollectionBean> mList;
    private ListView mListView;
    private CollectionBean mBean;

    private TextView mTitle;
    private LinearLayout mBack;
    private ReadAdapter mAdapter;
    ProgressBar progressbar;
    private String titleName;

    private class MyReadCallback extends StringCallback {


        @Override
        public void onError(Request request, Exception e) {

        }

        @Override
        public void onResponse(String s) {

            try {
                JSONObject jsonObject = new JSONObject(s);
                progressbar.setVisibility(View.GONE);
                JSONArray ja = jsonObject.getJSONArray("readList");
                for (int i = 0; i < ja.length(); i++) {

                    mBean = new CollectionBean();
                    JSONObject job = (JSONObject) ja.get(i);
                    mBean.id = job.getInt("userId");
                    mBean.title = job.getString("title");
                    mBean.imageView = job.getString("photoUrl");
                    mBean.webUrl = job.getString("webUrl");
                    mBean.date = job.getString("createTime");

                    mList.add(mBean);
                }
                mAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static ReadFragment newInstance() {

        ReadFragment fragment = new ReadFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragemnt_read, container, false);
        RelayoutViewTool.relayoutViewWithScale(view, YanHao.screenWidthScale);

        getReadInfo();
        mListView = (ListView) view.findViewById(R.id.read_listview);

        mList = new ArrayList<>();
        /*for (int i = 0; i < 15; i++) {
            mBean = new CollectionBean();
            mBean.setTitle("心理学家如何谈论冥想？" + i);
            mBean.setDate("2015年" + i + "月" + i + "日");
            mList.add(mBean);
        }*/
        progressbar = (ProgressBar) view.findViewById(R.id.progressbar);
        mAdapter = new ReadAdapter(mList, getActivity());
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("webUrl", mList.get(i).webUrl);
                intent.putExtra("imageUrl", mList.get(i).imageView);
                intent.putExtra("title", mList.get(i).title);
                startActivity(intent);
            }
        });
        mTitle = (TextView) view.findViewById(R.id.tv_section_title_title);
        mTitle.setText(titleName);

        mBack = (LinearLayout) view.findViewById(R.id.ll_section_title_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        return view;
    }

    private void getReadInfo() {

        String url = "http://7xop51.com1.z0.glb.clouddn.com/home_read.txt";
        OkHttpUtils
                .postString()
                .url(url)
                .content(new Gson().toJson(new UserInfo("zhy", "123")))
                .build()
                .execute(new MyReadCallback());
    }
}
