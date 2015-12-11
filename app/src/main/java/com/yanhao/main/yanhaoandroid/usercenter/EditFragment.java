package com.yanhao.main.yanhaoandroid.usercenter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.util.CircleImageView;
import com.yanhao.main.yanhaoandroid.util.FileUtilsOfPaul;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/11/9 0009.
 */
public class EditFragment extends Fragment implements View.OnClickListener {

    private TextView mTitle, mName_tv,mSex_tv,mCity_tv;
    private ImageView mBackImage;
    private CircleImageView mCircleImageView;
    private RelativeLayout mName_layout, mSex_layout, mAddress_layout, mProfire_layout;
    private String name;

    private static final String TAG = EditFragment.class.getSimpleName();
    private static final int REQUEST_MODIFY_AVATAR = 500;
    public static final int REQUEST_MODIFY_AVATAR_CAMERA = 501;
    public static final int REQUEST_MODIFY_AVATAR_SD = 502;
    private static final int REQUEST_MODIFY_REGION = 504;
    public static final int REQUEST_MODIFY_USERNAME = 505;
    public static final int REQUEST_MODIFY_SEX = 506;
    public static final int REQUEST_MODIFY_CITY = 507;
    private Map<String, String> tempUserInfo = new HashMap<>();

    public static EditFragment newInstance() {

        EditFragment fragment = new EditFragment();
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
        View view = inflater.inflate(R.layout.fragment_edit, container, false);

        name = getArguments().getString("username");
        mName_tv = (TextView) view.findViewById(R.id.edit_name);
        mSex_tv = (TextView) view.findViewById(R.id.edit_sex);
        mCity_tv = (TextView) view.findViewById(R.id.edit_address);
        mCircleImageView = (CircleImageView) view.findViewById(R.id.iv_uc_avatar);
        mCircleImageView.setImageResource(R.drawable.imgmengmengava);
        mName_layout = (RelativeLayout) view.findViewById(R.id.name_layout);
        mSex_layout = (RelativeLayout) view.findViewById(R.id.sex_layout);
        mAddress_layout = (RelativeLayout) view.findViewById(R.id.address_layout);
        mProfire_layout = (RelativeLayout) view.findViewById(R.id.profire_layout);
        mName_layout.setOnClickListener(this);
        mSex_layout.setOnClickListener(this);
        mAddress_layout.setOnClickListener(this);
        mProfire_layout.setOnClickListener(this);
        mCircleImageView.setOnClickListener(this);

        mBackImage = (ImageView) view.findViewById(R.id.iv_section_title_back);
        mBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        mTitle = (TextView) view.findViewById(R.id.tv_section_title_title);
        mTitle.setText("个人信息");
        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.name_layout:

                Intent intent = new Intent();
                intent.putExtra("type", 1);
                intent.setClass(getActivity(), UpdateProfireActivity.class);
                startActivityForResult(intent, REQUEST_MODIFY_USERNAME);
                break;
            case R.id.sex_layout:

                intent = new Intent();
                intent.putExtra("type", 2);
                intent.setClass(getActivity(), UpdateProfireActivity.class);
                startActivityForResult(intent, REQUEST_MODIFY_SEX);
                break;
            case R.id.address_layout:

                intent = new Intent();
                intent.setClass(getActivity(),CityListActivity.class);
                startActivityForResult(intent, REQUEST_MODIFY_CITY);
                break;
            case R.id.profire_layout:

                intent = new Intent();
                intent.setClass(getActivity(),ProFireActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_uc_avatar:

                FragmentManager fm = getFragmentManager();
                SelectAvatarSourceFrag f = SelectAvatarSourceFrag.newInstance();
                f.setTargetFragment(EditFragment.this, REQUEST_MODIFY_AVATAR);
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
                String path = "";
                if (type == 1) {
                    Bundle extras = data.getExtras();
                    path = extras.getString("avatarPath");
                    if (!TextUtils.isEmpty(path)) {
                        Log.i(TAG, "从相机返回的头像地址" + path);
                        Glide.with(this).load(Uri.fromFile(new File(path))).dontTransform().error(R.drawable.kuangge).into(mCircleImageView);
                    }
                } else if (type == 2) {
                    Uri imageUri = data.getData();
                    Log.i(TAG, "imageUri.toString=" + imageUri.toString() +
                            ",imageUri.getPath=" + imageUri.getPath() +
                            ",imageUri.getEncodedPath()=" + imageUri.getEncodedPath());
//                    imageUri.getPath(); iv_avatar.setImageURI(imageUri);
                    Glide.with(this).load(imageUri).dontTransform().error(R.drawable.kuangge).into(mCircleImageView);
                    path = FileUtilsOfPaul.getPath(getActivity(), imageUri);
                    Log.i(TAG, "从本地Uri解析出的头像地址" + path);
                }
                tempUserInfo.put("avatars", path);
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
                mName_tv.setText(name);
                tempUserInfo.put("username",name);
                break;
            case REQUEST_MODIFY_SEX:
                String sex = data.getStringExtra("sex");
                mSex_tv.setText(sex);
                tempUserInfo.put("sex", sex);
                break;
            case REQUEST_MODIFY_CITY:
                String cityname = data.getStringExtra("city_name");
                mCity_tv.setText(cityname);
                tempUserInfo.put("cityname", cityname);
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
}
