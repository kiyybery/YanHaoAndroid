package com.yanhao.main.yanhaoandroid.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.YanHao;
import com.yanhao.main.yanhaoandroid.alipay.PayActivity;
import com.yanhao.main.yanhaoandroid.alipay.pay.ExternalPartner;
import com.yanhao.main.yanhaoandroid.alipay.pay.Keys;
import com.yanhao.main.yanhaoandroid.alipay.pay.PayResult;
import com.yanhao.main.yanhaoandroid.test.WebViewTest;
import com.yanhao.main.yanhaoandroid.usercenter.ModifyOKActivity;
import com.yanhao.main.yanhaoandroid.util.PrefHelper;
import com.yanhao.main.yanhaoandroid.util.RelayoutViewTool;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2015/12/1 0001.
 */
public class PayPageFragment extends Fragment implements View.OnClickListener {


    // 商户PID
    public static final String PARTNER = Keys.DEFAULT_PARTNER;
    // 商户收款账号
    public static final String SELLER = Keys.DEFAULT_SELLER;
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = Keys.PRIVATE;
    // 支付宝公钥
    public static final String RSA_PUBLIC = Keys.PUBLIC;

    private static final int SDK_PAY_FLAG = 1;

    private static final int SDK_CHECK_FLAG = 2;
    private Button mPaybtn;
    private ImageView mWXpay_img, mAlipay_img;
    private boolean isSelect;
    private TextView mTitle;
    private int reservationId;

    private class SignOrder extends StringCallback {


        @Override
        public void onError(Request request, Exception e) {

            Toast.makeText(getActivity(), e + "", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onResponse(String s) {

            Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
            try {
                JSONObject jsonObject = new JSONObject(s);
                Toast.makeText(getActivity(), jsonObject.getString("info"), Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);

                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    String resultInfo = payResult.getResult();

                    String resultStatus = payResult.getResultStatus();

                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(getActivity(), "支付成功",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(getActivity(), "支付结果确认中",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(getActivity(), "支付失败",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                }
                case SDK_CHECK_FLAG: {
                    Toast.makeText(getActivity(), "检查结果为：" + msg.obj,
                            Toast.LENGTH_SHORT).show();
                    break;
                }
                default:
                    break;
            }
        }
    };

    public static PayPageFragment newInstance() {

        PayPageFragment fragment = new PayPageFragment();
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
        View view = inflater.inflate(R.layout.fragment_paypage, container, false);
        RelayoutViewTool.relayoutViewWithScale(view, YanHao.screenWidthScale);

        reservationId = getArguments().getInt("reservationId");
        mPaybtn = (Button) view.findViewById(R.id.btnpay);
        mPaybtn.setOnClickListener(this);

        mTitle = (TextView) view.findViewById(R.id.tv_section_title_title);
        mTitle.setText("资费支付");
        /*mWXpay_img = (ImageView) view.findViewById(R.id.wechat_pay_iv_check);
        mWXpay_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSelect) {

                    mWXpay_img.setSelected(false);
                    isSelect = false;
                } else {

                    mWXpay_img.setSelected(true);
                    isSelect = true;
                }
            }
        });*/

        mAlipay_img = (ImageView) view.findViewById(R.id.alipay_pay_iv_check);
        mAlipay_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSelect) {

                    mAlipay_img.setSelected(false);
                    isSelect = false;
                } else {

                    mAlipay_img.setSelected(true);
                    isSelect = true;
                    /*Intent intent = new Intent();
                    intent.setClass(getActivity(), PayActivity.class);
                    startActivity(intent);*/

                    ExternalPartner.getInstance(getActivity(), " 支付", "1234567",
                            mHandler, "0.1").doOrder();
                }
            }
        });
        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnpay:

                //String webUrl = "http://192.168.0.179:8080/tougu_order/order/signorder.jspa?orderType=6&pid=1000&canPayType=2&sellUserId=111&buyUserId=222&num=1";
                String webUrl = "http://210.51.190.27:8582/order/signorder.jspa?orderType=6&pid=1&canPayType=6&buyUserId=" + PrefHelper.get().getString("userId", "");
                Intent intent = new Intent(getActivity(), WebViewTest.class);
                intent.putExtra("webUrl", webUrl);
                startActivity(intent);


                //暂加确认支付，测试用
                //confirmReservation();
                break;

            default:
                break;
        }

    }

    private void confirmReservation() {

        String url = "http://210.51.190.27:8082/confirmReservation.jspa";
        OkHttpUtils
                .post()
                .url(url)
                .addParams("userId", PrefHelper.get().getString("userId", ""))
                .addParams("reservationId", reservationId + "")
                .build()
                .execute(new SignOrder());

    }

    private void gotoPay() {

        String url = "http://210.51.190.27:8582/order/signorder.jspa";
        OkHttpUtils
                .post()
                .url(url)
                .addParams("orderType", 6 + "")
                .addParams("pid", 1 + "")
                .addParams("canPayType", 6 + "")
                .addParams("buyUserId", PrefHelper.get().getString("userId", ""))
                .addParams("num", 1 + "")
                .build()
                .execute(new SignOrder());
    }
}
