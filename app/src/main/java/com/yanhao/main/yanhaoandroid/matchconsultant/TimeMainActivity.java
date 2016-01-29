package com.yanhao.main.yanhaoandroid.matchconsultant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.util.PrefHelper;
import com.yanhao.main.yanhaoandroid.util.SecurityUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.apache.commons.codec.Encoder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class TimeMainActivity extends Activity implements View.OnClickListener {

    private String period;
    private int status;
    private List newList;
    private TextView mTitle;
    private RecyclerView mRecyclerView;
    private GridView mGridView;
    private GalleryAdapter mAdapter;
    private TimeAdapter mTimeAdapter;
    private List<Text> mDatas = new ArrayList<>();
    private List<Data> mValues = new ArrayList<>();
    private List<ScheduleData> mTimes = new ArrayList<>();
    private String userId;
    private String data, time;
    private int reservationId;
    private Button mOrderBtn;
    private ProgressBar progressBar;

    private List<ReservationData> reservationDatas = new ArrayList<>();
    private String[] texts = new String[]{

            "周一", "周二", "周三", "周四", "周五", "周六", "周日", "周二", "周四"
    };

    private String[] data_tv = new String[]{

            "10-11", "10-12", "10-13", "10-16", "10-20", "10-23", "10-30", "11-3", "11-5"
    };

    private String[] times = new String[]{

            "10:00 - 11:00", "12:30 - 13:30", "14:00 - 15:00",
            "16:30 - 17:30"
    };

    private class GetReservationSchedule extends StringCallback {

        @Override
        public void onError(Request request, Exception e) {

        }

        @Override
        public void onResponse(String s) {


            try {
                JSONObject jsonObject = new JSONObject(s);
                progressBar.setVisibility(View.GONE);
                JSONArray reservationArray = jsonObject.getJSONArray("reservationList");
                for (int i = 0; i < reservationArray.length(); i++) {


                    JSONObject job = (JSONObject) reservationArray.get(i);
                    ReservationData reservationData = new ReservationData();
                    List<ScheduleData> scheduleDatas = new ArrayList<>();
                    reservationData.date = job.getString("date");
                    reservationData.timeMillis = job.getLong("timeMillis");
                    JSONArray scheduleArr = job.getJSONArray("scheduleList");
                    for (int j = 0; j < scheduleArr.length(); j++) {
                        JSONObject o = scheduleArr.getJSONObject(j);
                        ScheduleData data = new ScheduleData();
                        data.period = o.getString("period");
                        data.reservationId = o.getInt("reservationId");
                        data.status = o.getInt("status");
                        scheduleDatas.add(data);
                    }
                    reservationData.scheduleList = scheduleDatas;
                    reservationDatas.add(reservationData);


                    Text text = new Text();
                    text.setText(job.getString("date"));
                    mDatas.add(text);
                    long timeMillis = job.getInt("timeMillis");
                    Data data = new Data();
                    data.setText_data(timeMillis);
                    mValues.add(data);
                    mAdapter.notifyDataSetChanged();
                }

                mTimes.addAll(reservationDatas.get(0).scheduleList);
                mTimeAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordertime);

        userId = getIntent().getStringExtra("userId");

        mTitle = (TextView) findViewById(R.id.tv_section_title_title);
        mTitle.setText("预约时间");
        mOrderBtn = (Button) findViewById(R.id.btnpay_orderTime);
        mOrderBtn.setOnClickListener(this);
        progressBar = (ProgressBar) findViewById(R.id.progressbar_selectTime);
        /*initDatas();
        initValues();*/
        //initTimes();
        getData(userId);
        Log.i("time_userId", userId);
        //Toast.makeText(TimeMainActivity.this, userId, Toast.LENGTH_LONG).show();
        mGridView = (GridView) findViewById(R.id.id_gridview_grid);
        mTimeAdapter = new TimeAdapter(this, mTimes);
        mGridView.setAdapter(mTimeAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                mTimeAdapter.setSelectedPosition(i);
                mTimeAdapter.notifyDataSetInvalidated();
                time = mTimes.get(i).period;
                reservationId = mTimes.get(i).reservationId;
                Log.i("reservationId", reservationId + "");
                Toast.makeText(TimeMainActivity.this, mTimes.get(i).reservationId + "", Toast.LENGTH_LONG).show();
                //save();
            }
        });
        //得到控件
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview_horizontal);
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        //设置适配器
        mAdapter = new GalleryAdapter(this, mDatas, mValues);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickLitener(new GalleryAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                /*Toast.makeText(TimeMainActivity.this, mDatas.get(position).getText() + "", Toast.LENGTH_SHORT)
                        .show();*/
                data = mDatas.get(position).getText();
                mAdapter.setSelectedPosition(position);
                mAdapter.notifyDataSetChanged();

                mTimes.clear();
                mTimes.addAll(reservationDatas.get(position).scheduleList);
                mTimeAdapter.notifyDataSetChanged();
            }
        });
    }

    public void save() {

        ScheduleData scheduledata = new ScheduleData();
        Intent intent = new Intent();
        intent.putExtra("data", data);
        intent.putExtra("time", time);
        intent.putExtra("reservationId", reservationId);
        finish(Activity.RESULT_OK, intent);
    }

    protected void finish(int resultCode, Intent data) {
        if (TimeMainActivity.this != null) {
            TimeMainActivity.this.setResult(resultCode, data);
            TimeMainActivity.this.finish();
        }
    }

    public void getData(String userId) {

        String url = "http://210.51.190.27:8082/getReservationSchedule.jspa";
        String counselorId = SecurityUtil.encrypt("6");
        String urlcoun_id = null;
        try {
            urlcoun_id = URLEncoder.encode("H+csS/mcKCU=", "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        OkHttpUtils
                .post()
                .url(url)
                .addParams("userId", PrefHelper.get().getString("userId", ""))
                .addParams("counselorId", userId)
                .build()
                .execute(new GetReservationSchedule());
    }

    /*public void getData() {

        String url = "http://7xop51.com1.z0.glb.clouddn.com/ordertime_1.txt";

        OkHttpUtils.postString()
                .url(url)
                .content(new Gson().toJson(new Times()))
                .build()
                .execute(new GetReservationSchedule());

    }*/

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnpay_orderTime:

                save();
                break;
        }
    }
}
