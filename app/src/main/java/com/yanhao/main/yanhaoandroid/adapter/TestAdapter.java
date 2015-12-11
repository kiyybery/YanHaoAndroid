package com.yanhao.main.yanhaoandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.YanHao;
import com.yanhao.main.yanhaoandroid.bean.TestBean;
import com.yanhao.main.yanhaoandroid.util.RelayoutViewTool;

import java.util.List;

/**
 * Created by Administrator on 2015/11/20 0020.
 */
public class TestAdapter extends BaseAdapter{

    private List<TestBean> mList;
    private LayoutInflater mInflater;

    public TestAdapter(List<TestBean> list,Context context) {
        this.mList = list;
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
            view = mInflater.inflate(R.layout.fragment_test_item,null);
            RelayoutViewTool.relayoutViewWithScale(view, YanHao.screenWidthScale);
            viewHolder.left_iv = (ImageView) view.findViewById(R.id.img_test);
            viewHolder.title_tv = (TextView) view.findViewById(R.id.title_tv);
            viewHolder.time_tv = (TextView) view.findViewById(R.id.answer_num);
            view.setTag(viewHolder);
        }else {

            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.left_iv.setImageResource(R.drawable.uc_my_background);
        viewHolder.title_tv.setText(mList.get(i).test_title);
        viewHolder.time_tv.setText(mList.get(i).time);

        return view;
    }

    class ViewHolder{

        ImageView left_iv,tag_iv;
        TextView title_tv,time_tv;
    }
}
