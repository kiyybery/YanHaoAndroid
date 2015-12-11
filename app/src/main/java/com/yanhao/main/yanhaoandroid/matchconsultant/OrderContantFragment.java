package com.yanhao.main.yanhaoandroid.matchconsultant;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.YanHao;
import com.yanhao.main.yanhaoandroid.homepage.HomePageActivity;
import com.yanhao.main.yanhaoandroid.homepage.PayPageActivity;
import com.yanhao.main.yanhaoandroid.util.RelayoutViewTool;

/**
 * Created by Administrator on 2015/12/1 0001.
 */
public class OrderContantFragment extends Fragment implements View.OnClickListener {

    private Button mCommitBtn;
    private RelativeLayout mAsktime_layout;
    private TextView mAsk_tv_face;
    private LinearLayout mLayout_face, mLayout_phone;
    private int status = 0;

    public static OrderContantFragment newInstance() {

        OrderContantFragment fragment = new OrderContantFragment();
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

        View view = inflater.inflate(R.layout.fragment_orderconstant, container, false);
        RelayoutViewTool.relayoutViewWithScale(view, YanHao.screenWidthScale);

        mLayout_face = (LinearLayout) view.findViewById(R.id.tv1_layout);
        mLayout_phone = (LinearLayout) view.findViewById(R.id.tv2_layout);
        mLayout_phone.setOnClickListener(this);
        mLayout_face.setOnClickListener(this);
        mAsk_tv_face = (TextView) view.findViewById(R.id.ask_tv_1);

        mCommitBtn = (Button) view.findViewById(R.id.btnCommitAsk);
        mCommitBtn.setOnClickListener(this);

        mAsktime_layout = (RelativeLayout) view.findViewById(R.id.asktime_layout);
        mAsktime_layout.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnCommitAsk:

                Intent intent = new Intent();
                intent.setClass(getActivity(), PayPageActivity.class);
                startActivity(intent);

                break;

            case R.id.asktime_layout:

                intent = new Intent();
                intent.setClass(getActivity(), OrderTimeActivity.class);
                startActivity(intent);
                break;

            case R.id.tv1_layout:
                mLayout_face.setSelected(true);

            case R.id.tv2_layout:

                mLayout_phone.setSelected(true);
            default:

                break;
        }
    }
}
