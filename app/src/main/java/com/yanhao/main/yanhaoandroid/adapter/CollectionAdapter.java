package com.yanhao.main.yanhaoandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.bean.CollectionBean;

import java.util.List;

/**
 * Created by Administrator on 2015/11/9 0009.
 */
public class CollectionAdapter extends BaseAdapter{

    private List<CollectionBean> mList;
    private LayoutInflater mInflater;

    public CollectionAdapter(List<CollectionBean> mList,Context context) {
        this.mList = mList;
        this.mInflater = LayoutInflater.from(context);
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
            view = mInflater.inflate(R.layout.collection_item,null);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.img_collection);
            viewHolder.title_tv = (TextView) view.findViewById(R.id.title_tv);
            viewHolder.time_tv = (TextView) view.findViewById(R.id.time_tv);
            view.setTag(viewHolder);
        }else {

            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.imageView.setImageResource(R.drawable.uc_my_background);
        viewHolder.title_tv.setText(mList.get(i).getTitle());
        viewHolder.time_tv.setText(mList.get(i).getDate());
        return view;
    }

    class ViewHolder{

        ImageView imageView;
        TextView title_tv,time_tv;
    }
}
