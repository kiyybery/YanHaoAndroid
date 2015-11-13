package com.yanhao.main.yanhaoandroid.usercenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yanhao.main.yanhaoandroid.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2015/11/10 0010.
 */
public class UpdateSexFragment extends Fragment implements View.OnClickListener{

    private TextView mMan_tv,mWoman_tv,mFinish;
    private String getMan,getWoman,getSex;
    private ImageView mCheckMan,mCheckWoman;

    public static UpdateSexFragment newInstance() {

        UpdateSexFragment fragment = new UpdateSexFragment();
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

        View view = inflater.inflate(R.layout.fragment_updatesex,container,false);
        mMan_tv = (TextView) view.findViewById(R.id.man_tv);
        mMan_tv.setOnClickListener(this);
        mWoman_tv = (TextView) view.findViewById(R.id.woman_tv);
        mWoman_tv.setOnClickListener(this);
        mFinish = (TextView) view.findViewById(R.id.tv_text_title_right);
        mFinish.setOnClickListener(this);
        mCheckMan = (ImageView) view.findViewById(R.id.right_check_man);
        mCheckWoman = (ImageView) view.findViewById(R.id.right_check_woman);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.man_tv:
                getMan = mMan_tv.getText().toString();
                getSex = getMan;
                mCheckMan.setVisibility(View.VISIBLE);
                mCheckWoman.setVisibility(View.GONE);
                break;
            case R.id.woman_tv:
                getWoman = mWoman_tv.getText().toString();
                getSex = getWoman;
                mCheckMan.setVisibility(View.GONE);
                mCheckWoman.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_text_title_right:
                save();
                break;
            default:
                break;
        }
    }

    public void save(){

        Intent intent = new Intent();
        intent.putExtra("sex",getSex);
        Toast.makeText(getActivity(),"sex="+getSex,Toast.LENGTH_LONG).show();
        finish(Activity.RESULT_OK, intent);
    }

    protected void finish(int resultCode, Intent data) {
        if (getActivity() != null) {
            getActivity().setResult(resultCode, data);
            getActivity().finish();
        }
    }
}
