package com.yanhao.main.yanhaoandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.YanHao;
import com.yanhao.main.yanhaoandroid.bean.ChatListViewBean;
import com.yanhao.main.yanhaoandroid.util.RelayoutViewTool;

import java.util.List;


/**
 * Created by Administrator on 2015/11/25 0025.
 */
public class ChatItemListViewAdapter extends BaseAdapter {

    private List<ChatListViewBean> mData;
    private LayoutInflater mInflater;

    public ChatItemListViewAdapter(Context context,
                                   List data) {
        this.mData = data;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        ChatListViewBean bean = mData.get(position);
        return bean.getType();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            if (getItemViewType(position) == 0) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(
                        R.layout.chat_item_itemin, null);
                RelayoutViewTool.relayoutViewWithScale(convertView, YanHao.screenWidthScale);
                holder.text = (TextView) convertView.findViewById(
                        R.id.text_in);
            } else {
                holder = new ViewHolder();
                convertView = mInflater.inflate(
                        R.layout.chat_item_itemout, null);
                RelayoutViewTool.relayoutViewWithScale(convertView, YanHao.screenWidthScale);
                holder.text = (TextView) convertView.findViewById(
                        R.id.text_out);
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.text.setText(mData.get(position).getText());
        return convertView;
    }

    public final class ViewHolder {
        public TextView text;
    }
}
