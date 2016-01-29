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
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.adapter.MyOrderAdapter;
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

    private class GetUserReservation extends StringCallback {

        @Override
        public void onError(Request request, Exception e) {

        }

        @Override
        public void onResponse(String s) {

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray array = jsonObject.getJSONArray("reservationList");
                for (int i = 0; i < array.length(); i++) {

                    JSONObject job = (JSONObject) array.get(i);
                    mOrderBean = new OrderBean();
                    mOrderBean.setConstantName(job.getString("counselorName"));
                    mOrderBean.setImageview(job.getString("portraitUrl"));
                    mOrderBean.setArea(job.getString("consultTypeName"));
                    mOrderBean.setData(job.getString("reservationTime"));

                    JSONObject object = job.getJSONObject("details");
                    mOrderBean.setAddress(object.getString("address"));
                    mOrderBean.setServicetel(object.getString("serviceTel"));
                    Log.i("order_desp", object.getString("address") + object.getString("servicetel"));

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

        //getUserReservation();
        getUserReservationForQiNiu();
        mBackImage = (ImageView) view.findViewById(R.id.iv_section_title_back);
        mBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        mTitle = (TextView) view.findViewById(R.id.tv_section_title_title);
        mTitle.setText("我的预约");

        mList = new ArrayList<>();
        /*for (int i = 0; i < 20; i++) {
            mOrderBean = new OrderBean();
            mOrderBean.setConstantName("咨询师"+i);
            mOrderBean.setLevel("国家" + i+"级");
            mOrderBean.setArea("神经病 亲子关系 婚姻关系" + i);
            mOrderBean.setData("2015年2月" + i+"日");
            mList.add(mOrderBean);
        }*/

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
                intent.setClass(getActivity(), AllOrderActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    /*private void getUserReservation() {

        String url = "http://210.51.190.27:8082/getUserReservation.jspa";
        OkHttpUtils
                .post()
                .url(url)
                .addParams("userId", PrefHelper.get().getString("userId", ""))
                .build()
                .execute(new GetUserReservation());
    }*/

    private void getUserReservationForQiNiu() {

        String url = "http://7xop51.com1.z0.glb.clouddn.com/order_desp_get.txt";

        OkHttpUtils
                .post()
                .url(url)
                .build()
                .execute(new GetUserReservation());
    }
}
