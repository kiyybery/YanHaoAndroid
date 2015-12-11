package com.yanhao.main.yanhaoandroid.homepage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.YanHao;
import com.yanhao.main.yanhaoandroid.adapter.ReadAdapter;
import com.yanhao.main.yanhaoandroid.bean.CollectionBean;
import com.yanhao.main.yanhaoandroid.util.RelayoutViewTool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/7 0007.
 */
public class ReadFragment extends Fragment {

    private List<CollectionBean> mList;
    private ListView mListView;
    private CollectionBean mBean;

    private TextView mTitle;
    private LinearLayout mBack;

    public static ReadFragment newInstance() {

        ReadFragment fragment = new ReadFragment();
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
        View view = inflater.inflate(R.layout.fragemnt_read, container, false);
        RelayoutViewTool.relayoutViewWithScale(view, YanHao.screenWidthScale);
        mListView = (ListView) view.findViewById(R.id.read_listview);

        mList = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            mBean = new CollectionBean();
            mBean.setTitle("心理学家如何谈论冥想？" + i);
            mBean.setDate("2015年" + i + "月" + i + "日");
            mList.add(mBean);
        }
        mListView.setAdapter(new ReadAdapter(mList, getActivity()));

        mTitle = (TextView) view.findViewById(R.id.tv_section_title_title);
        mTitle.setText("案例解读");
        mBack = (LinearLayout) view.findViewById(R.id.ll_section_title_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        return view;
    }
}
