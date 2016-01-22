package com.yanhao.main.yanhaoandroid.usercenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.adapter.MyOrderAdapter;
import com.yanhao.main.yanhaoandroid.bean.OrderBean;
import com.yanhao.main.yanhaoandroid.bean.PersonalOrderBean;
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
        return view;
    }

    private void getUserReservation() {

        String url = "http://210.51.190.27:8082/getUserReservation.jspa";
        OkHttpUtils
                .post()
                .url(url)
                .addParams("userId", "WbqhSt2gqUU=")
                .build()
                .execute(new GetUserReservation());
    }
}
