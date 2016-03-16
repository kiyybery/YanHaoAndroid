package com.yanhao.main.yanhaoandroid.usercenter;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.util.CircleImageView;
import com.yanhao.main.yanhaoandroid.util.SecurityUtil;
import com.yanhao.main.yanhaoandroid.util.UIHelper;

/**
 * Created by Administrator on 2015/11/11 0011.
 */
public class PersonalOrderFragment extends Fragment implements View.OnClickListener {

    private CircleImageView mCircleImageView;
    private TextView mPhoneNum, mName, mTime, mIssue, mNote, mType;
    Dialog dialog;

    private String consultTypeName, userName, reservationTime, portraitUrl, issue, mobile, note;

    public static PersonalOrderFragment newInstance() {
        PersonalOrderFragment fragment = new PersonalOrderFragment();
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
        View view = inflater.inflate(R.layout.fragment_personal_order, container, false);

        consultTypeName = getArguments().getString("consultTypeName");
        userName = getArguments().getString("userName");
        reservationTime = getArguments().getString("reservationTime");
        portraitUrl = getArguments().getString("portraitUrl");
        issue = getArguments().getString("issue");
        mobile = getArguments().getString("mobile");
        Log.i("order_mobile", mobile);
        note = getArguments().getString("note");

        mCircleImageView = (CircleImageView) view.findViewById(R.id.cv_personal_order_avatar);
        Glide.with(PersonalOrderFragment.this)
                .load(portraitUrl)
                .dontTransform()
                .dontAnimate()
                .placeholder(R.drawable.avatar_default)
                .error(R.drawable.avatar_default)
                .into(mCircleImageView);

        mName = (TextView) view.findViewById(R.id.personal_order_name);
        mTime = (TextView) view.findViewById(R.id.personal_ordered_time);
        mType = (TextView) view.findViewById(R.id.personal_style_time);
        mIssue = (TextView) view.findViewById(R.id.personal_style_type);
        mPhoneNum = (TextView) view.findViewById(R.id.personal_call_layout_num);

        mName.setText(userName);
        mTime.setText(reservationTime);
        mType.setText(consultTypeName);
        mIssue.setText(issue);
        //mPhoneNum.setText(SecurityUtil.decrypt(mobile));
        mPhoneNum.setText(mobile);
        //Log.i("order_mobile", SecurityUtil.decrypt(mobile));

        mPhoneNum.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.personal_call_layout_num:
                dialog = UIHelper.buildCall(getActivity(),
                        "拨号",
                        mPhoneNum.getText().toString(),
                        "确定",
                        "取消",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(Intent.ACTION_CALL);
                                intent.setData(Uri.parse("tel:" + mPhoneNum.getText().toString()));
                                startActivity(intent);
                                dialog.dismiss();
                            }
                        }, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                break;
            default:
                break;
        }
    }
}
