package com.yanhao.main.yanhaoandroid.consult;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.adapter.ConsultListAdapter;
import com.yanhao.main.yanhaoandroid.bean.ConsultListBean;
import com.yanhao.main.yanhaoandroid.matchconsultant.MatchConsultantActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/2 0002.
 */
public class ConsultListFragment extends Fragment{

    private ListView mConsultListView;
    private List<ConsultListBean> mList;
    private ConsultListBean mConsultListBean;

    public static ConsultListFragment newInstance() {

        ConsultListFragment fragment = new ConsultListFragment();
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
        View view = inflater.inflate(R.layout.fragment_consultlist,container,false);
        mList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mConsultListBean = new ConsultListBean();
            mConsultListBean.setText("情绪障碍"+i);
            mList.add(mConsultListBean);
        }
        mConsultListView = (ListView) view.findViewById(R.id.consult_listview);
        mConsultListView.setAdapter(new ConsultListAdapter(mList, getActivity()));
        mConsultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), MatchConsultantActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

}
