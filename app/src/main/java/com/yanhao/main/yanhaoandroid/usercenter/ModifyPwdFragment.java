package com.yanhao.main.yanhaoandroid.usercenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.YanHao;
import com.yanhao.main.yanhaoandroid.util.RelayoutViewTool;

/**
 * Created by Administrator on 2015/11/24 0024.
 */
public class ModifyPwdFragment extends Fragment implements View.OnClickListener {

    private TextView tv_section_title_title, mPhoneNum_tv;
    private ImageView iv_section_title_back;
    private Button mGetPwd;
    String mobile = "18101215049";
    private RelativeLayout mModify_layout;

    public static ModifyPwdFragment newInstance() {

        ModifyPwdFragment fragment = new ModifyPwdFragment();
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

        View view = inflater.inflate(R.layout.fragment_modifypwd, container, false);
        RelayoutViewTool.relayoutViewWithScale(view, YanHao.screenWidthScale);

        tv_section_title_title = (TextView) view.findViewById(R.id.tv_section_title_title);
        tv_section_title_title.setText("修改密码");
        iv_section_title_back = (ImageView) view.findViewById(R.id.iv_section_title_back);
        iv_section_title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        String maskNumber = mobile.substring(0, 3) + "****" + mobile.substring(7, mobile.length());
        mPhoneNum_tv = (TextView) view.findViewById(R.id.edit_modify);
        mPhoneNum_tv.setText(maskNumber);
        mGetPwd = (Button) view.findViewById(R.id.modify_btnGetPwd);
        mGetPwd.setOnClickListener(this);

        mModify_layout = (RelativeLayout) view.findViewById(R.id.modify_layout);
        mModify_layout.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.modify_btnGetPwd:
                Intent intent = new Intent();
                intent.setClass(getActivity(), ModifyOKActivity.class);
                intent.putExtra("titleName",tv_section_title_title.getText());
                startActivity(intent);
                break;
            case R.id.modify_layout:
                intent = new Intent();
                intent.setClass(getActivity(), ModifyPhoneActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
