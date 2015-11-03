package com.yanhao.main.yanhaoandroid.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.bean.ConsultListBean;

import java.util.List;

/**
 * Created by Administrator on 2015/11/2 0002.
 */
public class ConsultListAdapter extends BaseAdapter{

    private LayoutInflater mInflater;
    private List<ConsultListBean> mList;
    /**不同item显示不同颜色**/
    private String[] colors = new String[]{
            "#d199e7","#7ebaeb","#82cfd2",
            "#7cbc8e","#f9bf91","#fb998d",
            "#7ebaeb","#f5d68b"
    };
    /**左边的色块**/
    private ShapeDrawable[] leftDrawables;

    public ConsultListAdapter(List<ConsultListBean> list, Context context) {
        this.mList = list;
        this.mInflater = LayoutInflater.from(context);
        initShapeDrawable();
    }

    /** 初始化不同颜色色块 */
    public void initShapeDrawable(){
        leftDrawables = new ShapeDrawable[colors.length];
        for (int i = 0; i < colors.length; i++) {
            leftDrawables[i] = new ShapeDrawable(new RectShape());
            leftDrawables[i].setIntrinsicHeight(200);
            leftDrawables[i].setIntrinsicWidth(25);
            leftDrawables[i].getPaint().setColor(Color.parseColor(colors[i]));
        }
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
        ViewHolder viewHolder ;

        if(view == null){
            viewHolder = new ViewHolder();
            view = mInflater.inflate(R.layout.consultlist_item, null);
            viewHolder.textView = (TextView) view.findViewById(R.id.item_tv);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        /*int I =colors.length % 8;
        String color = colors[I];*/
        //Toast.makeText(mContext,color,Toast.LENGTH_LONG).show();
        //加载色块
        viewHolder.textView.setCompoundDrawablesWithIntrinsicBounds(leftDrawables[i], null, null, null);
        viewHolder.textView.setText(mList.get(i).getText());
        return view;
    }
    class ViewHolder{
        //View view;
        TextView textView;
     }
}
