package com.yanhao.main.yanhaoandroid.usercenter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import com.yanhao.main.yanhaoandroid.homepage.ReadActivity;
import com.yanhao.main.yanhaoandroid.login.LoginActivity;
import com.yanhao.main.yanhaoandroid.share.ShareActivity;
import com.yanhao.main.yanhaoandroid.util.CircleImageView;
import com.yanhao.main.yanhaoandroid.util.FileUtilsOfPaul;
import com.yanhao.main.yanhaoandroid.util.NewFeature;
import com.yanhao.main.yanhaoandroid.util.SecurityUtil;
import com.yanhao.main.yanhaoandroid.util.Type;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.Inflater;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2015/11/9 0009.
 */
public class MyPrefireFragment extends Fragment implements View.OnClickListener, android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener {

    private TextView mOrder_tv, mTest_tv, mCollection_tv, mNote_tv, mIn_tv, mSetting_tv, mShare_tv, mUpdate_tv,
            mName_tv, mAddress_tv, mUnLogin_tv;
    private CircleImageView mCircleImageView;
    String nick_name, loaction, photourl;

    private static final int REQUEST_MODIFY_AVATAR = 500;
    private static final int REQUEST_MODIFY_LOGOUT = 501;

    private Map<String, String> tempUserInfo = new HashMap<>();

    private String userName, userId, userPhone, passWord;

    public static final int REQUEST_MODIFY_LOGIN = 701;

    SwipeRefreshLayout swipe;

    View view;

    LayoutInflater inflater;

    SharedPreferences sp;

    RelativeLayout mUnLoginLayout;

    String shareTitle, shareDesp, shareImageUrl, shareWebUrl;

    boolean isLogin = NewFeature.LOGIN_STATUS;

    public static MyPrefireFragment newInstance() {

        MyPrefireFragment fragment = new MyPrefireFragment();
        Bundle data = new Bundle();
        data.putString("title", " ");
        fragment.setArguments(data);
        return fragment;
    }


