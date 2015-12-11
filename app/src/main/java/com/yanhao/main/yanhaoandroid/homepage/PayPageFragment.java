package com.yanhao.main.yanhaoandroid.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.YanHao;
import com.yanhao.main.yanhaoandroid.usercenter.ModifyOKActivity;
import com.yanhao.main.yanhaoandroid.util.RelayoutViewTool;

/**
 * Created by Administrator on 2015/12/1 0001.
 */
public class PayPageFragment extends Fragment implements View.OnClickListener{

    private Button mPaybtn;
    public static PayPageFragment newInstance() {

        PayPageFragment fragment = new PayPageFragment();
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
        View view = inflater.inflate(R.layout.fragment_paypage, container, false);
        RelayoutViewTool.relayoutViewWithScale(view, YanHao.screenWidthScale);

        mPaybtn = (Button) view.findViewById(R.id.btnpay);
        mPaybtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btnpay:

                break;
            
            default:
                break;
        }

    }
}
