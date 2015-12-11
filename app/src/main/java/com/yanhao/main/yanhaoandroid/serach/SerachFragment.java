package com.yanhao.main.yanhaoandroid.serach;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Spinner;

import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.YanHao;
import com.yanhao.main.yanhaoandroid.adapter.SelectAdapter;
import com.yanhao.main.yanhaoandroid.adapter.SerachHistoryAdapter;
import com.yanhao.main.yanhaoandroid.bean.SelectBean;
import com.yanhao.main.yanhaoandroid.util.RelayoutViewTool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/1 0001.
 */
public class SerachFragment extends Fragment {

    private List<SelectBean> mContent;
    private ListView mListView;

    public static SerachFragment newInstance() {

        SerachFragment fragment = new SerachFragment();
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

        View view = inflater.inflate(R.layout.fragment_serach, container, false);
        RelayoutViewTool.relayoutViewWithScale(view, YanHao.screenWidthScale);

        Spinner spinner2 = (Spinner) view.findViewById(R.id.spinner2);
        List<SelectBean> selectBeanList = new ArrayList<>();
        selectBeanList.add(new SelectBean("全部"));
        selectBeanList.add(new SelectBean("咨询师"));
        selectBeanList.add(new SelectBean("机构"));

        SelectAdapter selectAdapter = new SelectAdapter(getActivity(), selectBeanList);
        spinner2.setAdapter(selectAdapter);

        mListView = (ListView) view.findViewById(R.id.history_serach_list);

        mContent = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            SelectBean select = new SelectBean();
            select.setText("记录" + i);
            mContent.add(select);
        }
        mListView.setAdapter(new SerachHistoryAdapter(getActivity(), mContent));
        return view;
    }
}
