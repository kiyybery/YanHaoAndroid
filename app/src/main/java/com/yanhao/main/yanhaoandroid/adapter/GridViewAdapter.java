package com.yanhao.main.yanhaoandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.YanHao;
import com.yanhao.main.yanhaoandroid.bean.Type;
import com.yanhao.main.yanhaoandroid.util.RelayoutViewTool;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Administrator on 2016/1/6 0006.
 */
public class GridViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<Type> mList;
    boolean isSelect;
    private int selectedPosition = -1;

    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }

    public GridViewAdapter(Context context, List<Type> list) {

        this.mContext = context;
        this.mList = list;
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
    public View getView(int i, View view, ViewGroup viewGroup) {

        Type type = (Type) getItem(i);
        view = LayoutInflater.from(mContext).inflate(R.layout.item, null);
        RelayoutViewTool.relayoutViewWithScale(view, YanHao.screenWidthScale);
        final TextView textView = (TextView) view.findViewById(R.id.ask_tv_item);
        textView.setText(type.text);

        if (i == selectedPosition) {

            textView.setSelected(true);
            isSelect = true;
        } else {

            textView.setSelected(false);
            isSelect = false;
        }
        return view;
    }
}
