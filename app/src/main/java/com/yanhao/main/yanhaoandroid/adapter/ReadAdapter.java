package com.yanhao.main.yanhaoandroid.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.YanHao;
import com.yanhao.main.yanhaoandroid.bean.CollectionBean;
import com.yanhao.main.yanhaoandroid.util.RelayoutViewTool;
import com.yanhao.main.yanhaoandroid.util.XCRoundRectImageView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;

import java.util.List;

/**
 * Created by Administrator on 2015/12/7 0007.
 */
public class ReadAdapter extends BaseAdapter {

    private List<CollectionBean> mList;
    private LayoutInflater mInflater;

    public ReadAdapter(List<CollectionBean> mList, Context context) {
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
        final ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = mInflater.inflate(R.layout.fragment_read_item, null);
            RelayoutViewTool.relayoutViewWithScale(view, YanHao.screenWidthScale);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.read_pic);
            viewHolder.title_tv = (TextView) view.findViewById(R.id.title_name);
            viewHolder.time_tv = (TextView) view.findViewById(R.id.read_time_tv);
            view.setTag(viewHolder);
        } else {

            viewHolder = (ViewHolder) view.getTag();
        }
        String url = mList.get(i).imageView;
        OkHttpUtils.get()
                .url(url)
                .tag(this)
                .build()
                .connTimeOut(20000)
                .readTimeOut(20000)
                .writeTimeOut(20000)
                .execute(new BitmapCallback(){
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(Bitmap bitmap) {

                        viewHolder.imageView.setImageBitmap(bitmap);
                    }
                });

        viewHolder.title_tv.setText(mList.get(i).getTitle());
        viewHolder.time_tv.setText(mList.get(i).getDate());
        return view;
    }

    class ViewHolder {

        ImageView imageView;
        TextView title_tv, time_tv;
    }
}
