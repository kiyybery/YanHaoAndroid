package com.yanhao.main.yanhaoandroid.matchconsultant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.YanHao;
import com.yanhao.main.yanhaoandroid.util.RelayoutViewTool;

import java.util.List;

/**
 * Created by Administrator on 2016/1/8 0008.
 */
public class TimeAdapter extends BaseAdapter {

    private Context mContext;
    private List<ScheduleData> mList;
    boolean isSelect;
    private int selectedPosition = 0;

    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }

    public TimeAdapter(Context mContext, List<ScheduleData> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    public void resetData(Context mContext, List<ScheduleData> mList) {

        this.mList.clear();
        this.mList.addAll(mList);
        this.notifyDataSetChanged();
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
        ScheduleData data = mList.get(i);
        view = LayoutInflater.from(mContext).inflate(R.layout.fragment_card, null);
        RelayoutViewTool.relayoutViewWithScale(view, YanHao.screenWidthScale);
        final TextView areaTextView = (TextView) view.findViewById(R.id.area_tv);
        ImageView select_img = (ImageView) view.findViewById(R.id.check_img_icon);
        areaTextView.setText(data.period);


        if (i == selectedPosition) {

            areaTextView.setSelected(true);
            select_img.setVisibility(View.VISIBLE);
            isSelect = true;
        } else {

            areaTextView.setSelected(false);
            select_img.setVisibility(View.GONE);
            isSelect = false;
        }


        return view;
    }
}
