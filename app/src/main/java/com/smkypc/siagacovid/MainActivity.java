package com.smkypc.siagacovid;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {
    WebView myWebView;
    MediaPlayer mediaPlayer;
    FloatingActionButton fab, info;
    private final String TAG = "TAG";

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myWebView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.setWebChromeClient(new WebChromeClient() {
            // Grant permissions for cam
            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    request.grant(request.getResources());
                }
                Log.d(TAG, "onPermissionRequest");
            }
        });

        myWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                String ip = Preferences.getPrefs("ip", MainActivity.this);
                Log.i("IP", ip);
                myWebView.loadUrl("javascript:initUrl("+ip+")");
            }
        });

        webSettings.setMediaPlaybackRequiresUserGesture(false);
        webSettings.setSupportZoom(false);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);

        Intent current = getIntent();
        if(current!=null && current.getStringExtra("index")!=null){
            String index = current.getStringExtra("index");
            myWebView.loadUrl("file:///android_asset/views/kegiatan-harian.html?index="+index);
        }else{
            myWebView.loadUrl("file:///android_asset/views/index.html");
        }

        myWebView.addJavascriptInterface(new WebAppInterface(this), "App");

        initBacksound();
        initFab();
    }

    private void initBacksound() {
        mediaPlayer = MediaPlayer.create(this, R.raw.backsound);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        mediaPlayer.setVolume(0, (float) .05);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()) {
            info.setVisibility(View.VISIBLE);
            myWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        new SweetAlertDialog(MainActivity.this, SweetAlertDialog.NORMAL_TYPE)
            .setTitleText("Apakah kamu yakin?")
            .setContentText("Kamu akan keluar aplikasi?")
            .setConfirmText("Ya")
            .setConfirmButtonBackgroundColor(R.color.red_btn_bg_color)
            .setCancelButtonBackgroundColor(R.color.main_blue_color)
            .setConfirmClickListener(sDialog -> {
                sDialog.dismissWithAnimation();
                finish();
            })
            .setCancelButton("Batal", sDialog -> {
                sDialog.dismissWithAnimation();
            })
            .show();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void initFab(){
        fab = findViewById(R.id.fab);
        final Boolean[] muted = {false};
        fab.setOnClickListener(view -> {
            muted[0] = !muted[0];
            if(muted[0]){
                fab.setImageDrawable(getResources().getDrawable(R.drawable.volume_mute));
                mediaPlayer.setVolume(0,0);
            }else{
                fab.setImageDrawable(getResources().getDrawable(R.drawable.volume_high));
                mediaPlayer.setVolume(0,(float) .1);
            }
        });

        info = findViewById(R.id.fab_info);
        info.setOnClickListener(v -> {
            info.setVisibility(View.INVISIBLE);
            myWebView.loadUrl("file:///android_asset/views/info.html");
        });
    }
}