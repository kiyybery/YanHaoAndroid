package com.yanhao.main.yanhaoandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.YanHao;
import com.yanhao.main.yanhaoandroid.bean.SelectBean;
import com.yanhao.main.yanhaoandroid.util.RelayoutViewTool;

import java.util.List;

/**
 * Created by Administrator on 2015/12/10 0010.
 */
public class SerachHistoryAdapter extends BaseAdapter {

    private List<SelectBean> mList;
    private Context mContext;

    public SerachHistoryAdapter(Context pContext, List<SelectBean> pList) {
        this.mContext = pContext;
        this.mList = pList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater _LayoutInflater = LayoutInflater.from(mContext);
        convertView = _LayoutInflater.inflate(R.layout.fragment_serach_history, null);
        RelayoutViewTool.relayoutViewWithScale(convertView, YanHao.screenWidthScale);
        if (convertView != null) {

            TextView _TextView1 = (TextView) convertView.findViewById(R.id.history_tv);
            _TextView1.setText(mList.get(position).getText());
        }
        return convertView;
    }
}
