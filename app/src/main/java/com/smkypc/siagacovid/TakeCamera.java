package com.smkypc.siagacovid;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.util.Base64;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;

public class TakeCamera extends BroadcastReceiver {
    WebView myWebView;
    Context mContext;

    private static final String INJECTION_TOKEN = "**injection**";

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        if(intent.getAction().equals("takeCamera")){
            Intent i = new Intent(mContext, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(i);

//            myWebView = new WebView(context);
//            myWebView.setWebChromeClient(new WebChromeClient() {
//                // Grant permissions for cam
//                @Override
//                public void onPermissionRequest(final PermissionRequest request) {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        request.grant(request.getResources());
//                    }
//                }
//            });
//            myWebView.setWebViewClient(new WebViewClient());
//
//            WebSettings webSettings = myWebView.getSettings();
//            webSettings.setJavaScriptEnabled(true);
//            webSettings.setMediaPlaybackRequiresUserGesture(false);
//            webSettings.setSupportZoom(false);
//            webSettings.setDomStorageEnabled(true);
//            webSettings.setDefaultTextEncodingName("utf-8");
//
//            Log.i("IP", Preferences.getPrefs("ip", mContext.getApplicationContext()));
//            String ip = Preferences.getPrefs("ip", mContext.getApplicationContext());
//            myWebView.loadUrl(ip+"/views/camera.html");
        }
    }
}