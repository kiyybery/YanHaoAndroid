package com.yanhao.main.yanhaoandroid.usercenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yanhao.main.yanhaoandroid.R;

/**
 * Created by Administrator on 2015/11/10 0010.
 *
 * 咨询师的预约界面（按照咨询师id显示）
 */
public class ConsultantOrderFragment extends Fragment {

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
        View view = inflater.inflate(R.layout.fragment_consultant_order,container,false);
        return view;
    }
}
