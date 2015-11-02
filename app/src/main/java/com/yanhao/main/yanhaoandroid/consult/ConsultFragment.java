package com.yanhao.main.yanhaoandroid.consult;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.adapter.AreaGridAdapter;
import com.yanhao.main.yanhaoandroid.bean.AreaItem;
import com.yanhao.main.yanhaoandroid.util.TopBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/2 0002.
 */
public class ConsultFragment extends Fragment{

    private GridView mGridView;
    private List<AreaItem> arealist = new ArrayList<AreaItem>();
    private TopBar mTopBar;

    private int[] areaimages = new int[]{
            R.drawable.marriage,R.drawable.children,
            R.drawable.workplace,R.drawable.mood,
            R.drawable.interpersonal,R.drawable.personal,
            R.drawable.sex,R.drawable.other
    };
    private String[] areatexts = new String[]{
            "婚姻关系","少年儿童","职场困惑",
            "情绪神经症","人及社交","个人成长",
            "性方面","其他"
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (int i = 0; i < areatexts.length; i++) {
            AreaItem areaItem = new AreaItem();
            areaItem.setImg(areaimages[i]);
            areaItem.setText(areatexts[i]);
            arealist.add(areaItem);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_subarea,container,false);
        mGridView = (GridView) view.findViewById(R.id.AreaGridView);
        mGridView.setAdapter(new AreaGridAdapter(getActivity(),arealist));
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.setClass(getActivity(),ConsultListActivity.class);
                startActivity(intent);
            }
        });

        mTopBar = (TopBar) view.findViewById(R.id.topBar_consult);
        mTopBar.setOnTopbarClickListener(new TopBar.topbarClickListener() {
            @Override
            public void leftClick() {

            }

            @Override
            public void rightClick() {
                Toast.makeText(getActivity(),"区域选择",Toast.LENGTH_LONG).show();
            }
        });

        mTopBar.setButtonVisable(0, false);
        mTopBar.setButtonVisable(1, true);
        return view;
    }
}
