package com.yanhao.main.yanhaoandroid.wxpay;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yanhao.main.yanhaoandroid.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/2/4 0004.
 */
public class WXPayActivity extends Activity {

    private IWXAPI api;
    private String appid, partnerid, prepayid, noncestr, timestamp, packagename, sign;
    String url = "http://210.51.190.27:8582/order/pay.jspa";
    private String orderId;

    private class Pay extends StringCallback {


        @Override
        public void onError(Request request, Exception e) {

        }

        @Override
        public void onResponse(String s) {

            Toast.makeText(WXPayActivity.this, s, Toast.LENGTH_LONG).show();
            try {
                JSONObject json = new JSONObject(s);
                PayReq req = new PayReq();
                req.appId = json.getString("appid");
                req.partnerId = json.getString("partnerid");
                req.prepayId = json.getString("prepayid");
                req.nonceStr = json.getString("noncestr");
                req.timeStamp = json.getString("timestamp");
                req.packageValue = json.getString("package");
                req.sign = json.getString("sign");
                Log.i("wx_info", s);
                Toast.makeText(WXPayActivity.this, "正常调起支付", Toast.LENGTH_SHORT).show();
                // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                api.sendReq(req);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pay);

        orderId = getIntent().getStringExtra("shareTitle");
        Log.i("pay_orderId", orderId + "");
        api = WXAPIFactory.createWXAPI(this, "wx49293149c06c00f7");

        OkHttpUtils
                .post()
                .url(url)
                .addParams("orderType", "2")
                .addParams("orderId", orderId + "")
                .addParams("payType", 2 + "")
                .build()
                .execute(new Pay());
    }
}
