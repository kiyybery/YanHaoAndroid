package com.yanhao.main.yanhaoandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.bean.ConstantBean;

import java.util.List;

/**
 * Created by Administrator on 2015/11/2 0002.
 */
public class ConstantAdapter extends BaseAdapter{
    private List<ConstantBean> mList;
    private LayoutInflater mInflater;

    public ConstantAdapter (Context ctx,List<ConstantBean> list){
        mInflater = LayoutInflater.from(ctx);
        mList = list;
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
        if(view == null){
            viewHolder = new ViewHolder();
            view = mInflater.inflate(R.layout.fragment_matchitem,null);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.cv_comment_avatar);
            viewHolder.name_tv = (TextView) view.findViewById(R.id.consultant_name);
            viewHolder.level_tv = (ImageView) view.findViewById(R.id.consult_level);
            viewHolder.area_tv = (TextView) view.findViewById(R.id.consult_area);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.imageView.setImageResource(R.drawable.avatar_default);

        viewHolder.name_tv.setText(mList.get(i).constantName);
        //viewHolder.level_tv.setText(mList.get(i).level);
        viewHolder.area_tv.setText(mList.get(i).area);
        return view;
    }

    class ViewHolder {
        ImageView imageView,level_tv;
        TextView name_tv,area_tv;
    }
}
