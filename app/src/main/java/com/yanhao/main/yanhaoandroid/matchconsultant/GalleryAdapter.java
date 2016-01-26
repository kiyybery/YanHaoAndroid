package com.yanhao.main.yanhaoandroid.matchconsultant;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yanhao.main.yanhaoandroid.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GalleryAdapter extends
        RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private List<Text> mDatas;
    private List<Data> mValues;
    private Context mContext;
    private boolean isSelect;

    private int selectedPosition = 0;

    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }

    public interface OnItemClickLitener {

        void onItemClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public GalleryAdapter(Context context, List<Text> datats, List<Data> values) {
        mInflater = LayoutInflater.from(context);
        mDatas = datats;
        mValues = values;
        mContext = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View arg0) {
            super(arg0);
        }

        TextView mImg;
        TextView mTxt;
        ImageView mImgBottom;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.gallery_item,
                viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.mImg = (TextView) view
                .findViewById(R.id.id_index_gallery_item_image);
        viewHolder.mTxt = (TextView) view.findViewById(R.id.id_index_gallery_item_text);
        viewHolder.mImgBottom = (ImageView) view.findViewById(R.id.image_bottom);
        return viewHolder;
    }

    /**
     * 设置值
     */
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {

        viewHolder.mImg.setText(mDatas.get(i).getText());
        //viewHolder.mTxt.setText(String.valueOf(mValues.get(i).text_data));

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss E");
        Date dt = new Date(mValues.get(i).text_data * 1000);
        String sTime = sdf.format(dt);
        Calendar calendar = Calendar.getInstance();
        String w = getWeekOfDate(dt);
        viewHolder.mTxt.setText(w);

        if (mOnItemClickLitener != null) {

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(viewHolder.itemView, i);
                    //Toast.makeText(mContext, mValues.get(i).getText_data(), Toast.LENGTH_LONG).show();
                }
            });

            if (i == selectedPosition) {

                viewHolder.mImg.setSelected(true);
                viewHolder.mTxt.setSelected(true);
                viewHolder.mImgBottom.setVisibility(View.VISIBLE);
                isSelect = true;
            } else {

                viewHolder.mImg.setSelected(false);
                viewHolder.mTxt.setSelected(false);
                viewHolder.mImgBottom.setVisibility(View.GONE);
                isSelect = false;
            }

        }
    }

    public static String getWeekOfDate(Date date) {

        String[] weekOfDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};

        Calendar calendar = Calendar.getInstance();

        if (date != null) {

            calendar.setTime(date);

        }

        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        if (w < 0) {

            w = 0;

        }

        return weekOfDays[w];

    }


}
