package com.yanhao.main.yanhaoandroid.consult;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.adapter.ConsultListAdapter;
import com.yanhao.main.yanhaoandroid.bean.ConsultListBean;
import com.yanhao.main.yanhaoandroid.matchconsultant.MatchConsultantActivity;
import com.yanhao.main.yanhaoandroid.util.CustomProgressDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/2 0002.
 */
public class ConsultListFragment extends Fragment {

    private ListView mConsultListView;
    private List<ConsultListBean> mList;
    private ConsultListBean mConsultListBean;
    private ImageView mBackImage;
    private TextView mTitle;

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
        View view = inflater.inflate(R.layout.fragment_consultlist, container, false);

        int itemId = getArguments().getInt("itemId", 0);
        String itemName = getArguments().getString("itemName");

        Toast.makeText(getActivity(), "activity say :" + itemId + "," + itemName, Toast.LENGTH_LONG).show();
        mList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            mConsultListBean = new ConsultListBean();
            mConsultListBean.setText("情绪障碍" + i);
            mList.add(mConsultListBean);
        }
        mConsultListView = (ListView) view.findViewById(R.id.consult_listview);
        mConsultListView.setAdapter(new ConsultListAdapter(mList, getActivity()));
        mConsultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String titleName = mList.get(i).getText();
                Intent intent = new Intent(getActivity(), MatchConsultantActivity.class);
                intent.putExtra("titlename", titleName);
                startActivity(intent);
            }
        });

        mBackImage = (ImageView) view.findViewById(R.id.iv_section_title_back);
        mBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        mTitle = (TextView) view.findViewById(R.id.tv_section_title_title);
        mTitle.setText(itemName);
        return view;
    }

}
