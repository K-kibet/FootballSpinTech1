package com.extreme.spinsport;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.forms.sti.progresslitieigb.ProgressLoadingJIGB;


public class Live extends AppCompatActivity {

    private WebView mainWebView;
    private String link;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            link =  extras.getString("link");

        }

        if (savedInstanceState != null)
        {  ((WebView) findViewById(R.id.webview)).restoreState(savedInstanceState.getBundle("webViewState"));}
        else {
            mainWebView = (WebView) findViewById(R.id.webview);

        }



        WebSettings webSettings = mainWebView.getSettings();
        mainWebView.setInitialScale(1);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        mainWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        mainWebView.setScrollbarFadingEnabled(false);
        webSettings.setJavaScriptEnabled(true);

        ProgressLoadingJIGB.setupLoading = (setup) ->  {
            setup.srcLottieJson = R.raw.sun; // Tour Source JSON Lottie
            setup.message = "Please Wait!";//  Center Message
            setup.timer = 0;   // Time of live for progress.
            setup.width = 200; // Optional
            setup.hight = 200; // Optional
        };
        ProgressLoadingJIGB.startLoading(this);



        mainWebView.setWebChromeClient(new WebChromeClient());
        mainWebView.setWebViewClient(new MyCustomWebViewClient());
        mainWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        mainWebView.loadUrl(link);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ProgressLoadingJIGB.finishLoadingJIGB(Live.this);
            }
        },10000);
    }

    private class MyCustomWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);

            return true;


        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }


    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle bundle = new Bundle();
        mainWebView.saveState(bundle);
        outState.putBundle("webViewState", bundle);
    }
    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        Bundle bundle = new Bundle();
        mainWebView.saveState(bundle);
        state.putBundle("webViewState", bundle);
    }
}