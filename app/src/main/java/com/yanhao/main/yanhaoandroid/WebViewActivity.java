package com.yanhao.main.yanhaoandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.yanhao.main.yanhaoandroid.banner.T;
import com.yanhao.main.yanhaoandroid.share.ShareActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;


public class WebViewActivity extends AppCompatActivity implements View.OnClickListener {

    private WebView mWebView;
    private ImageView mBackImg;
    private TextView titletv;
    private String webUrl, imageUrl, title, shareTitle, shareDesp, shareImageUrl, shareWebUrl;
    private ImageView mBack_img, mShare_img, mLike_img, mStar_img;

    private class addFavorCallback extends StringCallback {

        @Override
        public void onError(Request request, Exception e) {

        }

        @Override
        public void onResponse(String s) {

            try {
                JSONObject jsonObject = new JSONObject(s);
                Toast.makeText(WebViewActivity.this, jsonObject.getString("info"), Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        mBack_img = (ImageView) findViewById(R.id.back_img);
        mBack_img.setOnClickListener(this);
        mShare_img = (ImageView) findViewById(R.id.share_img);
        mShare_img.setOnClickListener(this);
        mLike_img = (ImageView) findViewById(R.id.like_img);
        mLike_img.setOnClickListener(this);
        mStar_img = (ImageView) findViewById(R.id.star_img);
        mStar_img.setOnClickListener(this);

        webUrl = getIntent().getStringExtra("webUrl");
        imageUrl = getIntent().getStringExtra("imageUrl");
        title = getIntent().getStringExtra("title");
        shareTitle = getIntent().getStringExtra("shareTitle");
        shareDesp = getIntent().getStringExtra("shareDesp");
        shareImageUrl = getIntent().getStringExtra("shareImageUrl");
        shareWebUrl = getIntent().getStringExtra("shareWebUrl");

        mWebView = (WebView) findViewById(R.id.webView);
        //mWebView.loadData(html, "text/html", "UTF-8");

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

    class MyDownloadListener implements DownloadListener {

        @Override
        public void onDownloadStart(String s, String s1, String s2, String s3, long l) {

        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.back_img:

                finish();
                break;
            case R.id.share_img:

                onShareRead(shareTitle, shareDesp, shareImageUrl, shareWebUrl);
                /*Intent intent = new Intent();
                intent.putExtra("shareTitle", shareTitle);
                intent.putExtra("shareDesp", shareDesp);
                intent.putExtra("shareImageUrl", shareImageUrl);
                intent.putExtra("shareWebUrl", shareWebUrl);
                intent.setClass(WebViewActivity.this, ShareActivity.class);
                startActivity(intent);*/
                break;
            case R.id.like_img:

                Toast.makeText(WebViewActivity.this, "已经点赞！", Toast.LENGTH_LONG).show();
                break;
            case R.id.star_img:
                addFavorContent();
                break;
            default:
                break;
        }
    }

    public void addFavorContent() {

        String url = "http://210.51.190.27:8082/addFavorContent.jspa";
        OkHttpUtils
                .post()
                .url(url)
                .addParams("userId", "WbqhSt2gqUU=")
                .addParams("type", "1")
                .addParams("imageUrl", imageUrl)
                .addParams("contentUrl", webUrl)
                .addParams("title", title)
                .build()
                .execute(new addFavorCallback());
    }

    private void onShareRead(String shareTitle, String shareDesp, String shareImageUrl, String shareWebUrl) {

        Intent intent = new Intent();
        intent.putExtra("shareTitle", shareTitle);
        intent.putExtra("shareDesp", shareDesp);
        intent.putExtra("shareImageUrl", shareImageUrl);
        intent.putExtra("shareWebUrl", shareWebUrl);
        intent.setClass(WebViewActivity.this, ShareActivity.class);
        startActivity(intent);
    }
}
