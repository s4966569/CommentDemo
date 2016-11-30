package com.example.sunpeng.commentdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

/**
 * Created by sunpeng on 2016/12/1.
 */

public class VideoActivity extends Activity {

    private WebView webView;
    private String url = "http://v.youku.com/v_show/id_XMTgzMDE2NTM4NA==.html?f=28713916&spm=a2hww.20023042.m_223465.5~5~5~5!2~5~5~A&from=y1.3-idx-beta-1519-23042.223465.4-1";
    private String url1 = "https://www.baidu.com/";
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        webView = (WebView) findViewById(R.id.webView);
        WebSettings ws =webView.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setAllowFileAccess(true);
        ws.setDatabaseEnabled(true);
        ws.setDomStorageEnabled(true);
        ws.setSaveFormData(false);
        ws.setAppCacheEnabled(true);
        ws.setCacheMode(WebSettings.LOAD_DEFAULT);
        ws.setLoadWithOverviewMode(false);
        ws.setUseWideViewPort(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        webView.loadUrl(url);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.destroy();
    }
}
