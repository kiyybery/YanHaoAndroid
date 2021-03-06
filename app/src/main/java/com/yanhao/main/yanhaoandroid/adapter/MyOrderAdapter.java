package com.yanhao.main.yanhaoandroid.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.bean.ConstantBean;
import com.yanhao.main.yanhaoandroid.bean.OrderBean;

import java.util.List;

/**
 * Created by Administrator on 2015/11/9 0009.
 */
public class MyOrderAdapter extends BaseAdapter {

    private List<OrderBean> mList;
    private LayoutInflater mInflater;
    private Context mContext;

    public MyOrderAdapter(Context ctx, List<OrderBean> list) {
        mInflater = LayoutInflater.from(ctx);
        mList = list;
        mContext = ctx;
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
            view = mInflater.inflate(R.layout.order_item, null);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.cv_order_avatar);
            viewHolder.name_tv = (TextView) view.findViewById(R.id.order_name);
            viewHolder.level_tv = (ImageView) view.findViewById(R.id.order_level);
            viewHolder.area_tv = (TextView) view.findViewById(R.id.order_area_tv);
            viewHolder.date_tv = (TextView) view.findViewById(R.id.order_date_tv);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        String url = mList.get(i).imageview;
        if (!url.equals("")) {

            Glide.with(mContext).load(url).dontTransform().error(R.drawable.kuangge).into(viewHolder.imageView);
        } else {

            Glide.with(mContext).load(R.drawable.avatar_default).into(viewHolder.imageView);
        }
        //viewHolder.imageView.setImageResource(R.drawable.imgmengmengava);
        viewHolder.name_tv.setText(mList.get(i).constantName);
        //viewHolder.level_tv.setText(mList.get(i).level);
        viewHolder.area_tv.setText(mList.get(i).area);
        viewHolder.date_tv.setText(mList.get(i).data);
        return view;
    }

    class ViewHolder {
        ImageView imageView, level_tv;
        TextView name_tv, area_tv, date_tv;
    }
}
