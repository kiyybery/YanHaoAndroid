package com.yanhao.main.yanhaoandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.TextView;

import com.yanhao.main.yanhaoandroid.R;

import com.yanhao.main.yanhaoandroid.YanHao;
import com.yanhao.main.yanhaoandroid.bean.CardBean;
import com.yanhao.main.yanhaoandroid.util.RelayoutViewTool;


import java.util.List;

/**
 * Created by Administrator on 2015/12/9 0009.
 */
public class CardAdapter extends BaseAdapter {

    private Context mContext;
    private List<CardBean> mList;
    boolean isSelect;
    private int selectedPosition = -1;

    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }

    public CardAdapter(Context mContext, List<CardBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList == null ? null : mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        CardBean card = (CardBean) getItem(i);
        view = LayoutInflater.from(mContext).inflate(R.layout.fragment_card, null);
        RelayoutViewTool.relayoutViewWithScale(view, YanHao.screenWidthScale);
        final TextView areaTextView = (TextView) view.findViewById(R.id.area_tv);
        areaTextView.setText(card.getText());


        if (i == selectedPosition) {

            areaTextView.setSelected(true);
            isSelect = true;
        } else {

            areaTextView.setSelected(false);
            isSelect = false;
        }


        return view;
    }
}
