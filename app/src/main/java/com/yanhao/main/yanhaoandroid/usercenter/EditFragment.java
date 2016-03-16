package com.yanhao.main.yanhaoandroid.usercenter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.yanhao.main.yanhaoandroid.MainActivity;
import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.YanHao;
import com.yanhao.main.yanhaoandroid.banner.T;
import com.yanhao.main.yanhaoandroid.bean.UserInfo;
import com.yanhao.main.yanhaoandroid.util.CircleImageView;
import com.yanhao.main.yanhaoandroid.util.FileUtilsOfPaul;
import com.yanhao.main.yanhaoandroid.util.PrefHelper;
import com.yanhao.main.yanhaoandroid.util.SecurityUtil;
import com.yanhao.main.yanhaoandroid.util.Type;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2015/11/9 0009.
 */
public class EditFragment extends Fragment implements View.OnClickListener {

    private TextView mTitle, mName_tv, mSex_tv, mCity_tv, mFinish_tv;
    private ImageView mBackImage;
    private CircleImageView mCircleImageView;
    private RelativeLayout mName_layout, mSex_layout, mAddress_layout, mProfire_layout;
    private String name;
    private String userId;
    private String password;
    private String intro;
    private int gender;
    private LinearLayout mBack;

    String path = "";
    private static final String TAG = EditFragment.class.getSimpleName();
    private static final int REQUEST_MODIFY_AVATAR = 500;
    public static final int REQUEST_MODIFY_AVATAR_CAMERA = 501;
    public static final int REQUEST_MODIFY_AVATAR_SD = 502;
    private static final int REQUEST_MODIFY_REGION = 504;
    public static final int REQUEST_MODIFY_USERNAME = 505;
    public static final int REQUEST_MODIFY_SEX = 506;
    public static final int REQUEST_MODIFY_CITY = 507;
    public static final int REQUEST_MODIFY_PROFIRE = 508;
    private Map<String, String> tempUserInfo = new HashMap<>();

    SharedPreferences sp;

    SharedPreferences.Editor editor;

    private String img_path;

    public static EditFragment newInstance() {

        EditFragment fragment = new EditFragment();
        Bundle data = new Bundle();
        data.putString("title", " ");
        fragment.setArguments(data);
        return fragment;
    }

    private class UpdateProFileCallBack extends StringCallback {


        @Override
        public void onError(Request request, Exception e) {
            T.show(getActivity(), e + "", 1000000);
            Log.i("update_pro", e + "");
        }

