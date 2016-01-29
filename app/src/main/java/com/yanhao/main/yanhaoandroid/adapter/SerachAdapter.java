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
import com.yanhao.main.yanhaoandroid.util.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2015/11/2 0002.
 */
public class SerachAdapter extends BaseAdapter {
    private List<ConstantBean> mList;
    private LayoutInflater mInflater;
    private ImageLoader imageLoader;
    private Context mContext;

    public SerachAdapter(Context ctx, List<ConstantBean> list) {
        mInflater = LayoutInflater.from(ctx);
        mList = list;
        imageLoader = new ImageLoader();
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
            view = mInflater.inflate(R.layout.fragment_matchitem, null);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.cv_comment_avatar);
            viewHolder.name_tv = (TextView) view.findViewById(R.id.consultant_name_match);
            viewHolder.level_tv = (ImageView) view.findViewById(R.id.consult_level_match);
            viewHolder.area_tv = (TextView) view.findViewById(R.id.consult_area);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        String url = mList.get(i).imageview;
        /*if (url.equals(" ")) {
            viewHolder.imageView.setImageResource(R.drawable.avatar_default);
        } else {
            imageLoader.showImageByAsyncTask(viewHolder.imageView, url);
        }*/
        /*Glide
                .with(mContext)
                .load(url)
                .placeholder(R.drawable.avatar_default)
                .into(viewHolder.imageView);*/
        if (url.equals("")) {

            viewHolder.imageView.setImageResource(R.drawable.avatar_default);
        } else {
            Glide.with(mContext).load(url).into(viewHolder.imageView);
        }
        viewHolder.name_tv.setText(mList.get(i).constantName);
        //viewHolder.level_tv.setText(mList.get(i).level);
        if (mList.get(i).level == 1) {
            //viewHolder.level_tv.setImageResource(R.drawable.yiji_icon);
            Glide.with(mContext).load(R.drawable.level_icon_yiji).into(viewHolder.level_tv);
        } else if (mList.get(i).level == 2) {
            // viewHolder.level_tv.setImageResource(R.drawable.erji_icon);
            Glide.with(mContext).load(R.drawable.level_icon_erji).into(viewHolder.level_tv);
        } else if (mList.get(i).level == 3) {
            //viewHolder.level_tv.setImageResource(R.drawable.sanji_icon);
            Glide.with(mContext).load(R.drawable.level_icon_sanji).into(viewHolder.level_tv);
        }

        //viewHolder.area_tv.setText(mList.get(i).area);

        return view;
    }

    class ViewHolder {
        ImageView imageView, level_tv;
        TextView name_tv, area_tv;
    }
}