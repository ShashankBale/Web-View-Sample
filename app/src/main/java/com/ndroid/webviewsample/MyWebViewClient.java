package com.ndroid.webviewsample;

import android.graphics.Bitmap;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebViewClient extends WebViewClient {
    private final MainActivity mainActivity;

    public MyWebViewClient(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        mainActivity.updateCurrentUrl(url);
        mainActivity.showProgress(true);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        mainActivity.showProgress(false);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return super.shouldOverrideUrlLoading(view, url);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return super.shouldOverrideUrlLoading(view, request);
    }
}