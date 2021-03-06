package com.yanhao.main.yanhaoandroid.test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.yanhao.main.yanhaoandroid.R;
import com.yanhao.main.yanhaoandroid.matchconsultant.MatchConsultantActivity;
import com.yanhao.main.yanhaoandroid.share.ShareActivity;
import com.yanhao.main.yanhaoandroid.wxpay.WXPayActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/1/20 0020.
 */
public class WebViewTest extends AppCompatActivity {

    private WebView mWebView;
    private String webUrl, titleName;
    private TextView titletv;
    private ImageView mBackImg;
    private LinearLayout mBack;
    private int tag;

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.webview_test);

        webUrl = getIntent().getStringExtra("webUrl");
        titleName = getIntent().getStringExtra("titleName");
        tag = getIntent().getIntExtra("tag", 0);

        mBack = (LinearLayout) findViewById(R.id.ll_section_title_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mBackImg = (ImageView) findViewById(R.id.iv_section_title_back);
        mBackImg.setOnClickListener(new MyListener());
        titletv = (TextView) findViewById(R.id.tv_section_title_title);
        titletv.setText(titleName);

        mWebView = (WebView) findViewById(R.id.webView_test);
        mWebView.setVerticalScrollbarOverlay(true);
        if (tag == 1) {

            mWebView.loadUrl(" file:///android_asset/userrule.html ");
        } else {

            mWebView.loadUrl(webUrl);
        }
        //mWebView.loadUrl(webUrl);
        //在js中调用本地java方法
        mWebView.addJavascriptInterface(new JsInterface(this), "AndroidWebView");

        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                //titletv.setText(title);
                super.onReceivedTitle(view, title);
            }
        });

        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setSupportZoom(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setBuiltInZoomControls(true);//support zoom
        //webSettings.setPluginsEnabled(true);//support flash
        webSettings.setUseWideViewPort(true);// 这个很关键
        webSettings.setLoadWithOverviewMode(true);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int mDensity = metrics.densityDpi;
        if (mDensity == 240) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else if (mDensity == 160) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        } else if (mDensity == 120) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
        } else if (mDensity == DisplayMetrics.DENSITY_XHIGH) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else if (mDensity == DisplayMetrics.DENSITY_TV) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        }
    }

    class MyListener implements View.OnClickListener {


        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.iv_section_title_back:
                    finish();
                    break;
                default:
                    break;
            }
        }
    }

    private class JsInterface {
        private Context mContext;

        public JsInterface(Context context) {
            this.mContext = context;
        }

        //在js中调用window.AndroidWebView.showInfoFromJs(name)，便会触发此方法。
        @JavascriptInterface
        public void showInfoFromJs(String name, String desp, String imageUrl, String webUrl) {
            //Toast.makeText(mContext, name + desp + imageUrl + webUrl, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.putExtra("shareTitle", name);
            intent.putExtra("shareDesp", desp);
            intent.putExtra("shareImageUrl", imageUrl);
            intent.putExtra("shareWebUrl", webUrl);
            intent.setClass(WebViewTest.this, ShareActivity.class);
            startActivity(intent);
        }

        @JavascriptInterface
        public void showPay(String orderId, String orderType) {
            //Toast.makeText(mContext, "name = " + orderId + "  " + "desp = " + orderType, Toast.LENGTH_SHORT).show();
            Log.i("showpay", orderId + "");

            Intent intent = new Intent();
            intent.putExtra("shareTitle", orderId);
            intent.putExtra("shareDesp", orderType);
            intent.setClass(WebViewTest.this, WXPayActivity.class);
            startActivity(intent);
        }

        @JavascriptInterface
        public void fun1FromJs(String action) {
            Toast.makeText(mContext, "name = " + action, Toast.LENGTH_SHORT).show();
            Log.i("showpay", action + "");

            Intent intent = new Intent();
            intent.putExtra("titlename", "婆媳关系");
            intent.putExtra("item", "婚姻关系");
            intent.setClass(WebViewTest.this, MatchConsultantActivity.class);
            startActivity(intent);
        }
    }
}
