package com.yanhao.main.yanhaoandroid.homepage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.YanHao;
import com.yanhao.main.yanhaoandroid.banner.T;
import com.yanhao.main.yanhaoandroid.bean.UserInfo;
import com.yanhao.main.yanhaoandroid.consult.ConsultFragment;
import com.yanhao.main.yanhaoandroid.login.LoginActivity;
import com.yanhao.main.yanhaoandroid.matchconsultant.OrderContantActivity;
import com.yanhao.main.yanhaoandroid.util.CircleImageView;
import com.yanhao.main.yanhaoandroid.util.RelayoutViewTool;
import com.yanhao.main.yanhaoandroid.util.Type;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2015/12/1 0001.
 */
public class HomePageFragment extends Fragment implements View.OnClickListener {

    private Button mOrderbtn;
    private RelativeLayout mBackGround_layout;
    private LinearLayout mBack_layout;
    private CircleImageView mTitleImg;
    private TextView mName, mAddress, mMyProfire, mEduction, mText1;
    private List<String> specialityList = new ArrayList();
    private List<String> chargeList = new ArrayList();
    private RecyclerView mRecycleView, mRecycleView_charge;
    String userId;
    MyAdapter myAdapter;
    ChargeAdapter myAdapter_charge;
    private ProgressBar progressBar;
    private SharedPreferences sp;
    private String sub_userId;
    private final static int REQUEST_MODIFY_NOLOGIN = 7001;


    public static HomePageFragment newInstance() {

        HomePageFragment fragment = new HomePageFragment();
        Bundle data = new Bundle();
        data.putString("title", " ");
        fragment.setArguments(data);
        return fragment;
    }

    private class MyStringCallBack extends StringCallback {


        @Override
        public void onError(Request request, Exception e) {

        }

