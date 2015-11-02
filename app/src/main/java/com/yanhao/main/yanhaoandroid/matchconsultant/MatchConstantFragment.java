package com.yanhao.main.yanhaoandroid.matchconsultant;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.adapter.ConstantAdapter;
import com.yanhao.main.yanhaoandroid.bean.ConstantBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/2 0002.
 */
public class MatchConstantFragment extends Fragment{

    private ListView mConstantListView;
    private List<ConstantBean> mList;
    private ConstantBean mConstantBean;

    public static MatchConstantFragment newInstance() {

        MatchConstantFragment fragment = new MatchConstantFragment();
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
        View view = inflater.inflate(R.layout.fragment_matchconsultant,container,false);

        mList = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            mConstantBean = new ConstantBean();
            mConstantBean.setConstantName("大师"+i);
            mConstantBean.setLevel("国家二级" + i);
            mConstantBean.setArea("亲子关系 婚姻困惑 三角恋" + i);
            mList.add(mConstantBean);
        }
        mConstantListView = (ListView) view.findViewById(R.id.match_listview);
        mConstantListView.setAdapter(new ConstantAdapter(getActivity(),mList));
        return view;
    }
}
