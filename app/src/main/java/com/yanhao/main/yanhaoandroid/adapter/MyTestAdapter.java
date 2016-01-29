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
import com.yanhao.main.yanhaoandroid.YanHao;
import com.yanhao.main.yanhaoandroid.banner.MyTest;
import com.yanhao.main.yanhaoandroid.matchconsultant.Text;
import com.yanhao.main.yanhaoandroid.util.RelayoutViewTool;

import java.util.List;

/**
 * Created by Administrator on 2016/1/28 0028.
 */
public class MyTestAdapter extends BaseAdapter {

    private List<MyTest> mList;
    private LayoutInflater mInflater;
    private Context mContext;

    public MyTestAdapter(Context context, List<MyTest> list) {

        this.mInflater = LayoutInflater.from(context);
        this.mList = list;
        this.mContext = context;
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
            view = mInflater.inflate(R.layout.test_item, null);
            RelayoutViewTool.relayoutViewWithScale(view, YanHao.screenWidthScale);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.img_test_my);
            viewHolder.textView = (TextView) view.findViewById(R.id.title_tv_my);
            view.setTag(viewHolder);
        } else {

            viewHolder = (ViewHolder) view.getTag();
        }

        Glide.with(mContext).load(mList.get(i).imageUrl).into(viewHolder.imageView);
        viewHolder.textView.setText(mList.get(i).name);
        return view;
    }

    class ViewHolder {

        ImageView imageView;
        TextView textView;
    }
}
