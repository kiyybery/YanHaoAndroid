package com.yanhao.main.yanhaoandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.YanHao;
import com.yanhao.main.yanhaoandroid.bean.Recommend;
import com.yanhao.main.yanhaoandroid.util.RelayoutViewTool;

import java.util.List;

/**
 * Created by Administrator on 2015/12/7 0007.
 */
public class RecommendAdapter extends BaseAdapter {

    private List<Recommend> mList;
    private LayoutInflater mInflater;

    public RecommendAdapter(Context context, List<Recommend> list) {
        this.mInflater = LayoutInflater.from(context);
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = mInflater.inflate(R.layout.fragment_recommend_item, null);
            RelayoutViewTool.relayoutViewWithScale(view, YanHao.screenWidthScale);
            viewHolder.pic = (ImageView) view.findViewById(R.id.recommend_item_pic);
            viewHolder.name = (TextView) view.findViewById(R.id.subname);
            viewHolder.level = (ImageView) view.findViewById(R.id.level_pic);
            viewHolder.distence = (TextView) view.findViewById(R.id.distence_tv);
            viewHolder.describe = (TextView) view.findViewById(R.id.describe_tv);
            view.setTag(viewHolder);
        } else {

            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.pic.setImageResource(R.drawable.title_pic);
        viewHolder.name.setText(mList.get(i).subName);
        viewHolder.distence.setText(mList.get(i).distence);
        viewHolder.describe.setText(mList.get(i).describe);
        return view;
    }

    class ViewHolder {
        ImageView pic;
        TextView name;
        ImageView level;
        TextView describe;
        TextView distence;
    }
}
