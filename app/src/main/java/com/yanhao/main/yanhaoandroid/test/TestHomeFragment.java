package com.yanhao.main.yanhaoandroid.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.adapter.TestAdapter;
import com.yanhao.main.yanhaoandroid.bean.TestBean;
import com.yanhao.main.yanhaoandroid.util.PrefHelper;
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
public class TestHomeFragment extends Fragment {

    private ListView mListview;
    private View view;
    private List<TestBean> mList;
    private TestBean mTestBean;
    private TestAdapter mAdapter;

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
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("testScaleList");
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
                    mAdapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_testhome, container, false);
        mListview = (ListView) view.findViewById(R.id.testhome_listview);
        getTestItem();
        mList = new ArrayList<>();
        mAdapter = new TestAdapter(mList, getActivity());
        return view;
    }

    private void getTestItem() {

        String url = "http://210.51.190.27:8082/getTestHome.jspa";
        //String url = YanHao.api_base + "getTestHome.jspa";
        String id = PrefHelper.get().getString("userId", "");
        Toast.makeText(getActivity(), id + " is test", Toast.LENGTH_LONG).show();
        Log.i("test_id", PrefHelper.get().getString("userId", ""));
        OkHttpUtils
                .post()
                .url(url)
                .addParams("userId", id)
                .build()
                .execute(new MyTestCallback());
    }
}
