package com.yanhao.main.yanhaoandroid.usercenter;

import android.content.Intent;
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
import com.yanhao.main.yanhaoandroid.util.CircleImageView;

/**
 * Created by Administrator on 2015/11/9 0009.
 */
public class EditFragment extends Fragment implements View.OnClickListener{

    private TextView mTitle;
    private ImageView mBackImage;
    private CircleImageView mCircleImageView;
    private RelativeLayout mName_layout,mSex_layout,mAddress_layout,mProfire_layout;

    public static EditFragment newInstance() {

        EditFragment fragment = new EditFragment();
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
        View view = inflater.inflate(R.layout.fragment_edit,container,false);

        mName_layout = (RelativeLayout) view.findViewById(R.id.name_layout);
        mSex_layout = (RelativeLayout) view.findViewById(R.id.sex_layout);
        mAddress_layout = (RelativeLayout) view.findViewById(R.id.address_layout);
        mProfire_layout = (RelativeLayout) view.findViewById(R.id.profire_layout);
        mName_layout.setOnClickListener(this);
        mSex_layout.setOnClickListener(this);
        mAddress_layout.setOnClickListener(this);
        mProfire_layout.setOnClickListener(this);

        mBackImage = (ImageView) view.findViewById(R.id.iv_section_title_back);
        mBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        mTitle = (TextView) view.findViewById(R.id.tv_section_title_title);
        mTitle.setText("个人信息");
        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.name_layout:

                Intent intent = new Intent();
                intent.putExtra("type",1);
                intent.setClass(getActivity(),UpdateProfireActivity.class);
                startActivity(intent);
                break;
            case R.id.sex_layout:

                intent = new Intent();
                intent.putExtra("type",2);
                intent.setClass(getActivity(), UpdateProfireActivity.class);
                startActivity(intent);
                break;
            case R.id.address_layout:

                break;
            case R.id.profire_layout:

                break;
            default:
                break;
        }
    }
}
