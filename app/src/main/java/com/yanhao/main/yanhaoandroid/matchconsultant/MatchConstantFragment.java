package com.yanhao.main.yanhaoandroid.matchconsultant;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.YanHao;
import com.yanhao.main.yanhaoandroid.adapter.ConstantAdapter;
import com.yanhao.main.yanhaoandroid.bean.ConstantBean;
import com.yanhao.main.yanhaoandroid.bean.UserInfo;
import com.yanhao.main.yanhaoandroid.homepage.HomePageActivity;
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
public class MatchConstantFragment extends Fragment {

    private ListView mConstantListView;
    private List<ConstantBean> mList;
    private ConstantBean mConstantBean;
    private List mSpecialityList;

    private final static int mMsgAnimStart = 1;
    private final static int mMsgAnimStop = 2;

    AnimationSet set;
    AnimationSet setLeft;
    AnimationSet setRight;
    RelativeLayout mRelativeLayout;

    private ImageView mBackImage;
    private TextView mTitle;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case mMsgAnimStop:
                    mRelativeLayout.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }
    };

    private class GetContantsCallBack extends StringCallback {


        @Override
        public void onError(Request request, Exception e) {

        }

        @Override
        public void onResponse(String s) {

            Toast.makeText(getActivity(), s + "s", Toast.LENGTH_LONG).show();
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray counselorList = jsonObject.getJSONArray("counselorList");
                for (int i = 0; i < counselorList.length(); i++) {

                    JSONObject jo = counselorList.getJSONObject(i);
                    mConstantBean = new ConstantBean();
                    int userId = jo.getInt("userId");
                    String name = jo.getString("name");
                    String level = jo.getString("level");
                    String photoUrl = jo.getString("photoUrl");
                    JSONArray ja = jo.getJSONArray("speciality");
                    for (int j = 0; j < ja.length(); j++) {
                        mConstantBean.area = ja.optString(j);
                    }
                    mConstantBean.constantName = jo.getString("name");
                    mConstantBean.imageview = jo.getString("photoUrl");

                    mList.add(mConstantBean);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static MatchConstantFragment newInstance() {
        MatchConstantFragment fragment = new MatchConstantFragment();
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
        View view = inflater.inflate(R.layout.fragment_matchconsultant, container, false);
        String titleName = getArguments().getString("titlename");

        getContants();
        mSpecialityList = new ArrayList<>();
        mList = new ArrayList<>();
        /*for (int i = 0; i < 15; i++) {
            mConstantBean = new ConstantBean();
            mConstantBean.setConstantName("大师" + i);
            //mConstantBean.setLevel("国家二级" + i);
            mConstantBean.setArea("亲子关系 婚姻困惑 三角恋" + i);
            mList.add(mConstantBean);
        }*/
        mConstantListView = (ListView) view.findViewById(R.id.match_listview);
        mConstantListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getActivity(), HomePageActivity.class);
                startActivity(intent);
            }
        });
        mConstantListView.setAdapter(new ConstantAdapter(getActivity(), mList));

        mBackImage = (ImageView) view.findViewById(R.id.iv_section_title_back);
        mBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        mTitle = (TextView) view.findViewById(R.id.tv_section_title_title);
        mTitle.setText("匹配到的咨询师");
        mRelativeLayout = (RelativeLayout) view.findViewById(R.id.anim_layout);
        set = (AnimationSet) AnimationUtils.loadAnimation(getActivity(), R.anim.gear_anim);
        setLeft = (AnimationSet) AnimationUtils.loadAnimation(getActivity(), R.anim.gear_anim_left);
        setRight = (AnimationSet) AnimationUtils.loadAnimation(getActivity(), R.anim.gear_anim_right);

        ImageView gearIV = (ImageView) view.findViewById(R.id.gear_iv);
        ImageView gearIVLeft = (ImageView) view.findViewById(R.id.gear_iv_left);
        ImageView gearIVRight = (ImageView) view.findViewById(R.id.gear_iv_right);
        gearIV.startAnimation(set);
        gearIVLeft.startAnimation(setLeft);
        gearIVRight.startAnimation(setRight);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    handler.sendEmptyMessage(mMsgAnimStop);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return view;
    }

    public void getContants() {

        String url = YanHao.TEST_URL + "matchCounselor.jspa?" +
                "firstMatchInfo=liveQuestion" + "&&" + "secondMatchInfo=maryQuestion";
        OkHttpUtils.postString()
                .url(url)
                .content(new Gson().toJson(new UserInfo()))
                .build()
                .execute(new GetContantsCallBack());
    }
}
