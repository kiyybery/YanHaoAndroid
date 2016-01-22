package com.yanhao.main.yanhaoandroid.usercenter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yanhao.main.yanhaoandroid.MainActivity;
import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.util.AlertDialogFrag;

/**
 * Created by Administrator on 2015/11/9 0009.
 */
public class SettingFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = SettingFragment.class.getSimpleName();
    private static final int FLAG_CLEAR_CACHE = 1;
    private static final int FLAG_LOGOUT = 2;

    private TextView mTitle, mClear_num;
    private ImageView mBackImage;
    private RelativeLayout mClear_layout, mLogout_layout, mNotification_layout, mSafe_layout, mFeedback_layout,
            mAbout_layout;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
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
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        sp = getActivity().getSharedPreferences("userInfo", getActivity().MODE_PRIVATE);
        editor = sp.edit();

        mBackImage = (ImageView) view.findViewById(R.id.iv_section_title_back);
        mBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        mTitle = (TextView) view.findViewById(R.id.tv_section_title_title);
        mTitle.setText("设置");

        mClear_layout = (RelativeLayout) view.findViewById(R.id.clear_layout);
        mClear_layout.setOnClickListener(this);
        mLogout_layout = (RelativeLayout) view.findViewById(R.id.logout_layout);
        mLogout_layout.setOnClickListener(this);
        mClear_num = (TextView) view.findViewById(R.id.clear_num);
        mNotification_layout = (RelativeLayout) view.findViewById(R.id.message_layout);
        mNotification_layout.setOnClickListener(this);
        mSafe_layout = (RelativeLayout) view.findViewById(R.id.safe_layout);
        mSafe_layout.setOnClickListener(this);
        mAbout_layout = (RelativeLayout) view.findViewById(R.id.about_layout);
        mAbout_layout.setOnClickListener(this);
        mFeedback_layout = (RelativeLayout) view.findViewById(R.id.feedback_layout);
        mFeedback_layout.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.clear_layout:
                Bundle data = new Bundle();
                data.putString("title", getString(R.string.clear_cache_title));
                data.putString("msg", getString(R.string.clear_cache_hint));
                AlertDialogFrag f = AlertDialogFrag.newInstance(data);
                f.setTargetFragment(SettingFragment.this, FLAG_CLEAR_CACHE);
                f.show(getChildFragmentManager(), "clearcache");
                break;
            case R.id.logout_layout:
                data = new Bundle();
                data.putString("title", getString(R.string.logout_title));
                data.putString("msg", getString(R.string.logout_hint));
                f = AlertDialogFrag.newInstance(data);
                f.setTargetFragment(SettingFragment.this, FLAG_LOGOUT);
                f.show(getChildFragmentManager(), "logout");
                editor.clear();
                editor.commit();
                break;
            case R.id.message_layout:
                Intent intent = new Intent();
                intent.setClass(getActivity(), Notifications.class);
                startActivity(intent);
                break;
            case R.id.safe_layout:
                intent = new Intent();
                intent.setClass(getActivity(), PrivacyActivity.class);
                startActivity(intent);
                break;
            case R.id.about_layout:
                intent = new Intent();
                intent.setClass(getActivity(), AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.feedback_layout:
                intent = new Intent();
                intent.setClass(getActivity(), MessageCallBack.class);
                startActivity(intent);
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
        switch (requestCode) {
            case FLAG_CLEAR_CACHE:
                clearCache();
                break;
            case FLAG_LOGOUT:
                logout();
                break;
            default:
                break;
        }
    }

    public void clearCache() {

        mClear_num.setText("缓存清理完成");
    }

    public void logout() {

        Toast.makeText(getActivity(), "退出成功", Toast.LENGTH_LONG).show();
        Intent intent = new Intent();
        intent.setClass(getActivity(), MainActivity.class);
        startActivity(intent);

    }
}
