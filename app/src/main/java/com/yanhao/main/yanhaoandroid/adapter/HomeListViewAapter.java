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
import com.yanhao.main.yanhaoandroid.bean.HomeActivityBean;
import com.yanhao.main.yanhaoandroid.util.ImageLoader;
import com.yanhao.main.yanhaoandroid.util.RelayoutViewTool;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;

import java.util.List;

/**
 * Created by Administrator on 2015/12/3 0003.
 */
public class HomeListViewAapter extends BaseAdapter {


    private List<HomeActivityBean> mList;
    private LayoutInflater mInflater;
    private ImageLoader imageLoader;

    public HomeListViewAapter(List<HomeActivityBean> list, Context ctx) {

        this.mList = list;
        this.mInflater = LayoutInflater.from(ctx);
        imageLoader = new ImageLoader();
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
            view = mInflater.inflate(R.layout.fragment_homeactivity_item, null);
            RelayoutViewTool.relayoutViewWithScale(view, YanHao.screenWidthScale);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.home_item_iv);
            viewHolder.title_tv = (TextView) view.findViewById(R.id.home_item_title);
            viewHolder.teacher_tv = (TextView) view.findViewById(R.id.home_item_teacher);
            viewHolder.pay_tv = (TextView) view.findViewById(R.id.home_item_pay_cash);
            view.setTag(viewHolder);
        } else {

            viewHolder = (ViewHolder) view.getTag();
        }

        String url = mList.get(i).image;
        /*if(url.equals("")){

            viewHolder.imageView.setImageResource(R.drawable.imgmengmengava);
        }else {
            imageLoader.showImageByAsyncTask(viewHolder.imageView,url);
        }*/

        OkHttpUtils.get()
                .url(url)
                .tag(this)
                .build()
                .connTimeOut(20000)
                .readTimeOut(20000)
                .writeTimeOut(20000)
                .execute(new BitmapCallback() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(Bitmap bitmap) {
                viewHolder.imageView.setImageBitmap(bitmap);
            }
        });
        viewHolder.title_tv.setText(mList.get(i).title);
        viewHolder.teacher_tv.setText(mList.get(i).teacher);
        viewHolder.pay_tv.setText(mList.get(i).pay);
        return view;
    }

    class ViewHolder {

        ImageView imageView;
        TextView title_tv, teacher_tv, pay_tv;
    }
}
