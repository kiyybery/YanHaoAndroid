package com.yanhao.main.yanhaoandroid.usercenter;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.util.CircleImageView;

/**
 * Created by Administrator on 2015/11/9 0009.
 */
public class MyPrefireFragment extends Fragment implements View.OnClickListener {

    private TextView mOrder_tv, mTest_tv, mCollection_tv, mNote_tv, mSetting_tv, mShare_tv, mUpdate_tv;
    private CircleImageView mCircleImageView;

    public static MyPrefireFragment newInstance() {

        MyPrefireFragment fragment = new MyPrefireFragment();
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

        //透明状态栏
        //getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        View view = inflater.inflate(R.layout.prefire_fragment, container, false);

        mCircleImageView = (CircleImageView) view.findViewById(R.id.iv_uc_avatar);
        mCircleImageView.setImageResource(R.drawable.imgmengmengava);
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
        setTVDrawable();
        return view;
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
                intent.setClass(getActivity(),MyCollectionActivity.class);
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

                break;
            default:
                break;
        }
    }
}
