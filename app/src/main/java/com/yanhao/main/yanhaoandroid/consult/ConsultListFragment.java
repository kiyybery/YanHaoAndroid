package com.yanhao.main.yanhaoandroid.consult;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.adapter.ConsultListAdapter;
import com.yanhao.main.yanhaoandroid.bean.ConsultListBean;
import com.yanhao.main.yanhaoandroid.bean.UserInfo;
import com.yanhao.main.yanhaoandroid.matchconsultant.MatchConsultantActivity;
import com.yanhao.main.yanhaoandroid.util.CustomProgressDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/2 0002.
 */
public class ConsultListFragment extends Fragment {

    private ListView mConsultListView;
    private List<String> mList;
    private ConsultListBean mConsultListBean;
    private ImageView mBackImage;
    private TextView mTitle;
    private ConsultListAdapter consultListAdapter;

    public static ConsultListFragment newInstance() {

        ConsultListFragment fragment = new ConsultListFragment();
        Bundle data = new Bundle();
        data.putString("title", " ");
        fragment.setArguments(data);
        return fragment;
    }

    class MyCallback extends StringCallback {

        @Override
        public void onError(Request request, Exception e) {

        }

        @Override
        public void onResponse(String s) {

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray ja = jsonObject.getJSONArray("wordsList");
                for (int i = 0; i < ja.length(); i++) {
                    mList.add(ja.optString(i));
                }

                mConsultListView.setAdapter(consultListAdapter);
                consultListAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_consultlist, container, false);

        int itemId = getArguments().getInt("itemId", 0);
        final String itemName = getArguments().getString("itemName");

        //Toast.makeText(getActivity(), "activity say :" + itemId + "," + itemName, Toast.LENGTH_LONG).show();
        mList = new ArrayList<>();
        /*for (int i = 0; i < 8; i++) {
            mConsultListBean = new ConsultListBean();
            mConsultListBean.setText("情绪障碍" + i);
            mList.add(mConsultListBean);
        }*/
        if (itemName.equals("青少年儿童")) {
            postString();
        } else if (itemName.equals("婚姻关系")) {
            postStringWordList();
        } else if (itemName.equals("职场困惑")) {

            postStringWordList3();
        } else if (itemName.equals("情绪与神经症")) {

            postStringWordList4();
        } else if (itemName.equals("人际社交")) {

            postStringWordList5();
        } else if (itemName.equals("个人成长")) {

            postStringWordList6();
        } else if (itemName.equals("性方面")) {

            postStringWordList7();
        } else if (itemName.equals("其他")) {

            Intent intent = new Intent(getActivity(), MatchConsultantActivity.class);
            startActivity(intent);
        }

        mConsultListView = (ListView) view.findViewById(R.id.consult_listview);
        consultListAdapter = new ConsultListAdapter(mList, getActivity());

        mConsultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String titleName = mList.get(i);
                Intent intent = new Intent(getActivity(), MatchConsultantActivity.class);
                intent.putExtra("titlename", titleName);
                intent.putExtra("item", itemName);
                startActivity(intent);
            }
        });

        mBackImage = (ImageView) view.findViewById(R.id.iv_section_title_back);
        mBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        mTitle = (TextView) view.findViewById(R.id.tv_section_title_title);
        mTitle.setText(itemName);
        return view;
    }

    private void postString() {

        String url = "http://7xop51.com1.z0.glb.clouddn.com/keywords_2_list.txt";
        OkHttpUtils
                .postString()
                .url(url)
                .content(new Gson().toJson(new UserInfo("zhy", "123")))
                .build()
                .execute(new MyCallback());
    }

    private void postStringWordList() {

        String url = "http://7xop51.com1.z0.glb.clouddn.com/keywords_list.txt";
        OkHttpUtils
                .postString()
                .url(url)
                .content(new Gson().toJson(new UserInfo("zhy", "123")))
                .build()
                .execute(new MyCallback());
    }

    private void postStringWordList3() {

        String url = "http://7xop51.com1.z0.glb.clouddn.com/keywords_3.txt";
        OkHttpUtils
                .postString()
                .url(url)
                .content(new Gson().toJson(new UserInfo("zhy", "123")))
                .build()
                .execute(new MyCallback());
    }

    private void postStringWordList4() {

        String url = "http://7xop51.com1.z0.glb.clouddn.com/keywords_4.txt";
        OkHttpUtils
                .postString()
                .url(url)
                .content(new Gson().toJson(new UserInfo("zhy", "123")))
                .build()
                .execute(new MyCallback());
    }

    private void postStringWordList5() {

        String url = "http://7xop51.com1.z0.glb.clouddn.com/keywords_5.txt";
        OkHttpUtils
                .postString()
                .url(url)
                .content(new Gson().toJson(new UserInfo("zhy", "123")))
                .build()
                .execute(new MyCallback());
    }

    private void postStringWordList6() {

        String url = "http://7xop51.com1.z0.glb.clouddn.com/keywords_6.txt";
        OkHttpUtils
                .postString()
                .url(url)
                .content(new Gson().toJson(new UserInfo("zhy", "123")))
                .build()
                .execute(new MyCallback());
    }

    private void postStringWordList7() {

        String url = "http://7xop51.com1.z0.glb.clouddn.com/keywords_7.txt";
        OkHttpUtils
                .postString()
                .url(url)
                .content(new Gson().toJson(new UserInfo("zhy", "123")))
                .build()
                .execute(new MyCallback());
    }
}
