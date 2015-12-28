package com.yanhao.main.yanhaoandroid.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.yanhao.main.yanhaoandroid.matchconsultant.OrderContantActivity;
import com.yanhao.main.yanhaoandroid.util.CircleImageView;
import com.yanhao.main.yanhaoandroid.util.RelayoutViewTool;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/1 0001.
 */
public class HomePageFragment extends Fragment implements View.OnClickListener {

    private Button mOrderbtn;
    private RelativeLayout mBackGround_layout;
    private CircleImageView mTitleImg;
    private TextView mName,mAddress,mMyProfire,mEduction,mText1;
    private List specialityList = new ArrayList();

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
                String userId = jsonObject.getString("userId");
                int userType = jsonObject.getInt("userType");
                String nick_name = jsonObject.getString("name");
                String photoUrl = jsonObject.getString("photoUrl");
                String address = jsonObject.getString("address");
                String intro = jsonObject.getString("intro");
                String education = jsonObject.getString("education");
                JSONArray jsonArray = jsonObject.getJSONArray("speciality");
                String str = jsonArray.toString();

                for (int i = 0; i < jsonArray.length(); i++) {
                    specialityList.add(jsonArray.optString(i));
                    Toast.makeText(getActivity(),"list"+jsonArray.optString(i),Toast.LENGTH_LONG).show();
                    mText1.setText(jsonArray.optString(i));
                }

                JSONArray jsonArray1 = jsonObject.getJSONArray("charge");
                String str1 = jsonArray1.toString();
                /*Toast.makeText(getActivity(), "userId" + userId +
                        "nick_name" + nick_name +
                        "address" + address +
                        "intro" + intro +
                        "education" + education +
                        "speciality" + str +
                        "charge" + str1, Toast.LENGTH_LONG).show();*/

                Glide.with(HomePageFragment.this).load(photoUrl).into(mTitleImg);
                mName.setText(nick_name);
                mAddress.setText(address);
                mMyProfire.setText(intro);
                mEduction.setText(education);

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

        View view = inflater.inflate(R.layout.fragment_homepage_consultant, container, false);
        RelayoutViewTool.relayoutViewWithScale(view, YanHao.screenWidthScale);

        mOrderbtn = (Button) view.findViewById(R.id.btnOrder);
        mOrderbtn.setOnClickListener(this);

        mBackGround_layout = (RelativeLayout) view.findViewById(R.id.homepage_consultant_title_layout);
        mBackGround_layout.setBackgroundResource(R.drawable.homepage_bg);

        mTitleImg = (CircleImageView) view.findViewById(R.id.iv_homepage_avatar);

        mName = (TextView) view.findViewById(R.id.tv_homepage_nickname);
        mAddress = (TextView) view.findViewById(R.id.tv_uc_address);
        mMyProfire = (TextView) view.findViewById(R.id.myprofire_content);
        mEduction = (TextView) view.findViewById(R.id.professional_content);

        mText1 = (TextView) view.findViewById(R.id.item_tv_1);
        getUserInfo();
        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnOrder:
                Intent intent = new Intent();
                intent.setClass(getActivity(), OrderContantActivity.class);
                startActivity(intent);

                break;

            default:
                break;
        }
    }

    public void getUserInfo() {

        String url = YanHao.TEST_URL + "selectCounselorInfo.jspa?" + "userId=" + "1";
        OkHttpUtils.postString()
                .url(url)
                .content(new Gson().toJson(new UserInfo()))
                .build()
                .execute(new MyStringCallBack());
    }
}
