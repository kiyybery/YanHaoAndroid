package com.yanhao.main.yanhaoandroid.usercenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.YanHao;
import com.yanhao.main.yanhaoandroid.adapter.MyOrderAdapter;
import com.yanhao.main.yanhaoandroid.banner.T;
import com.yanhao.main.yanhaoandroid.bean.OrderBean;
import com.yanhao.main.yanhaoandroid.util.PrefHelper;
import com.yanhao.main.yanhaoandroid.util.RelayoutViewTool;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/9 0009.
 */
public class RecordFragment extends Fragment {

    private ListView mListView;
    private OrderBean mOrderBean;
    private List<OrderBean> mList;
    private TextView mTitle;
    private ImageView mBackImage;
    private MyOrderAdapter mAdapter;
    private RelativeLayout empty_layout;
    private int userType;

    private class GetUserReservation extends StringCallback {

        @Override
        public void onError(Request request, Exception e) {

        }

        @Override
        public void onResponse(String s) {

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray array = jsonObject.getJSONArray("reservationRecordList");
                if (array.length() == 0) {

                    empty_layout.setVisibility(View.VISIBLE);
                }
                for (int i = 0; i < array.length(); i++) {

                    JSONObject job = (JSONObject) array.get(i);
                    mOrderBean = new OrderBean();
                    mOrderBean.setConstantName(job.getString("name"));
                    mOrderBean.setImageview(job.getString("portraitUrl"));
                    mOrderBean.setArea(job.getString("consultTypeName"));
                    mOrderBean.setData(job.getString("reservationTime"));

                    mList.add(mOrderBean);
                    mAdapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public static RecordFragment newInstance() {
        RecordFragment fragment = new RecordFragment();
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
        View view = inflater.inflate(R.layout.fragment_myrecord, container, false);
        RelayoutViewTool.relayoutViewWithScale(view, YanHao.screenWidthScale);

        empty_layout = (RelativeLayout) view.findViewById(R.id.empty_layout);
        userType = getArguments().getInt("userType");
        if (userType == 0) {

            getUserReservation();
        } else {

            getConsultorReservation();
        }

        mList = new ArrayList<>();
        /*for (int i = 0; i < 20; i++) {
            mOrderBean = new OrderBean();
            mOrderBean.setConstantName("咨询师" + i);
            mOrderBean.setLevel("国家" + i + "级");
            mOrderBean.setArea("擅长 亲子关系 婚姻关系 " + i);
            mOrderBean.setData("2015年12月" + i + "日");
            mList.add(mOrderBean);
        }*/
        mListView = (ListView) view.findViewById(R.id.record_listview);
        mAdapter = new MyOrderAdapter(getActivity(), mList);
        mListView.setAdapter(mAdapter);

        mBackImage = (ImageView) view.findViewById(R.id.iv_section_title_back);
        mBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        mTitle = (TextView) view.findViewById(R.id.tv_section_title_title);
        mTitle.setText("咨询记录");
        return view;
    }

    private void getUserReservation() {

        String url = "http://210.51.190.27:8082/getUserReservationRecord.jspa";
        OkHttpUtils
                .post()
                .url(url)
                .addParams("userId", PrefHelper.get().getString("userId", ""))
                .build()
                .execute(new GetUserReservation());
    }

    private void getConsultorReservation() {

        String url = "http://210.51.190.27:8082/getCounselorReservationRecord.jspa";
        OkHttpUtils
                .post()
                .url(url)
                .addParams("userId", PrefHelper.get().getString("userId", ""))
                .build()
                .execute(new GetUserReservation());
    }
}
