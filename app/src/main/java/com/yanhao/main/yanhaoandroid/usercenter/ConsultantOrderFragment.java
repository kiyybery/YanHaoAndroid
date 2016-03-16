package com.yanhao.main.yanhaoandroid.usercenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.matchconsultant.Text;
import com.yanhao.main.yanhaoandroid.util.CircleImageView;

/**
 * Created by Administrator on 2015/11/10 0010.
 */
public class ConsultantOrderFragment extends Fragment {

    private String consultTypeName, counselorName, reservationTime, portraitUrl, address, serviceTel;
    private TextView mName_tv, mOrder_time_tv, mTitle, mAddress, mServiceTel, mOrderType_tv;
    private CircleImageView mCircleImageView;
    private LinearLayout mBack;

    public static ConsultantOrderFragment newInstance() {
        ConsultantOrderFragment fragment = new ConsultantOrderFragment();
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
        View view = inflater.inflate(R.layout.fragment_consultant_order, container, false);
        consultTypeName = getArguments().getString("consultTypeName");
        counselorName = getArguments().getString("counselorName");
        reservationTime = getArguments().getString("reservationTime");
        portraitUrl = getArguments().getString("portraitUrl");
        address = getArguments().getString("address");
        serviceTel = getArguments().getString("servicetel");

        mOrderType_tv = (TextView) view.findViewById(R.id.style_time);
        mOrderType_tv.setText(consultTypeName);
        mName_tv = (TextView) view.findViewById(R.id.order_name);
        mName_tv.setText(counselorName);
        mOrder_time_tv = (TextView) view.findViewById(R.id.ordered_time);
        mOrder_time_tv.setText(reservationTime);
        mAddress = (TextView) view.findViewById(R.id.ordered_address_tv_info);
        mAddress.setText(address);
        mServiceTel = (TextView) view.findViewById(R.id.call_layout_num);
        mServiceTel.setText(serviceTel);
        mCircleImageView = (CircleImageView) view.findViewById(R.id.cv_order_avatar);
        Glide.with(ConsultantOrderFragment.this)
                .load(portraitUrl)
                .dontTransform()
                .dontAnimate()
                .placeholder(R.drawable.avatar_default)
                .error(R.drawable.avatar_default)
                .into(mCircleImageView);

        mBack = (LinearLayout) view.findViewById(R.id.ll_section_title_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        mTitle = (TextView) view.findViewById(R.id.tv_section_title_title);
        mTitle.setText("预约详情");
        return view;
    }
}
