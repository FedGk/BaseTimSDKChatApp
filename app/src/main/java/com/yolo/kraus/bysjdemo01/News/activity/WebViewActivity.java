package com.yolo.kraus.bysjdemo01.News.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.yolo.kraus.bysjdemo01.R;

public class WebViewActivity extends Activity {


    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");



        webView  = findViewById(R.id.webview);
        webView.loadUrl(url);
    }
}
