package com.yanhao.main.yanhaoandroid.usercenter;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.YanHao;
import com.yanhao.main.yanhaoandroid.util.RelayoutViewTool;

/**
 * Created by Administrator on 2015/11/24 0024.
 */
public class ModifyIsOK extends Fragment {

    private TextView tv_section_title_title;
    private ImageView iv_section_title_back;
    private String titleName;

    public static ModifyIsOK newInstance() {
        ModifyIsOK fragment = new ModifyIsOK();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int style = DialogFragment.STYLE_NO_FRAME, theme = 0;//神奇
        //setStyle(style, theme);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //getDialog().setCanceledOnTouchOutside(true);
        View view = inflater.inflate(R.layout.fragment_modifyok, container, false);
        RelayoutViewTool.relayoutViewWithScale(view, YanHao.screenWidthScale);

        titleName = getArguments().getString("titleName");
        tv_section_title_title = (TextView) view.findViewById(R.id.tv_section_title_title);
        tv_section_title_title.setText(titleName);
        iv_section_title_back = (ImageView) view.findViewById(R.id.iv_section_title_back);
        iv_section_title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //Dialog d = getDialog();
        //d.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }
}
