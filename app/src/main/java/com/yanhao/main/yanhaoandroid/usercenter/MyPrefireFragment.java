package com.yanhao.main.yanhaoandroid.usercenter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.YanHao;
import com.yanhao.main.yanhaoandroid.bean.UserInfo;
import com.yanhao.main.yanhaoandroid.share.ShareActivity;
import com.yanhao.main.yanhaoandroid.util.CircleImageView;
import com.yanhao.main.yanhaoandroid.util.FileUtilsOfPaul;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/11/9 0009.
 */
public class MyPrefireFragment extends Fragment implements View.OnClickListener {

    private TextView mOrder_tv, mTest_tv, mCollection_tv, mNote_tv, mSetting_tv, mShare_tv, mUpdate_tv,
            mName_tv, mAddress_tv;
    private CircleImageView mCircleImageView;
    String nick_name, loaction, photourl;

    private static final int REQUEST_MODIFY_AVATAR = 500;

    private Map<String, String> tempUserInfo = new HashMap<>();

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
                JSONObject jsonObject = new JSONObject(s);
                jsonObject.getString("address");
                JSONArray jsonArray = jsonObject.getJSONArray("charge");
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jo = (JSONObject) jsonArray.get(i);

                }
                jsonObject.getString("counselorName");
                jsonObject.getString("education");
                jsonObject.getInt("gender");
                jsonObject.getString("intro");
                jsonObject.getString("photoUrl");
                JSONArray jsonArray1 = jsonObject.getJSONArray("speciality");

                for (int j = 0; j < jsonArray1.length(); j++) {

                }
                Toast.makeText(getActivity(),"result"+s,Toast.LENGTH_LONG).show();



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

        //透明状态栏
        //getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        View view = inflater.inflate(R.layout.prefire_fragment, container, false);

        getUserInfo();

        mName_tv = (TextView) view.findViewById(R.id.tv_uc_nickname);

        mAddress_tv = (TextView) view.findViewById(R.id.tv_uc_address);

        mCircleImageView = (CircleImageView) view.findViewById(R.id.iv_uc_avatar);
        mCircleImageView.setImageResource(R.drawable.imgmengmengava);
        //mCircleImageView.setOnClickListener(this);
        mOrder_tv = (TextView) view.findViewById(R.id.my_profile_order);
        mTest_tv = (TextView) view.findViewById(R.id.my_profile_test);
        mCollection_tv = (TextView) view.findViewById(R.id.my_profile_collection);
        mNote_tv = (TextView) view.findViewById(R.id.my_profile_note);
        mSetting_tv = (TextView) view.findViewById(R.id.my_profile_setting);
        mShare_tv = (TextView) view.findViewById(R.id.my_profile_share);
        mUpdate_tv = (TextView) view.findViewById(R.id.reupdate_tv);

        mUpdate_tv.setOnClickListener(this);
        mOrder_tv.setOnClickListener(this);
        mNote_tv.setOnClickListener(this);
        mSetting_tv.setOnClickListener(this);
        mCollection_tv.setOnClickListener(this);
        mShare_tv.setOnClickListener(this);
        setTVDrawable();


        return view;
    }

    public void getUserInfo() {

        String url = YanHao.TEST_URL + "counselorId=1";
        OkHttpUtils
                .postString()
                .url(url)
                .content(new Gson().toJson(new UserInfo()))
                .build()
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
                intent.setClass(getActivity(), MyCollectionActivity.class);
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
                startActivity(intent);
                break;
            case R.id.my_profile_share:
                intent = new Intent();
                intent.setClass(getActivity(), ShareActivity.class);
                startActivity(intent);
                break;

            case R.id.iv_uc_avatar:
                FragmentManager fm = getFragmentManager();
                SelectAvatarSourceFrag f = SelectAvatarSourceFrag.newInstance();
                f.setTargetFragment(MyPrefireFragment.this, REQUEST_MODIFY_AVATAR);
                f.show(fm, "avatar");
                break;
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
            default:
                break;
        }
    }
}
