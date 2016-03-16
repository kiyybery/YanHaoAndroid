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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.YanHao;
import com.yanhao.main.yanhaoandroid.test.WebViewTest;
import com.yanhao.main.yanhaoandroid.util.RelayoutViewTool;
import com.yanhao.main.yanhaoandroid.util.UIHelper;

/**
 * Created by Administrator on 2015/11/24 0024.
 */
public class AboutFragment extends Fragment implements View.OnClickListener {

    private TextView mCallPhone_tv, mTitle;
    private ImageView mBackImage;
    Dialog dialog;
    private RelativeLayout info_layout, deal_layout;

    public static AboutFragment newInstance() {

        AboutFragment fragment = new AboutFragment();
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

        View view = inflater.inflate(R.layout.fragment_about, container, false);
        RelayoutViewTool.relayoutViewWithScale(view, YanHao.screenWidthScale);

        info_layout = (RelativeLayout) view.findViewById(R.id.about_layout_info);
        info_layout.setOnClickListener(this);
        deal_layout = (RelativeLayout) view.findViewById(R.id.about_layout_deal);
        deal_layout.setOnClickListener(this);
        mBackImage = (ImageView) view.findViewById(R.id.iv_section_title_back);
        mBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        mTitle = (TextView) view.findViewById(R.id.tv_section_title_title);
        mTitle.setText("关于我们");
        mCallPhone_tv = (TextView) view.findViewById(R.id.about_phone);
        mCallPhone_tv.setOnClickListener(this);
        mCallPhone_tv.setText("400-966-9809");
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.about_phone:
                dialog = UIHelper.buildCall(getActivity(),
                        "拨号",
                        mCallPhone_tv.getText().toString(),
                        "确定",
                        "取消",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(Intent.ACTION_CALL);
                                intent.setData(Uri.parse("tel:" + mCallPhone_tv.getText().toString()));
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
            case R.id.about_layout_info:

                Intent intent = new Intent();
                intent.setClass(getActivity(), AboutDetialActivity.class);
                startActivity(intent);
                break;
            case R.id.about_layout_deal:

                int tag = 1;
                intent = new Intent();
                intent.putExtra("tag",tag);
                intent.setClass(getActivity(), WebViewTest.class);
                startActivity(intent);
            default:
                break;
        }
    }
}
