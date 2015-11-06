package com.yanhao.main.yanhaoandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.bean.AreaItem;
import com.yanhao.main.yanhaoandroid.consult.ConsultListActivity;
import com.yanhao.main.yanhaoandroid.util.CustomProgressDialog;

import java.util.List;

/**
 * Created by Administrator on 2015/11/2 0002.
 */
public class AreaGridAdapter extends BaseAdapter {

    private Context mContext;
    private List<AreaItem> mList;

    public AreaGridAdapter(Context mContext,List<AreaItem> mList){
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList == null ? null : mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        AreaItem areaItem = (AreaItem) getItem(i);
        view = LayoutInflater.from(mContext).inflate(R.layout.area_item, null);
        ImageView areaImageView = (ImageView) view.findViewById(R.id.area_iv);
        TextView areaTextView = (TextView) view.findViewById(R.id.area_tv);
        areaImageView.setImageResource(areaItem.getImg());
        areaTextView.setText(areaItem.getText());
        final String select = areaItem.getText();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("itemId",i);
                intent.putExtra("itemName",select);
                intent.setClass(mContext, ConsultListActivity.class);
                mContext.startActivity(intent);
            }
        });
        return view;
    }
}
