package com.yanhao.main.yanhaoandroid.usercenter;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.util.CircleImageView;
import com.yanhao.main.yanhaoandroid.util.UIHelper;

/**
 * Created by Administrator on 2015/11/11 0011.
 */
public class PersonalOrderFragment extends Fragment implements View.OnClickListener {

    private CircleImageView mCircleImageView;
    private TextView mPhoneNum;
    Dialog dialog;

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
        mCircleImageView = (CircleImageView) view.findViewById(R.id.cv_personal_order_avatar);
        mCircleImageView.setImageResource(R.drawable.imgmengmengava);
        mPhoneNum = (TextView) view.findViewById(R.id.personal_call_layout_num);
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
                                intent.setData(Uri.parse("tel:"+mPhoneNum.getText().toString()));
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
