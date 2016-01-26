package com.yanhao.main.yanhaoandroid.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.okhttp.Request;
import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.YanHao;
import com.yanhao.main.yanhaoandroid.bean.MyCollection;
import com.yanhao.main.yanhaoandroid.util.RelayoutViewTool;
import com.yanhao.main.yanhaoandroid.util.XCRoundRectImageView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;

import java.util.List;

/**
 * Created by Administrator on 2016/1/26 0026.
 */
public class MyCollectionAdapter extends BaseAdapter {

    private Context mContext;
    private List<MyCollection> mList;
    private LayoutInflater mInflater;

    public MyCollectionAdapter(List<MyCollection> mList, Context context) {

        this.mList = mList;
        this.mInflater = LayoutInflater.from(context);
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

        final ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = mInflater.inflate(R.layout.mycollection_item, null);
            RelayoutViewTool.relayoutViewWithScale(view, YanHao.screenWidthScale);

            viewHolder.imageView = (ImageView) view.findViewById(R.id.mycollection_img);
            viewHolder.textView = (TextView) view.findViewById(R.id.title_tv);
            view.setTag(viewHolder);
        } else {

            viewHolder = (ViewHolder) view.getTag();
        }

        String imageUrl = mList.get(i).imageUrl;
        Glide.with(mContext).load(imageUrl).into(viewHolder.imageView);
        viewHolder.textView.setText(mList.get(i).title);
        return view;
    }

    class ViewHolder {

        ImageView imageView;
        TextView textView;
    }
}
