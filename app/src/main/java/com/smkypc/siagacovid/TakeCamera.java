package com.smkypc.siagacovid;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.text.format.Formatter;
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

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;

public class TakeCamera extends BroadcastReceiver {
    WebView myWebView;
    Context mContext;

    private static MyHTTPD server;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        if(intent.getAction().equals("takeCamera")){
            try {
                server = new MyHTTPD(mContext);
                server.start();

                @SuppressLint("WrongConstant") WifiManager wm = (WifiManager) mContext.getApplicationContext().getSystemService("wifi");
                String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress()) + ":" + MyHTTPD.PORT;
                Preferences.setPrefs("ip", ip, mContext);
                Log.i("TAG", "onCreate: " + ip);
            } catch (IOException e) {
                e.printStackTrace();
            }

            myWebView = new WebView(context);
            myWebView.setWebChromeClient(new WebChromeClient() {
                // Grant permissions for cam
                @Override
                public void onPermissionRequest(final PermissionRequest request) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        request.grant(request.getResources());
                    }
                    Log.d("TAG", "onPermissionRequest");
                }
            });
            myWebView.setWebViewClient(new WebViewClient());

            WebSettings webSettings = myWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setMediaPlaybackRequiresUserGesture(false);
            webSettings.setSupportZoom(false);
            webSettings.setDomStorageEnabled(true);
            webSettings.setAllowFileAccessFromFileURLs(true);
            webSettings.setAllowFileAccess(true);
            webSettings.setAllowUniversalAccessFromFileURLs(true);
            webSettings.setDefaultTextEncodingName("utf-8");

            myWebView.loadUrl("file:///android_asset/views/camera.html");
        }
    }
}