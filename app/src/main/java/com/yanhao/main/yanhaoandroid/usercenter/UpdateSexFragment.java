package com.yanhao.main.yanhaoandroid.usercenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yanhao.main.yanhaoandroid.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2015/11/10 0010.
 */
public class UpdateSexFragment extends Fragment{

    String[] sex = new String[]{
            "男",//1
            "女",//2
    };
    private List<String> mList;
    private ListView mListView;
    private SexAdapter mSexAdapter;

    public static UpdateSexFragment newInstance() {

        UpdateSexFragment fragment = new UpdateSexFragment();
        Bundle data = new Bundle();
        data.putString("title", " ");
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_updatesex,container,false);
        mList = Arrays.asList(sex);

        mSexAdapter = new SexAdapter(getActivity());
        mListView = (ListView) view.findViewById(R.id.list_sex);
        mListView.setAdapter(new SexAdapter(getActivity()));
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mSexAdapter.setSelectpoistion(i);
                mSexAdapter.notifyDataSetInvalidated();
                String sexname = mSexAdapter.getItem(i);
                Toast.makeText(getActivity(),i+"sexname",Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

    private class SexAdapter extends BaseAdapter{

        int selectpoistion = 0;
        private LayoutInflater mInflater;

        public SexAdapter(Context context){

            this.mInflater = LayoutInflater.from(context);
        }

        public void setSelectpoistion(int i){
            selectpoistion = i;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public String getItem(int i) {
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
                view = mInflater.inflate(R.layout.item_updatesex,null);
                viewHolder.tv_sex = (TextView) view.findViewById(R.id.tvSexName);
                viewHolder.ib_sex = (ImageButton) view.findViewById(R.id.ibSexField);
                view.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) view.getTag();
            }

            String sexname = getItem(i);
            viewHolder.tv_sex.setText(sexname);
            if(selectpoistion != i){
                viewHolder.ib_sex.setVisibility(View.VISIBLE);
            }else{
                viewHolder.ib_sex.setVisibility(View.INVISIBLE);
            }
            return view;
        }
    }

    class ViewHolder{
        TextView tv_sex;
        ImageButton ib_sex;
    }
}
