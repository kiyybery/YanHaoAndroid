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
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.adapter.PersonalOrderAdapter;
import com.yanhao.main.yanhaoandroid.bean.OrderBean;
import com.yanhao.main.yanhaoandroid.bean.PersonalOrderBean;
import com.yanhao.main.yanhaoandroid.util.PrefHelper;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/11 0011.
 */
public class OrderPersonalFragment extends Fragment {

    private ListView mPersonalOrderListView;
    private List<PersonalOrderBean> mList;
    private PersonalOrderBean mPersonalOrderBean;
    private TextView mTitle;
    private ImageView mBackImage;
    private PersonalOrderAdapter mPersonalOrderAdapter;

    private class GetUserReservation extends StringCallback {

        @Override
        public void onError(Request request, Exception e) {

        }

        @Override
        public void onResponse(String s) {

            try {
                JSONObject jsonObject = new JSONObject(s);
                Log.i("coustour_info", s);
                Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
                JSONArray array = jsonObject.getJSONArray("reservationList");
                for (int i = 0; i < array.length(); i++) {

                    JSONObject job = (JSONObject) array.get(i);
                    mPersonalOrderBean = new PersonalOrderBean();
                    mPersonalOrderBean.setPresonalName(job.getString("userName"));
                    mPersonalOrderBean.setImage(job.getString("portraitUrl"));
                    mPersonalOrderBean.setType(job.getString("consultTypeName"));
                    mPersonalOrderBean.setTime(job.getString("reservationTime"));

                    JSONObject object = job.getJSONObject("details");
                    mPersonalOrderBean.setIssue(object.getString("issue"));
                    mPersonalOrderBean.setMobile(object.getString("mobile"));
                    mPersonalOrderBean.setNote(object.getString("note"));

                    mList.add(mPersonalOrderBean);
                    mPersonalOrderAdapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public static OrderPersonalFragment newInstance() {
        OrderPersonalFragment fragment = new OrderPersonalFragment();
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
        View view = inflater.inflate(R.layout.fragment_orderpersonal, container, false);

        getUserReservation();

        //Toast.makeText(getActivity(), "Consultor order", Toast.LENGTH_LONG).show();
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
            mPersonalOrderBean = new PersonalOrderBean();
            mPersonalOrderBean.setPresonalName("萌萌");
            mPersonalOrderBean.setType("电话咨询");
            mPersonalOrderBean.setTime("5月"+i+"日下午"+i+"点");
            mList.add(mPersonalOrderBean);
        }*/
        mPersonalOrderListView = (ListView) view.findViewById(R.id.personal_order_listview);
        mPersonalOrderAdapter = new PersonalOrderAdapter(getActivity(), mList);
        mPersonalOrderListView.setAdapter(mPersonalOrderAdapter);
        mPersonalOrderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra("portraitUrl", mList.get(i).getImage());
                intent.putExtra("userName", mList.get(i).getPresonalName());
                intent.putExtra("reservationTime", mList.get(i).getTime());
                intent.putExtra("consultTypeName", mList.get(i).getType());
                intent.putExtra("issue", mList.get(i).getIssue());
                intent.putExtra("mobile", mList.get(i).getMobile());
                intent.putExtra("note", mList.get(i).getNote());
                intent.setClass(getActivity(), PersonalOrderActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void getUserReservation() {

        String url = "http://210.51.190.27:8082/getCounselorReservation.jspa";
        Log.i("counselor_id", PrefHelper.get().getString("userId", ""));
        OkHttpUtils
                .post()
                .url(url)
                .addParams("userId", PrefHelper.get().getString("userId", ""))
                .build()
                .execute(new GetUserReservation());
    }
}
