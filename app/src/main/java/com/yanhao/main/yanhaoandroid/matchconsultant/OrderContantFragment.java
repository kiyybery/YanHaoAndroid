package com.yanhao.main.yanhaoandroid.matchconsultant;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.YanHao;
import com.yanhao.main.yanhaoandroid.adapter.GridViewAdapter;
import com.yanhao.main.yanhaoandroid.banner.T;
import com.yanhao.main.yanhaoandroid.bean.Type;
import com.yanhao.main.yanhaoandroid.bean.UserInfo;
import com.yanhao.main.yanhaoandroid.homepage.HomePageActivity;
import com.yanhao.main.yanhaoandroid.homepage.PayPageActivity;
import com.yanhao.main.yanhaoandroid.util.CircleImageView;
import com.yanhao.main.yanhaoandroid.util.RelayoutViewTool;
import com.yanhao.main.yanhaoandroid.util.SecurityUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/1 0001.
 */
public class OrderContantFragment extends Fragment implements View.OnClickListener {

    private Button mCommitBtn;
    private RelativeLayout mAsktime_layout;
    private TextView mAsk_tv_face, mAsk_tv_phone, mName, mAddress, mTitle, mAskTime, mAskData;
    private LinearLayout mLayout_face, mLayout_phone;
    private int status = 0;
    private String userId;
    private CircleImageView mImg;
    boolean isSelect;
    private GridView mGridView;
    private List<Type> mList = new ArrayList<>();
    private ImageView mLevel;
    private EditText mPhoneNum_et, mIssue_et, mNote_et;

    public static final int REQUEST_MODIFY_TIME = 100;

    private String[] types = new String[]{"当面咨询", "电话咨询"};
    private GridViewAdapter mAdapter;
    private int reservationId;

    private class MyStringCallback extends StringCallback {

        @Override
        public void onBefore(Request request) {
            super.onBefore(request);
        }

        @Override
        public void onAfter() {
            super.onAfter();
        }

        @Override
        public void onError(Request request, Exception e) {
        }

