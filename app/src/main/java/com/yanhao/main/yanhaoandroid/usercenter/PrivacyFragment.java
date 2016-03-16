package com.yanhao.main.yanhaoandroid.usercenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.YanHao;
import com.yanhao.main.yanhaoandroid.util.PrefHelper;
import com.yanhao.main.yanhaoandroid.util.RelayoutViewTool;

/**
 * Created by Administrator on 2015/11/24 0024.
 */
public class PrivacyFragment extends Fragment implements View.OnClickListener {

    private RelativeLayout mModifyPwd_layout, mModifyPhone_layout;
    private TextView tv_section_title_title, mPrivacy_title_num, mEdit_privacy;
    private ImageView iv_section_title_back;
    String mobile = PrefHelper.get().getString("userMobile", "");

    public static PrivacyFragment newInstance() {

        PrivacyFragment fragment = new PrivacyFragment();
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
        View view = inflater.inflate(R.layout.fragment_privacy, container, false);
        RelayoutViewTool.relayoutViewWithScale(view, YanHao.screenWidthScale);

        String maskNumber = mobile.substring(0, 3) + "****" + mobile.substring(7, mobile.length());
        tv_section_title_title = (TextView) view.findViewById(R.id.tv_section_title_title);
        tv_section_title_title.setText("隐私与安全");
        iv_section_title_back = (ImageView) view.findViewById(R.id.iv_section_title_back);
        iv_section_title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        mModifyPwd_layout = (RelativeLayout) view.findViewById(R.id.privacy_layout_modify);
        mModifyPwd_layout.setOnClickListener(this);
        mPrivacy_title_num = (TextView) view.findViewById(R.id.privacy_title_num);
        mPrivacy_title_num.setText(maskNumber);
        mEdit_privacy = (TextView) view.findViewById(R.id.edit_privacy);
        mEdit_privacy.setText(maskNumber);
        mModifyPhone_layout = (RelativeLayout) view.findViewById(R.id.privacy_layout);
        mModifyPhone_layout.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.privacy_layout_modify:
                Intent intent = new Intent();
                intent.setClass(getActivity(), ModifyPwdActivity.class);
                startActivity(intent);
                break;

            case R.id.privacy_layout:

                intent = new Intent();
                intent.setClass(getActivity(), ModifyPhoneActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
