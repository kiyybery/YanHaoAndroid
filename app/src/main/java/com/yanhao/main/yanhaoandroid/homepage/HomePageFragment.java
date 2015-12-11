package com.yanhao.main.yanhaoandroid.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.YanHao;
import com.yanhao.main.yanhaoandroid.matchconsultant.OrderContantActivity;
import com.yanhao.main.yanhaoandroid.util.RelayoutViewTool;

/**
 * Created by Administrator on 2015/12/1 0001.
 */
public class HomePageFragment extends Fragment implements View.OnClickListener {

    private Button mOrderbtn;
    private RelativeLayout mBackGround_layout;

    public static HomePageFragment newInstance() {

        HomePageFragment fragment = new HomePageFragment();
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

        View view = inflater.inflate(R.layout.fragment_homepage_consultant, container, false);
        RelayoutViewTool.relayoutViewWithScale(view, YanHao.screenWidthScale);

        mOrderbtn = (Button) view.findViewById(R.id.btnOrder);
        mOrderbtn.setOnClickListener(this);

        mBackGround_layout = (RelativeLayout) view.findViewById(R.id.homepage_consultant_title_layout);
        mBackGround_layout.setBackgroundResource(R.drawable.homepage_bg);
        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnOrder:
                Intent intent = new Intent();
                intent.setClass(getActivity(), OrderContantActivity.class);
                startActivity(intent);

                break;

            default:
                break;
        }
    }
}
