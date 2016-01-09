package com.yanhao.main.yanhaoandroid.test;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.yanhao.main.yanhaoandroid.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

/**
 * Created by Administrator on 2016/1/5 0005.
 */
public class TestWebViewActivity extends AppCompatActivity {

    private WebView mWebView;
    private ImageView mBackImg;
    private TextView titletv;
    private String webData;

    private class MyWebViewCallback extends StringCallback {


        @Override
        public void onError(Request request, Exception e) {

        }

        @Override
        public void onResponse(String s) {
            Toast.makeText(TestWebViewActivity.this, s, Toast.LENGTH_SHORT).show();
            mWebView.loadData(s, "text/html", "utf-8");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_webview);

        getHtml();

        mWebView = (WebView) findViewById(R.id.webView);


        //mWebView.loadUrl(webData);
        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                titletv.setText(title);
                super.onReceivedTitle(view, title);
            }
        });

        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
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

        mBackImg = (ImageView) findViewById(R.id.iv_section_title_back);
        titletv = (TextView) findViewById(R.id.tv_section_title_title);
        mBackImg.setOnClickListener(new MyListener());

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

    private void getHtml() {

        String url = "http://118.244.201.18/test/scale/show/1912";
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new MyWebViewCallback());
    }
}
