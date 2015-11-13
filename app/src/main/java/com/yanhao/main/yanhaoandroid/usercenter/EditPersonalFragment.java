package com.yanhao.main.yanhaoandroid.usercenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yanhao.main.yanhaoandroid.R;

/**
 * Created by Administrator on 2015/11/11 0011.
 *
 * 根据ID显示个人还是咨询师
 */
public class EditPersonalFragment extends Fragment{

    public static EditPersonalFragment newInstance() {

        EditPersonalFragment fragment = new EditPersonalFragment();
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
        View view = inflater.inflate(R.layout.fragment_personaledit,container,false);
        return view;
    }
}
