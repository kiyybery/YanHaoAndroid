package com.yanhao.main.yanhaoandroid.usercenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.adapter.CollectionAdapter;
import com.yanhao.main.yanhaoandroid.bean.CollectionBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/9 0009.
 */
public class MyCollectionFragment extends Fragment{

    private ListView mListView;
    private List<CollectionBean> mList;
    private CollectionBean mCollectionBean;

    public static MyCollectionFragment newInstance() {
        MyCollectionFragment fragment = new MyCollectionFragment();
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
        View view = inflater.inflate(R.layout.fragment_collection,container,false);
        mList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mCollectionBean = new CollectionBean();
            mCollectionBean.setTitle("心理学家是如何谈论冥想的");
            mCollectionBean.setDate("2015年12月" + i + "日");
            mList.add(mCollectionBean);
        }
        mListView = (ListView) view.findViewById(R.id.collection_listview);
        mListView.setAdapter(new CollectionAdapter(mList,getActivity()));
        return view;
    }
}
