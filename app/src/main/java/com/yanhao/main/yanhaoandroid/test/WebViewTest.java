package com.yanhao.main.yanhaoandroid.test;

import android.content.Context;
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

import com.yanhao.main.yanhaoandroid.R;

/**
 * Created by Administrator on 2016/1/20 0020.
 */
public class WebViewTest extends AppCompatActivity {

    private WebView mWebView;
    private String webUrl;
    private TextView titletv;
    private ImageView mBackImg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.webview_test);

        webUrl = getIntent().getStringExtra("webUrl");

        mBackImg = (ImageView) findViewById(R.id.iv_section_title_back);
        mBackImg.setOnClickListener(new MyListener());
        titletv = (TextView) findViewById(R.id.tv_section_title_title);

        mWebView = (WebView) findViewById(R.id.webView_test);
        mWebView.loadUrl(webUrl);
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
}
