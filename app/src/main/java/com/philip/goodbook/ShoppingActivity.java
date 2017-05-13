package com.philip.goodbook;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Zhangdingying on 2017/5/13.
 */

public class ShoppingActivity extends BaseActivity {

    private WebView webView;

    private String url = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_layout);
        Intent intent = getIntent();
        url = intent.getStringExtra("urlShp");

        webView = (WebView) findViewById(R.id.webview_shp);
        webView.loadUrl(url);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(url);
                return true;
            }
        });
    }
}