        @Override
        public void onResponse(String s) {

            try {
                JSONObject jsonObject = new JSONObject(s);
                progressBar.setVisibility(View.GONE);
                userId = jsonObject.getString("userId");
                //int userType = jsonObject.getInt("userType");
                String nick_name = jsonObject.getString("name");
                String photoUrl = jsonObject.getString("portraitUrl");
                String address = jsonObject.getString("city");
                //String intro = jsonObject.getString("intro");
                String education = jsonObject.getString("education");
                JSONArray jsonArray = jsonObject.getJSONArray("specialityList");
                String str = jsonArray.toString();
                Log.i("str_list", str + "");


                for (int i = 0; i < jsonArray.length(); i++) {
                    //specialityList.add(jsonArray.optString(i));
                    String[] str_list = jsonArray.optString(i).split(",");

                    for (int a = 0; a < str_list.length; a++) {

                        String[] a1 = str_list[0].split(",");
                        Log.i("a1", a1 + "");
                    }
                    Log.i("str_list", str_list.length + "");
                    for (int k = 0; k < str_list.length; k++) {
                        specialityList.add(str_list[k]);
                    }

                    String[] a = jsonArray.get(i).toString().split(",");

                    Log.i("string_a", a.toString() + "");
                    //Toast.makeText(getActivity(), "list" + jsonArray.optString(i), Toast.LENGTH_LONG).show();
                }

                JSONArray jsonArray1 = jsonObject.getJSONArray("chargeList");
                String str1 = jsonArray1.toString();
                for (int j = 0; j < jsonArray1.length(); j++) {

                    chargeList.add(jsonArray1.optString(j));
                }
                /*Toast.makeText(getActivity(), "userId" + userId +
                        "nick_name" + nick_name +
                        "address" + address +
                        "intro" + intro +
                        "education" + education +
                        "speciality" + str +
                        "charge" + str1, Toast.LENGTH_LONG).show();*/

                if (!TextUtils.isEmpty(photoUrl)) {

                    Glide
                            .with(HomePageFragment.this)
                            .load(photoUrl)
                                    //.placeholder(R.drawable.avatar_default)
                            .into(mTitleImg);
                } else {

                    //mTitleImg.setBackgroundResource(R.drawable.avatar_default);
                    mTitleImg.setImageResource(R.drawable.avatar_default);
                }

                //Glide.with(HomePageFragment.this).load(photoUrl).into(mTitleImg);
                mName.setText(nick_name);
                mAddress.setText(address);
                /*mMyProfire.setText(intro);*/
                mEduction.setText(education);

                myAdapter.notifyDataSetChanged();
                myAdapter_charge.notifyDataSetChanged();
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

        EventBus.getDefault().register(HomePageFragment.this);
        sp = getActivity().getSharedPreferences("userInfo", getActivity().MODE_PRIVATE);
        sub_userId = sp.getString("userId", "");
        View view = inflater.inflate(R.layout.fragment_homepage_consultant, container, false);
        RelayoutViewTool.relayoutViewWithScale(view, YanHao.screenWidthScale);

        userId = getArguments().getString("userId");

        getUserInfo(userId);

        /*if (userId == 1) {

            postString();
        } else if (userId == 2) {

            postStringhu();
        } else if (userId == 3) {

            postStringlilu();
        } else if (userId == 4) {

            postStringsong();
        }*/

        mBack_layout = (LinearLayout) view.findViewById(R.id.ll_homepage_title_back);
        mBack_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        mOrderbtn = (Button) view.findViewById(R.id.btnOrder);
        mOrderbtn.setOnClickListener(this);

        progressBar = (ProgressBar) view.findViewById(R.id.progressbar_ConsultHome);

        mBackGround_layout = (RelativeLayout) view.findViewById(R.id.homepage_consultant_title_layout);
        mBackGround_layout.setBackgroundResource(R.drawable.homepage_bg);

        mTitleImg = (CircleImageView) view.findViewById(R.id.iv_homepage_avatar);

        mName = (TextView) view.findViewById(R.id.tv_homepage_nickname);
        mAddress = (TextView) view.findViewById(R.id.tv_uc_address);
        mMyProfire = (TextView) view.findViewById(R.id.myprofire_content);
        mEduction = (TextView) view.findViewById(R.id.professional_content);

        mRecycleView = (RecyclerView) view.findViewById(R.id.id_recycleView);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecycleView.setLayoutManager(manager);

        myAdapter = new MyAdapter(getActivity(), specialityList);
        mRecycleView.setAdapter(myAdapter);


        mRecycleView_charge = (RecyclerView) view.findViewById(R.id.id_recycleView_charge);
        LinearLayoutManager manager_charge = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecycleView_charge.setLayoutManager(manager_charge);

        myAdapter_charge = new ChargeAdapter(getActivity(), chargeList);
        mRecycleView_charge.setAdapter(myAdapter_charge);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(HomePageFragment.this);
    }

    public void onEventMainThread(Type type) {
        String msg = "onEventMainThread收到了消息：" + type.getMsg();
        Log.d("harvic", msg);
        if (type.getMsg().equals("11111")) {

            Intent intent = new Intent();
            intent.setClass(getActivity(), OrderContantActivity.class);
            intent.putExtra("userId", userId);
            startActivity(intent);
            getActivity().finish();
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnOrder:
                if (sub_userId.equals("")) {

                    Intent intent = new Intent();
                    intent.setClass(getActivity(), LoginActivity.class);
                    startActivityForResult(intent, REQUEST_MODIFY_NOLOGIN);

                } else {

                    Intent intent = new Intent();
                    intent.setClass(getActivity(), OrderContantActivity.class);
                    intent.putExtra("userId", userId);
                    startActivity(intent);
                }

                break;

            default:
                break;
        }
    }

    public void getUserInfo(String userid) {

        String url = "http://210.51.190.27:8082/getCounselorHome.jspa";
        OkHttpUtils
                .post()//
                .url(url)//
                .addParams("userId", userid)//
                .build()//
                .execute(new MyStringCallBack());
    }

    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> implements View.OnClickListener {

        private LayoutInflater mInflater;
        private Context mContext;

        public MyAdapter(Context context, List<String> list) {

            this.mContext = context;
            specialityList = list;
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = mInflater.inflate(R.layout.speciality_item, viewGroup, false);
            RelayoutViewTool.relayoutViewWithScale(view, YanHao.screenWidthScale);
            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
            myViewHolder.tv.setText(specialityList.get(i));
        }

        @Override
        public int getItemCount() {
            return specialityList.size();
        }

        @Override
        public void onClick(View view) {

        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.item_tv_speciality);
        }
    }

    public class ChargeAdapter extends RecyclerView.Adapter<ChargeViewHolder> {
        private LayoutInflater mInflater;
        private Context mContext;

        public ChargeAdapter(Context context, List list) {

            this.mContext = context;
            chargeList = list;
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public ChargeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = mInflater.inflate(R.layout.charge_item, viewGroup, false);
            RelayoutViewTool.relayoutViewWithScale(view, YanHao.screenWidthScale);
            ChargeViewHolder myViewHolder = new ChargeViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(ChargeViewHolder chargeViewHolder, int i) {
            chargeViewHolder.tv.setText(chargeList.get(i));
        }

        @Override
        public int getItemCount() {
            return chargeList.size();
        }
    }

    class ChargeViewHolder extends RecyclerView.ViewHolder {

        TextView tv;

        public ChargeViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.charge_tv);
        }
    }
}
