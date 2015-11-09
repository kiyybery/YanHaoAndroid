package com.yanhao.main.yanhaoandroid.usercenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.adapter.MyOrderAdapter;
import com.yanhao.main.yanhaoandroid.bean.OrderBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/9 0009.
 */
public class RecordFragment extends Fragment {

    private ListView mListView;
    private OrderBean mOrderBean;
    private List<OrderBean> mList;
    private TextView mTitle;
    private ImageView mBackImage;

    public static RecordFragment newInstance() {
        RecordFragment fragment = new RecordFragment();
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
        View view = inflater.inflate(R.layout.fragment_myorder, container, false);

        mList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mOrderBean = new OrderBean();
            mOrderBean.setConstantName("咨询师" + i);
            mOrderBean.setLevel("国家" + i + "级");
            mOrderBean.setArea("擅长 亲子关系 婚姻关系 " + i);
            mOrderBean.setData("2015年12月" + i + "日");
            mList.add(mOrderBean);
        }
        mListView = (ListView) view.findViewById(R.id.order_listview);
        mListView.setAdapter(new MyOrderAdapter(getActivity(), mList));

        mBackImage = (ImageView) view.findViewById(R.id.iv_section_title_back);
        mBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        mTitle = (TextView) view.findViewById(R.id.tv_section_title_title);
        mTitle.setText("咨询记录");
        return view;
    }
}
