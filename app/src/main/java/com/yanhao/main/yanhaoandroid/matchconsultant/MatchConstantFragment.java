package com.yanhao.main.yanhaoandroid.matchconsultant;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.YanHao;
import com.yanhao.main.yanhaoandroid.adapter.ConstantAdapter;
import com.yanhao.main.yanhaoandroid.banner.T;
import com.yanhao.main.yanhaoandroid.bean.ConstantBean;
import com.yanhao.main.yanhaoandroid.bean.UserInfo;
import com.yanhao.main.yanhaoandroid.homepage.HomePageActivity;
import com.yanhao.main.yanhaoandroid.util.XListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2015/11/2 0002.
 */
public class MatchConstantFragment extends Fragment implements
        android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener, XListView.IXListViewListener {

    private XListView mConstantListView;
    private List<ConstantBean> mList;
    private ConstantBean mConstantBean;
    private List mSpecialityList;
    String userId;

    private final static int mMsgAnimStart = 1;
    private final static int mMsgAnimStop = 2;

    AnimationSet set;
    AnimationSet setLeft;
    AnimationSet setRight;
    RelativeLayout mRelativeLayout;

    private ImageView mBackImage;
    private TextView mTitle;
    private ConstantAdapter constantAdapter;

    SwipeRefreshLayout swipe;

    String titleName;
    String itemName;

    private Handler mHandler;
    private int start = 0;
    private static int refreshCnt = 0;

    private int pageId = 1;
    private int count = 10;
    private int countPerPage;
    private JSONArray jsonArray;
    private LinearLayout mBack;

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
            //Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
            try {
                JSONObject jsonObject = new JSONObject(s);
                countPerPage = jsonObject.getInt("countPerPage");
                jsonArray = jsonObject.getJSONArray("counselorList");

                for (int i = 0; i < jsonArray.length(); i++) {
                    Log.i("jsonArray_length", jsonArray.length() + "");
                    mConstantBean = new ConstantBean();
                    JSONObject jo = (JSONObject) jsonArray.get(i);
                    // T.show(getActivity(), jo.keys() + "", 1000);
                    /*Iterator<String> iterator = jo.keys();
                    while(iterator.hasNext()){
                        Log.i("keys",iterator.next());
                    }*/

                    /*Log.i("level=",level+"");
                    Toast.makeText(getActivity(), level+"", Toast.LENGTH_LONG).show();*/
                    String name = jo.getString("name");
                    int level = jo.getInt("level");
                    String photo = jo.getString("portraitUrl");
                    userId = jo.getString("userId");
                    JSONArray speciality = jo.getJSONArray("speciality");
                    for (int j = 0; j < speciality.length(); j++) {
                        speciality.optString(j);
                        mConstantBean.area = speciality.optString(j);
                        //Toast.makeText(getActivity(), "info" + userId + name + photo + speciality.optString(j), Toast.LENGTH_LONG).show();
                    }
                    mConstantBean.userid = userId;
                    mConstantBean.constantName = name;
                    mConstantBean.level = level;
                    mConstantBean.imageview = photo;
                    mList.add(mConstantBean);
                }


                constantAdapter.notifyDataSetChanged();
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
        titleName = getArguments().getString("titlename");
        itemName = getArguments().getString("item");
        //Toast.makeText(getActivity(), "fragment get values = " + titleName + " " + itemName, Toast.LENGTH_LONG).show();
        //getContants();
        //getConstants();
        getCounselor(titleName, itemName, pageId);
        mSpecialityList = new ArrayList<>();
        mList = new ArrayList<>();
        mHandler = new Handler();

        mConstantListView = (XListView) view.findViewById(R.id.match_listview);
        mConstantListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getActivity(), HomePageActivity.class);
                intent.putExtra("userId", mList.get(i - 1).userid);
                Log.i("consultorid", mList.get(i - 1).userid);
                //Toast.makeText(getActivity(), "userId" + mList.get(i).userid, Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });
        mConstantListView.setPullLoadEnable(true);
        mConstantListView.setXListViewListener(this);
        constantAdapter = new ConstantAdapter(getActivity(), mList);
        mConstantListView.setAdapter(constantAdapter);
        mBackImage = (ImageView) view.findViewById(R.id.iv_section_title_back);
        mBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        mBack = (LinearLayout) view.findViewById(R.id.ll_section_title_back);
        mBack.setOnClickListener(new View.OnClickListener() {
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

    private void getCounselor(String category, String item, int pageId) {

        String url = "http://210.51.190.27:8082/matchCounselor.jspa";
        //String url = YanHao.api_base + "matchCounselor.jspa";
        OkHttpUtils
                .post()//
                .url(url)//
                .addParams("category", item)//
                .addParams("item", category)//
                .addParams("pageId", pageId + "")
                .build()//
                .execute(new GetContantsCallBack());
    }

    private void onLoad() {
        mConstantListView.stopRefresh();
        mConstantListView.stopLoadMore();
        mConstantListView.setRefreshTime("刚刚");
    }

    @Override
    public void onRefresh() {

        new Handler().postDelayed(new Runnable() {
            public void run() {
                start = ++refreshCnt;
                //mList.clear();
                getCounselor(titleName, itemName, pageId);
                constantAdapter = new ConstantAdapter(getActivity(), mList);
                mConstantListView.setAdapter(constantAdapter);
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

                    getCounselor(titleName, itemName, ++pageId);
                    constantAdapter.notifyDataSetChanged();
                    onLoad();
                } else {

                    onLoad();
                    Toast.makeText(getActivity(), "已经到底了！", Toast.LENGTH_LONG).show();
                    mConstantListView.setPullLoadEnable(false);
                }

            }
        }, 2000);
    }

}
