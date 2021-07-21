package com.smkypc.siagacovid;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    WebView myWebView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myWebView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.setWebChromeClient(new WebChromeClient());
        myWebView.setWebViewClient(new WebViewClient());

        webSettings.setMediaPlaybackRequiresUserGesture(false);
        webSettings.setSupportZoom(false);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDefaultTextEncodingName("utf-8");
        myWebView.addJavascriptInterface(new WebAppInterface(this), "App");

        Intent current = getIntent();
        if(current!=null && current.getStringExtra("index")!=null){
            myWebView.loadUrl("file:///android_asset/views/protokol-isoman.html");
            Toast.makeText(this, "Dari halaman lain", Toast.LENGTH_LONG).show();
        }else{
            myWebView.loadUrl("file:///android_asset/views/index.html");
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()) {
            //if Back key pressed and webview can navigate to previous page
            myWebView.goBack();
            // go back to previous page
            return true;
        }
//        else
//        {
//            finish();
//            // finish the activity
//        }
        return super.onKeyDown(keyCode, event);
    }
}