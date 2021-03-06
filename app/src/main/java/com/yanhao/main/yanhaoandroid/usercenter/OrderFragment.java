package com.yanhao.main.yanhaoandroid.usercenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.adapter.MyOrderAdapter;
import com.yanhao.main.yanhaoandroid.banner.T;
import com.yanhao.main.yanhaoandroid.bean.OrderBean;
import com.yanhao.main.yanhaoandroid.bean.OrderInfo;
import com.yanhao.main.yanhaoandroid.bean.PersonalOrderBean;
import com.yanhao.main.yanhaoandroid.util.PrefHelper;
import com.yanhao.main.yanhaoandroid.util.SecurityUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/9 0009.
 */
public class OrderFragment extends Fragment {

    private ListView mOrderListView;
    private List<OrderBean> mList;
    private OrderBean mOrderBean;
    private TextView mTitle;
    private ImageView mBackImage;
    private MyOrderAdapter mMyOrderAdapter;
    private ProgressBar mBar;
    private RelativeLayout empty_layout;

    private class GetUserReservation extends StringCallback {

        @Override
        public void onError(Request request, Exception e) {

        }

        @Override
        public void onResponse(String s) {

            try {
                JSONObject jsonObject = new JSONObject(s);
                Log.i("reservationList", s);
                mBar.setVisibility(View.GONE);
                JSONArray array = jsonObject.getJSONArray("reservationList");
                if (array.length() == 0) {

                    empty_layout.setVisibility(View.VISIBLE);
                }
                for (int i = 0; i < array.length(); i++) {

                    JSONObject job = (JSONObject) array.get(i);
                    mOrderBean = new OrderBean();
                    mOrderBean.setConstantName(job.getString("counselorName"));
                    mOrderBean.setImageview(job.getString("portraitUrl"));
                    mOrderBean.setArea(job.getString("consultTypeName"));
                    mOrderBean.setData(job.getString("reservationTime"));
                    mOrderBean.setReservationId(job.getInt("reservationId"));
                    mOrderBean.setPayStatus(job.getInt("payStatus"));

                    JSONObject object = job.getJSONObject("details");
                    mOrderBean.setAddress(object.getString("address"));
                    mOrderBean.setServicetel(object.getString("serviceTel"));
                    Log.i("order_desp", object.getString("address") + object.getString("serviceTel"));

                    mList.add(mOrderBean);
                    mMyOrderAdapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static OrderFragment newInstance() {
        OrderFragment fragment = new OrderFragment();
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
        View view = inflater.inflate(R.layout.fragment_myorder, container, false);

        getUserReservation();
        //getUserReservationForQiNiu();
        empty_layout = (RelativeLayout) view.findViewById(R.id.empty_layout);
        mBackImage = (ImageView) view.findViewById(R.id.iv_section_title_back);
        mBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        mTitle = (TextView) view.findViewById(R.id.tv_section_title_title);
        mTitle.setText("我的预约");

        mBar = (ProgressBar) view.findViewById(R.id.progressbar_myorder);

        mList = new ArrayList<>();

        mOrderListView = (ListView) view.findViewById(R.id.order_listview);
        mMyOrderAdapter = new MyOrderAdapter(getActivity(), mList);
        mOrderListView.setAdapter(mMyOrderAdapter);
        mOrderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent();
                intent.putExtra("counselorName", mList.get(i).constantName);
                intent.putExtra("portraitUrl", mList.get(i).imageview);
                intent.putExtra("consultTypeName", mList.get(i).area);
                intent.putExtra("reservationTime", mList.get(i).data);
                intent.putExtra("address", mList.get(i).address);
                intent.putExtra("servicetel", mList.get(i).servicetel);
                intent.putExtra("payStatus", mList.get(i).payStatus);
                intent.setClass(getActivity(), AllOrderActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void getUserReservation() {

        String url = "http://210.51.190.27:8082/getUserReservation.jspa";
        OkHttpUtils
                .post()
                .url(url)
                .addParams("userId", PrefHelper.get().getString("userId", ""))
                .build()
                .execute(new GetUserReservation());
    }
}