        @Override
        public void onResponse(String response) {

            try {
                JSONObject jsonObject = new JSONObject(response);
                String nickname = jsonObject.getString("name");
                String address = jsonObject.getString("city");
                String url = jsonObject.getString("portraitUrl");
                int level = jsonObject.getInt("level");

                if (level == 1) {

                    Glide.with(OrderContantFragment.this).load(R.drawable.yiji_icon).into(mLevel);
                } else if (level == 2) {

                    Glide.with(OrderContantFragment.this).load(R.drawable.erji_icon).into(mLevel);
                } else if (level == 3) {

                    Glide.with(OrderContantFragment.this).load(R.drawable.sanji_icon).into(mLevel);
                }
                mName.setText(nickname);
                mAddress.setText(address);
                Glide.with(OrderContantFragment.this).load(url).into(mImg);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void inProgress(float progress) {

        }
    }

    private class ReverveCallback extends StringCallback {

        @Override
        public void onError(Request request, Exception e) {

        }

        @Override
        public void onResponse(String s) {

            //T.show(getActivity(), s, 100);
            try {
                JSONObject jsonObject = new JSONObject(s);
                Toast.makeText(getActivity(), jsonObject.getString("info"), Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static OrderContantFragment newInstance() {

        OrderContantFragment fragment = new OrderContantFragment();
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

        View view = inflater.inflate(R.layout.fragment_orderconstant, container, false);
        RelayoutViewTool.relayoutViewWithScale(view, YanHao.screenWidthScale);

        userId = getArguments().getString("userId");
        //Toast.makeText(getActivity(), "id =" + userId, Toast.LENGTH_SHORT).show();
        //getinfo(userId);
        getUserInfo(userId);
        /*if (userId.equals("1")) {
            getinfobai();
        } else if (userId.equals("2")) {
            getinfohu();

        } else if (userId.equals("3")) {

            getinfolilu();
        } else if (userId.equals("4")) {

            getinfosong();
        }*/

        mPhoneNum_et = (EditText) view.findViewById(R.id.callstyle_time);
        mIssue_et = (EditText) view.findViewById(R.id.problem_time);
        mNote_et = (EditText) view.findViewById(R.id.note_time);
        mTitle = (TextView) view.findViewById(R.id.tv_section_title_title);
        mTitle.setText("预约咨询师");
        mImg = (CircleImageView) view.findViewById(R.id.cv_comment_avatar);
        mName = (TextView) view.findViewById(R.id.consultant_name);
        mAddress = (TextView) view.findViewById(R.id.consult_area);
        mLevel = (ImageView) view.findViewById(R.id.consult_level);

        mAskTime = (TextView) view.findViewById(R.id.asktime_time);
        mAskData = (TextView) view.findViewById(R.id.asktime_data);
        /*mLayout_face = (LinearLayout) view.findViewById(R.id.tv1_layout);
        mLayout_phone = (LinearLayout) view.findViewById(R.id.tv2_layout);*/
        /*mLayout_phone.setOnClickListener(this);
        mLayout_face.setOnClickListener(this);*/

       /* mAsk_tv_face = (TextView) view.findViewById(R.id.ask_tv_1);
        mAsk_tv_face.setOnClickListener(this);
        mAsk_tv_phone = (TextView) view.findViewById(R.id.ask_tv_2);
        mAsk_tv_phone.setOnClickListener(this);*/
        mCommitBtn = (Button) view.findViewById(R.id.btnCommitAsk);
        mCommitBtn.setOnClickListener(this);

        mAsktime_layout = (RelativeLayout) view.findViewById(R.id.asktime_layout);
        mAsktime_layout.setOnClickListener(this);

        mGridView = (GridView) view.findViewById(R.id.type_gradview);
        for (int i = 0; i < types.length; i++) {

            Type type = new Type();
            type.setText(types[i]);
            mList.add(type);

        }
        mAdapter = new GridViewAdapter(getActivity(), mList);
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mAdapter.setSelectedPosition(i);
                mAdapter.notifyDataSetInvalidated();
            }
        });
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnCommitAsk:

                Intent intent = new Intent();
                intent.setClass(getActivity(), PayPageActivity.class);
                startActivity(intent);

                Reverve(userId);

                break;

            case R.id.asktime_layout:

                intent = new Intent();
                intent.putExtra("userId", userId);
                intent.setClass(getActivity(), TimeMainActivity.class);
                startActivityForResult(intent, REQUEST_MODIFY_TIME);
                break;

            /*case R.id.ask_tv_1:
                if (isSelect) {
                    mAsk_tv_face.setSelected(false);
                    isSelect = false;
                } else {
                    mAsk_tv_face.setSelected(true);
                    isSelect = true;
                }
                break;

            case R.id.ask_tv_2:
                if (isSelect) {
                    mAsk_tv_phone.setSelected(false);
                    isSelect = false;
                } else {
                    mAsk_tv_phone.setSelected(true);
                    isSelect = true;
                }
                break;*/

            /*case R.id.tv1_layout:
                mLayout_face.setSelected(true);

            case R.id.tv2_layout:

                mLayout_phone.setSelected(true);*/
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (data != null) {
            Log.i("tag", "data=" + data.toString());
        }

        switch (requestCode) {

            case REQUEST_MODIFY_TIME:

                String ask_data = data.getStringExtra("data");
                String ask_time = data.getStringExtra("time");
                reservationId = data.getIntExtra("reservationId", 2);

                mAskData.setText(ask_data);
                mAskTime.setText(ask_time);
                break;
        }
    }

    public void getUserInfo(String userid) {

        String url = "http://210.51.190.27:8082/getCounselorHome.jspa";
        /*OkHttpUtils.postString()
                .url(url)
                .content(new Gson().toJson(new UserInfo()))
                .build()
                .execute(new MyStringCallBack());*/

        OkHttpUtils
                .post()//
                .url(url)//
                .addParams("userId", userid)//
                .build()//
                .execute(new MyStringCallback());
    }

    private void Reverve(String userid) {

        String url = "http://210.51.190.27:8082/reserve.jspa";
        String mobile = mPhoneNum_et.getText().toString();
        String issue = mIssue_et.getText().toString();
        String note = mNote_et.getText().toString();

        String sec_mobile = SecurityUtil.encrypt(mobile);

        String encoder_issue = null;
        String encoder_note = null;

        try {
            encoder_issue = URLEncoder.encode(issue, "utf-8");
            encoder_note = URLEncoder.encode(note, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        OkHttpUtils
                .post()
                .url(url)
                .addParams("userId", userid)
                .addParams("reservationId", "2")
                .addParams("mobile", sec_mobile)
                .addParams("issue", encoder_issue)
                .addParams("note", encoder_note)
                .build()
                .execute(new ReverveCallback());
    }
}