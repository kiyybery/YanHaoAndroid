package com.yanhao.main.yanhaoandroid.matchconsultant;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.adapter.CardAdapter;
import com.yanhao.main.yanhaoandroid.bean.CardBean;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class SimpleCardFragment extends Fragment {
    private String title;
    private GridView mGridView;
    private List<CardBean> mList = new ArrayList<CardBean>();
    private String[] areatexts = new String[]{
            "10:00 - 11:00", "12:30 - 13:30", "14:00 - 15:00",
            "16:30 - 17:30"
    };

    public static SimpleCardFragment getInstance(String title) {
        SimpleCardFragment sf = new SimpleCardFragment();
        sf.title = title;
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        for (int i = 0; i < areatexts.length; i++) {
            CardBean careBean = new CardBean();
            careBean.setText(areatexts[i]);
            mList.add(careBean);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fr_simple_card, null);
        mGridView = (GridView) v.findViewById(R.id.cardGridView);
        mGridView.setAdapter(new CardAdapter(getActivity(), mList));
        /*TextView card_title_tv = (TextView) v.findViewById(R.id.card_title_tv);
        card_title_tv.setText(title);*/

        return v;
    }
}