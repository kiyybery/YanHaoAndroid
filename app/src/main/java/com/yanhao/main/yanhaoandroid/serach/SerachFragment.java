package com.yanhao.main.yanhaoandroid.serach;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.YanHao;
import com.yanhao.main.yanhaoandroid.adapter.SelectAdapter;
import com.yanhao.main.yanhaoandroid.adapter.SerachAdapter;
import com.yanhao.main.yanhaoandroid.adapter.SerachHistoryAdapter;
import com.yanhao.main.yanhaoandroid.bean.ConstantBean;
import com.yanhao.main.yanhaoandroid.bean.SelectBean;
import com.yanhao.main.yanhaoandroid.bean.UserInfo;
import com.yanhao.main.yanhaoandroid.homepage.HomePageActivity;
import com.yanhao.main.yanhaoandroid.util.RelayoutViewTool;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/1 0001.
 */
public class SerachFragment extends Fragment implements View.OnClickListener {

    private List<ConstantBean> mContent;
    private ListView mListView;
    private SerachAdapter mAdapter;
    private ConstantBean mBean;
    private ImageView serach_img;
    private EditText serach_et;
    private RelativeLayout mCancel_layout;

    private class SerachResult extends StringCallback {

        @Override
        public void onError(Request request, Exception e) {

        }

        @Override
        public void onResponse(String s) {
            //Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray array = jsonObject.getJSONArray("counselorList");
                for (int i = 0; i < array.length(); i++) {

                    JSONObject job = (JSONObject) array.get(i);
                    String name = job.getString("name");
                    String photoUrl = job.getString("portraitUrl");
                    String userId = job.getString("userId");
                    String speciality = job.getString("speciality");
                    int level = job.getInt("level");

                    mBean = new ConstantBean();
                    mBean.userid = userId;
                    mBean.constantName = name;
                    mBean.imageview = photoUrl;
                    mBean.level = level;
                    mBean.area = speciality;

                    mContent.add(mBean);
                    mAdapter.notifyDataSetChanged();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public static SerachFragment newInstance() {

        SerachFragment fragment = new SerachFragment();
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

        View view = inflater.inflate(R.layout.fragment_serach, container, false);
        RelayoutViewTool.relayoutViewWithScale(view, YanHao.screenWidthScale);

        /*Spinner spinner2 = (Spinner) view.findViewById(R.id.spinner2);
        List<SelectBean> selectBeanList = new ArrayList<>();
        selectBeanList.add(new SelectBean("咨询师"));
        selectBeanList.add(new SelectBean("全部"));
        selectBeanList.add(new SelectBean("机构"));*/

        /*SelectAdapter selectAdapter = new SelectAdapter(getActivity(), selectBeanList);
        spinner2.setAdapter(selectAdapter);*/
        mCancel_layout = (RelativeLayout) view.findViewById(R.id.cencel_layout);
        mCancel_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        serach_et = (EditText) view.findViewById(R.id.serach_et);
        serach_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mListView = (ListView) view.findViewById(R.id.history_serach_list);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra("userId", mContent.get(i).getUserid());
                intent.setClass(getActivity(), HomePageActivity.class);
                startActivity(intent);
            }
        });

        serach_img = (ImageView) view.findViewById(R.id.serach_iv);
        serach_img.setOnClickListener(this);

        mContent = new ArrayList<>();
        /*for (int i = 0; i < 6; i++) {
            SelectBean select = new SelectBean();
            select.setText("记录" + i);
            mContent.add(select);
        }*/
        mAdapter = new SerachAdapter(getActivity(), mContent);
        mListView.setAdapter(mAdapter);

        return view;
    }


    public void serach() throws UnsupportedEncodingException {

        String keywords = URLEncoder.encode("李璐", "utf-8");

        String url = "http://7xop51.com1.z0.glb.clouddn.com/serach.txt";
        OkHttpUtils
                .postString()
                .url(url)
                .content(new Gson().toJson(new UserInfo()))
                .build()
                .execute(new SerachResult());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.serach_iv:
                try {
                    serach();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
