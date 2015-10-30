package com.yanhao.main.yanhaoandroid;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yanhao.main.yanhaoandroid.util.TopBar;

/**
 * Created by Administrator on 2015/10/30 0030.
 */
public class HomeFragment extends Fragment{

    private TopBar mTopBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_home,container,false);

        //TopBar..FrogetFragment详情注释
        mTopBar = (TopBar) view.findViewById(R.id.topBar_home);
        mTopBar.setOnTopbarClickListener(new TopBar.topbarClickListener() {
            @Override
            public void leftClick() {

            }

            @Override
            public void rightClick() {

                Toast.makeText(getActivity(), "go to Serach", Toast.LENGTH_LONG).show();
            }
        });

        mTopBar.setButtonVisable(0, false);
        mTopBar.setButtonVisable(1, true);

        SharedPreferences sp = getActivity().getSharedPreferences("userInfo",getActivity().MODE_PRIVATE);
        sp.getString("username","");
        sp.getString("password","");
        Toast.makeText(getActivity(),"name"+sp.getString("username","")+"password"+sp.getString("password",""),Toast.LENGTH_LONG).show();

        return view;
    }
}
