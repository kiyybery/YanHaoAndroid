package com.yanhao.main.yanhaoandroid.alipay;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.alipay.pay.ExternalPartner;
import com.yanhao.main.yanhaoandroid.alipay.pay.Keys;
import com.yanhao.main.yanhaoandroid.alipay.pay.PayResult;

/**
 * Created by Administrator on 2015/12/30 0030.
 */
public class PayActivity extends AppCompatActivity {

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
                        Toast.makeText(PayActivity.this, "支付成功",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(PayActivity.this, "支付结果确认中",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(PayActivity.this, "支付失败",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                }
                case SDK_CHECK_FLAG: {
                    Toast.makeText(PayActivity.this, "检查结果为：" + msg.obj,
                            Toast.LENGTH_SHORT).show();
                    break;
                }
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("eee", "onCreate()");
        setContentView(R.layout.pay_main);
        findViewById(R.id.pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExternalPartner.getInstance(PayActivity.this, " 支付", "123456",
                        mHandler, "0.1").doOrder();
            }
        });

        findViewById(R.id.check).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExternalPartner.getInstance(PayActivity.this, "开会员", "123456",
                        mHandler, "0.1").check();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("eee", "onStart()");
    }
}