        @Override
        public void onResponse(String s) {

            /*try {
                JSONObject jsonObject = new JSONObject(s);
                String userId = jsonObject.getString("userId");
                int userType = jsonObject.getInt("userType");
                int gender = jsonObject.getInt("gender");
                String name = jsonObject.getString("name");
                String nick_name = jsonObject.getString("nickName");
                String address = jsonObject.getString("address");
                String imgUrl = jsonObject.getString("photoUrl");

                Glide.with(EditFragment.this).load(imgUrl).into(mCircleImageView);
                mName_tv.setText(nick_name);
                if (gender == 1) {
                    mSex_tv.setText("男");
                } else {
                    mSex_tv.setText("女");
                }

                mCity_tv.setText(address);

            } catch (JSONException e) {
                e.printStackTrace();
            }*/

            try {
                T.show(getActivity(), s, 1000);
                Log.i("update_avator", s);
                JSONObject jsonObject = new JSONObject(s);
                int ret = jsonObject.getInt("ret");
                if (ret == 0) {
                    img_path = jsonObject.getString("path");
                    PrefHelper.get().put("img_path", img_path);
                    T.show(getActivity(), jsonObject.getString("info"), 100);
                } else {

                    T.show(getActivity(), jsonObject.getString("info"), 100);
                }

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

        sp = getActivity().getSharedPreferences("userInfo", getActivity().MODE_PRIVATE);
        editor = sp.edit();
        userId = sp.getString("userId", "");
        name = sp.getString("username", "");
        password = sp.getString("password", "");

        //T.show(getActivity(), userId + name, 100);
        View view = inflater.inflate(R.layout.fragment_edit, container, false);

        //getUserInfo(name, password);

        name = getArguments().getString("username");
        //mShowError = (TextView) view.findViewById(R.id.show_error);
        mFinish_tv = (TextView) view.findViewById(R.id.tv_text_title_right);
        mName_tv = (TextView) view.findViewById(R.id.edit_name);
        mName_tv.setText(PrefHelper.get().getString("nickName", ""));
        mSex_tv = (TextView) view.findViewById(R.id.edit_sex);
        mSex_tv.setText(PrefHelper.get().getString("sex", ""));
        mCity_tv = (TextView) view.findViewById(R.id.edit_address);
        mCity_tv.setText(PrefHelper.get().getString("city_name", ""));
        mCircleImageView = (CircleImageView) view.findViewById(R.id.iv_uc_avatar_edit);
        /*Glide.with(EditFragment.this)
                .load(Uri.fromFile(new File(PrefHelper.get().getString("avatars", ""))))
                .dontTransform()
                .error(R.drawable.avatar_default)
                .into(mCircleImageView);*/
        Glide.with(EditFragment.this)
                .load(PrefHelper.get().getString("portraitUrl", ""))
                .dontTransform()
                .error(R.drawable.avatar_default)
                .into(mCircleImageView);
        mName_layout = (RelativeLayout) view.findViewById(R.id.name_layout);
        mSex_layout = (RelativeLayout) view.findViewById(R.id.sex_layout);
        mAddress_layout = (RelativeLayout) view.findViewById(R.id.address_layout);
        mProfire_layout = (RelativeLayout) view.findViewById(R.id.profire_layout);
        mName_layout.setOnClickListener(this);
        mFinish_tv.setOnClickListener(this);
        mSex_layout.setOnClickListener(this);
        mAddress_layout.setOnClickListener(this);
        mProfire_layout.setOnClickListener(this);
        mCircleImageView.setOnClickListener(this);

        mBackImage = (ImageView) view.findViewById(R.id.iv_text_title_back);
        mBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        mBack = (LinearLayout) view.findViewById(R.id.ll_text_title_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        mTitle = (TextView) view.findViewById(R.id.tv_text_title_title);
        mTitle.setText("个人信息");
        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.name_layout:

                Intent intent = new Intent();
                intent.putExtra("type", "1");
                intent.setClass(getActivity(), UpdateProfireActivity.class);
                startActivityForResult(intent, REQUEST_MODIFY_USERNAME);
                break;
            case R.id.sex_layout:

                intent = new Intent();
                intent.putExtra("type", "2");
                intent.setClass(getActivity(), UpdateProfireActivity.class);
                startActivityForResult(intent, REQUEST_MODIFY_SEX);
                break;
            case R.id.address_layout:

                intent = new Intent();
                intent.setClass(getActivity(), CityListActivity.class);
                startActivityForResult(intent, REQUEST_MODIFY_CITY);
                break;
            case R.id.profire_layout:

                intent = new Intent();
                intent.setClass(getActivity(), ProFireActivity.class);
                startActivityForResult(intent, REQUEST_MODIFY_PROFIRE);
                break;
            case R.id.iv_uc_avatar_edit:

                FragmentManager fm = getFragmentManager();
                SelectAvatarSourceFrag f = SelectAvatarSourceFrag.newInstance();
                f.setTargetFragment(EditFragment.this, REQUEST_MODIFY_AVATAR);
                f.show(fm, "avatar");
                break;

            case R.id.tv_text_title_right:

                String update_path = PrefHelper.get().getString("img_path", "");

                if (update_path == null) {

                    Toast.makeText(getActivity(), "path is null", Toast.LENGTH_LONG).show();
                    return;
                } else {

                    updateProfile(update_path);
                }
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
            Log.i(TAG, "data=" + data.toString());
        }
        switch (requestCode) {
            case REQUEST_MODIFY_AVATAR:
                /**
                 *  拍照后是跳转到裁剪frag,再到预览frag, 然后在预览frag if(确定)，再次返回本界面,更新本地avatar,
                 *   if(onBackPressed 用户确认更新个人资料|点击保存按钮)
                 * 从相册选择后返回，类似于拍照., 暂时先不做裁剪了，有时间再做
                 */
                int type = data.getIntExtra("avatar", 0);

                if (type == 1) {
                    Bundle extras = data.getExtras();
                    path = extras.getString("avatarPath");
                    if (!TextUtils.isEmpty(path)) {
                        Log.i(TAG, "从相机返回的头像地址" + path);
                        Glide.with(this).load(Uri.fromFile(new File(path))).dontTransform().error(R.drawable.kuangge).into(mCircleImageView);
                    }
                    multiFileUpload();
                } else if (type == 2) {
                    Uri imageUri = data.getData();
                    Log.i(TAG, "imageUri.toString=" + imageUri.toString() +
                            ",imageUri.getPath=" + imageUri.getPath() +
                            ",imageUri.getEncodedPath()=" + imageUri.getEncodedPath());
//                    imageUri.getPath(); iv_avatar.setImageURI(imageUri);
                    Glide.with(this).load(imageUri).dontTransform().error(R.drawable.avatar_default).into(mCircleImageView);
                    path = FileUtilsOfPaul.getPath(getActivity(), imageUri);
                    Log.i(TAG, "从本地Uri解析出的头像地址" + path);
                    //uploadImg(imageUri);

                    multiFileUpload();
                }
                tempUserInfo.put("avatars", path);
                PrefHelper.get().put("avatars", path);
                PrefHelper.get().put("portraitUrl", path);
                Log.i("avatars_path", path);

                break;

            case REQUEST_MODIFY_REGION:
//        修改城市后返回
                /*String city = data.getStringExtra("city");
                btnCity.setText(city);
                Map map = (Map) data.getExtras().getSerializable("region_id");
                tempUserInfo.putAll(map);*/
                break;
            case REQUEST_MODIFY_USERNAME:
                String name = data.getStringExtra("username");
                PrefHelper.get().put("username", data.getStringExtra("username"));
                mName_tv.setText(name);
                editor.putString("nickName", name);
                tempUserInfo.put("username", name);
                break;
            case REQUEST_MODIFY_SEX:
                String sex = data.getStringExtra("sex");
                if (sex.equals("男")) {

                    gender = 1;
                    PrefHelper.get().put("gender", gender);
                } else {

                    gender = 0;
                    PrefHelper.get().put("gender", gender);
                }
                PrefHelper.get().put("sex", data.getStringExtra("sex"));
                mSex_tv.setText(sex);
                tempUserInfo.put("sex", sex);
                break;
            case REQUEST_MODIFY_CITY:
                String cityname = data.getStringExtra("city_name");
                PrefHelper.get().put("city_name", data.getStringExtra("city_name"));
                mCity_tv.setText(cityname);
                tempUserInfo.put("cityname", cityname);
                break;

            case REQUEST_MODIFY_PROFIRE:
                intro = data.getStringExtra("intro");
                PrefHelper.get().put("intro", data.getStringExtra("intro"));
                Log.i("intro_profire", intro);
                break;
            default:
                break;
        }
    }

    public interface LocateIn {
        public void getCityName(String name);
    }

    public boolean isChanged() {
        boolean changed = tempUserInfo != null && tempUserInfo.size() > 0;
        if (changed) {
            Log.d(TAG, tempUserInfo.toString());
        }
        return changed;
    }

    private void updateProfile(String update_path) {

        String url = "http://210.51.190.27:8082/updateProfile.jspa";
        int sex_pr;
        String city_pr, intro_pr, portrait_pr, name;

        if (PrefHelper.get().getString("city_name", "").equals("") && PrefHelper.get().getString("intro", "").equals("")
                && mName_tv.getText().toString().equals("")) {

            city_pr = "北京";
            intro_pr = "个人简介";
            name = PrefHelper.get().getString("nickName", "");
        } else {

            city_pr = PrefHelper.get().getString("city_name", "");
            intro_pr = PrefHelper.get().getString("intro", "");
            name = mName_tv.getText().toString();
        }
        OkHttpUtils
                .post()
                .url(url)
                .addParams("userId", PrefHelper.get().getString("userId", ""))
                .addParams("nickName", name)
                .addParams("gender", 1 + "")
                .addParams("city", city_pr)
                .addParams("intro", intro_pr)
                .addParams("portrait", PrefHelper.get().getString("img_path", ""))
                .build()
                .execute(new UpdateProFileCallBack());
        update();
        Log.i("updateProfile_info", PrefHelper.get().getString("userId", "") + " " + mName_tv.getText().toString() + " " +
                gender + " " + mCity_tv.getText().toString() + " " + intro + " " + img_path);
    }

    private void multiFileUpload() {

        File file = new File(path);
        if (!file.exists()) {
            Toast.makeText(getActivity(), "文件不存在，请修改文件路径", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, String> params = new HashMap<>();
        params.put("userId", PrefHelper.get().getString("userId", ""));
        Log.i("params_userid", PrefHelper.get().getString("userId", ""));

        String url = "http://210.51.190.27:8082/uploadPortrait.jspa";
        OkHttpUtils.post()//
                .addFile("file", "YHPIC_fast.jpg", file)//
                .url(url)
                .params(params)//
                .build()//
                .execute(new UpdateProFileCallBack());
    }

    public void update() {

        Intent intent = new Intent();
        intent.putExtra("avatars_paths", path);
        intent.setClass(getActivity(), MainActivity.class);
        EventBus.getDefault().post(new Type("edit_avatars", path));
        finish(Activity.RESULT_OK, intent);
    }

    protected void finish(int resultCode, Intent data) {
        if (getActivity() != null) {
            getActivity().setResult(resultCode, data);
            getActivity().finish();
        }
    }
}