    public class MyStringCallback extends StringCallback {

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
        public void onResponse(String s) {

            try {
                //T.show(getActivity(), s, 100);
                JSONObject jsonObject = new JSONObject(s);
                String userId = jsonObject.getString("userId");
                String nick_name = jsonObject.getString("nickName");
                String imgUrl = jsonObject.getString("portraitUrl");

                mName_tv.setText(nick_name);
                Glide.with(MyPrefireFragment.this).load(imgUrl).into(mCircleImageView);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void inProgress(float progress) {
            super.inProgress(progress);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        sp = getActivity().getSharedPreferences("userInfo", getActivity().MODE_PRIVATE);
        userId = sp.getString("userId", "");
        userName = sp.getString("username", "");
        userPhone = sp.getString("userPhone", "");
        passWord = sp.getString("password", "");
        EventBus.getDefault().register(MyPrefireFragment.this);

        shareTitle = "燕好";
        shareDesp = "来自燕好的分享";
        shareImageUrl = "http://7xop51.com1.z0.glb.clouddn.com/yanhao_icon_72.png";
        shareWebUrl = "";
        //Toast.makeText(getActivity(), "profire uid = " + userId, Toast.LENGTH_LONG).show();
        //透明状态栏
        //getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //if (!userId.equals("")) {

        view = inflater.inflate(R.layout.prefire_fragment, container, false);
        //getUserInfo(userPhone, passWord);

        mName_tv = (TextView) view.findViewById(R.id.tv_uc_nickname);
        mName_tv.setText(userName);
        mAddress_tv = (TextView) view.findViewById(R.id.tv_uc_address);

        mUpdate_tv = (TextView) view.findViewById(R.id.reupdate_tv);
        mUnLoginLayout = (RelativeLayout) view.findViewById(R.id.unlogin_layout);
        if (!userId.equals("")) {

            mUnLoginLayout.setVisibility(View.GONE);
            mUpdate_tv.setVisibility(View.VISIBLE);
            getUserInfo(userName, passWord);
        } else {

            mUpdate_tv.setVisibility(View.GONE);
            mUnLoginLayout.setVisibility(View.VISIBLE);
        }
        mCircleImageView = (CircleImageView) view.findViewById(R.id.iv_uc_avatar);
        //mCircleImageView.setImageResource(R.drawable.imgmengmengava);
        //mCircleImageView.setOnClickListener(this);
        mOrder_tv = (TextView) view.findViewById(R.id.my_profile_order);
        mTest_tv = (TextView) view.findViewById(R.id.my_profile_test);
        mCollection_tv = (TextView) view.findViewById(R.id.my_profile_collection);
        mNote_tv = (TextView) view.findViewById(R.id.my_profile_note);
        mIn_tv = (TextView) view.findViewById(R.id.my_profile_inYanhao);
        mSetting_tv = (TextView) view.findViewById(R.id.my_profile_setting);
        mShare_tv = (TextView) view.findViewById(R.id.my_profile_share);


        mUnLogin_tv = (TextView) view.findViewById(R.id.tv_my_unlogin);
        mUnLogin_tv.setOnClickListener(this);
        swipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        swipe.setOnRefreshListener(this);
        // 顶部刷新的样式
        swipe.setColorSchemeResources(android.R.color.holo_red_light,
                android.R.color.holo_green_light,
                android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light);

        mUpdate_tv.setOnClickListener(this);
        mOrder_tv.setOnClickListener(this);
        mNote_tv.setOnClickListener(this);
        mIn_tv.setOnClickListener(this);
        mSetting_tv.setOnClickListener(this);
        mCollection_tv.setOnClickListener(this);
        mShare_tv.setOnClickListener(this);
        setTVDrawable();

        return view;
        /*} else {

            view = inflater.inflate(R.layout.fragment_my_unlogin, container, false);
            initUnloginView();
            return view;
        }*/
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(MyPrefireFragment.this);
    }

    //该方法接受上一个activity传递来的消息。目测是映射机制。
    // （!!这里需要一个Bean来完成收发，也就是get方法）
    public void onEventMainThread(Type type) {
        String msg = "onEventMainThread收到了消息：" + type.getMsg() + "第二条信息: " + type.getPwd();

        Log.d("harvic", msg);
        if (!type.getMsg().equals("")) {
            //Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
            mUnLoginLayout.setVisibility(View.GONE);
            getUserInfo(type.getMsg(), type.getPwd());
            mUpdate_tv.setVisibility(View.VISIBLE);
        } else if (type.getPwd().equals("logout")) {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
            mUnLoginLayout.setVisibility(View.VISIBLE);
            mUpdate_tv.setVisibility(View.GONE);
            mName_tv.setText(nick_name);
            Glide.with(MyPrefireFragment.this).load(R.drawable.avatar_default).into(mCircleImageView);
        }
    }


    public void getUserInfo(String phone, String pwd) {

        String url = "http://210.51.190.27:8082/login.jspa";
        String mobile = SecurityUtil.encrypt(phone);
        String password = SecurityUtil.encrypt(pwd);
        OkHttpUtils
                .post()//
                .url(url)//
                .addParams("mobile", mobile)//
                .addParams("password", password)//
                        //.addParams("mobile", SecurityUtil.encrypt(userPhone))
                        //.addParams("password", SecurityUtil.encrypt(passWord))
                .build()//
                .execute(new MyStringCallback());
    }

    public void setTVDrawable() {

        Drawable drawable_order_left = getResources().getDrawable(R.drawable.uc_myask);
        drawable_order_left.setBounds(0, 0, 70, 70);
        Drawable drawable_order_right = getResources().getDrawable(R.drawable.arrow_right_uc);
        drawable_order_right.setBounds(0, 0, 44, 44);
        mOrder_tv.setCompoundDrawables(drawable_order_left, null, drawable_order_right, null);

        Drawable drawable_test_left = getResources().getDrawable(R.drawable.uc_mytest);
        drawable_test_left.setBounds(0, 0, 70, 70);
        mTest_tv.setCompoundDrawables(drawable_test_left, null, drawable_order_right, null);

        Drawable drawable_collection_left = getResources().getDrawable(R.drawable.uc_mycollection);
        drawable_collection_left.setBounds(0, 0, 70, 70);
        mCollection_tv.setCompoundDrawables(drawable_collection_left, null, drawable_order_right, null);

        Drawable drawable_note_left = getResources().getDrawable(R.drawable.uc_asknote);
        drawable_note_left.setBounds(0, 0, 70, 70);
        mNote_tv.setCompoundDrawables(drawable_note_left, null, drawable_order_right, null);

        Drawable drawable_in_left = getResources().getDrawable(R.drawable.inall_conutor);
        drawable_in_left.setBounds(0, 0, 70, 70);
        mIn_tv.setCompoundDrawables(drawable_in_left, null, drawable_order_right, null);

        Drawable drawable_setting_left = getResources().getDrawable(R.drawable.uc_setting);
        drawable_setting_left.setBounds(0, 0, 70, 70);
        mSetting_tv.setCompoundDrawables(drawable_setting_left, null, drawable_order_right, null);

        Drawable drawable_share_left = getResources().getDrawable(R.drawable.uc_share);
        drawable_share_left.setBounds(0, 0, 70, 70);
        mShare_tv.setCompoundDrawables(drawable_share_left, null, drawable_order_right, null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.reupdate_tv:

                //只做跳转，待传值。
                Intent intent = new Intent();
                intent.setClass(getActivity(), EditActivity.class);
                startActivity(intent);
                break;
            case R.id.my_profile_order:
                //
                intent = new Intent();
                intent.setClass(getActivity(), OrderActivity.class);
                startActivity(intent);
                break;
            case R.id.my_profile_test:

                break;
            case R.id.my_profile_collection:
                intent = new Intent();
                intent.setClass(getActivity(), CollectionActivity.class);
                startActivity(intent);
                break;
            case R.id.my_profile_note:

                intent = new Intent();
                intent.setClass(getActivity(), RecordActivity.class);
                startActivity(intent);
                break;
            case R.id.my_profile_setting:

                intent = new Intent();
                intent.setClass(getActivity(), SettingActivity.class);
                startActivityForResult(intent, REQUEST_MODIFY_LOGOUT);
                break;
            case R.id.my_profile_share:
                intent = new Intent();
                intent.putExtra("shareTitle", shareTitle);
                intent.putExtra("shareDesp", shareDesp);
                intent.putExtra("shareImageUrl", shareImageUrl);
                intent.putExtra("shareWebUrl", shareWebUrl);
                intent.setClass(getActivity(), ShareActivity.class);
                startActivity(intent);
                break;

            case R.id.iv_uc_avatar:
                FragmentManager fm = getFragmentManager();
                SelectAvatarSourceFrag f = SelectAvatarSourceFrag.newInstance();
                f.setTargetFragment(MyPrefireFragment.this, REQUEST_MODIFY_AVATAR);
                f.show(fm, "avatar");
                break;

            case R.id.tv_my_unlogin:

                intent = new Intent();
                intent.setClass(getActivity(), LoginActivity.class);
                startActivityForResult(intent, REQUEST_MODIFY_LOGIN);
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
            Log.i("111", "data=" + data.toString());
        }

        switch (resultCode) {
            case REQUEST_MODIFY_AVATAR:
                /**
                 *  拍照后是跳转到裁剪frag,再到预览frag, 然后在预览frag if(确定)，再次返回本界面,更新本地avatar,
                 *   if(onBackPressed 用户确认更新个人资料|点击保存按钮)
                 * 从相册选择后返回，类似于拍照., 暂时先不做裁剪了，有时间再做
                 */
                int type = data.getIntExtra("avatar", 0);
                String path = "";
                if (type == 1) {
                    Bundle extras = data.getExtras();
                    path = extras.getString("avatarPath");
                    if (!TextUtils.isEmpty(path)) {
                        Log.i("11", "从相机返回的头像地址" + path);
                        Glide.with(this).load(Uri.fromFile(new File(path))).dontTransform().error(R.drawable.kuangge).into(mCircleImageView);
                    }
                } else if (type == 2) {
                    Uri imageUri = data.getData();
                    Log.i("11", "imageUri.toString=" + imageUri.toString() +
                            ",imageUri.getPath=" + imageUri.getPath() +
                            ",imageUri.getEncodedPath()=" + imageUri.getEncodedPath());
//                    imageUri.getPath(); iv_avatar.setImageURI(imageUri);
                    Glide.with(this).load(imageUri).dontTransform().error(R.drawable.kuangge).into(mCircleImageView);
                    path = FileUtilsOfPaul.getPath(getActivity(), imageUri);
                    Log.i("11", "从本地Uri解析出的头像地址" + path);
                }
                tempUserInfo.put("avatars", path);
                break;

            case REQUEST_MODIFY_LOGIN:

                isLogin = true;
                if (!userId.equals("")) {

                    view = inflater.inflate(R.layout.prefire_fragment, null);
                }
                break;

            case REQUEST_MODIFY_LOGOUT:

                view = inflater.inflate(R.layout.prefire_fragment, null);
                break;
            default:
                break;
        }
    }

    @Override
    public void onRefresh() {

        new Handler().postDelayed(new Runnable() {
            public void run() {
                getUserInfo(userPhone, passWord);
                swipe.setRefreshing(false);
            }
        }, 1500);
    }
}
