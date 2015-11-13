package com.yanhao.main.yanhaoandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.bean.PersonalOrderBean;

import java.util.List;

/**
 * Created by Administrator on 2015/11/11 0011.
 */
public class PersonalOrderAdapter extends BaseAdapter {

    private List<PersonalOrderBean> mList;
    private LayoutInflater mInflater;

    public PersonalOrderAdapter(Context context, List<PersonalOrderBean> list) {

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
            view = mInflater.inflate(R.layout.personal_order_item, null);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.cv_presonal_order_avatar);
            viewHolder.personal_name = (TextView) view.findViewById(R.id.presonal_order_name);
            viewHolder.personal_type = (TextView) view.findViewById(R.id.presonal_order_area_tv);
            viewHolder.personal_time = (TextView) view.findViewById(R.id.presonal_order_date_tv);
            view.setTag(viewHolder);
        } else {

            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.imageView.setImageResource(R.drawable.imgmengmengava);
        viewHolder.personal_name.setText(mList.get(i).getPresonalName());
        viewHolder.personal_type.setText(mList.get(i).getType());
        viewHolder.personal_time.setText(mList.get(i).getTime());
        return view;
    }

    class ViewHolder {

        ImageView imageView;
        TextView personal_name, personal_type, personal_time;
    }
}
