package com.smkypc.siagacovid;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    WebView myWebView;
    MediaPlayer mediaPlayer;
    FloatingActionButton fab;
    private final String TAG = "TAG";
    private static MyHTTPD server;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            server = new MyHTTPD(this);
            server.start();

            initIPAddress();
        } catch (IOException e) {
            e.printStackTrace();
        }

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

        myWebView.setWebViewClient(new WebViewClient());

        webSettings.setMediaPlaybackRequiresUserGesture(false);
        webSettings.setSupportZoom(false);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDefaultTextEncodingName("utf-8");
        myWebView.addJavascriptInterface(new WebAppInterface(this), "App");

        Intent current = getIntent();
        if(current!=null && current.getStringExtra("index")!=null){
            String index = current.getStringExtra("index");
            myWebView.loadUrl("file:///android_asset/views/kegiatan-harian.html?index="+index);
        }else{
            myWebView.loadUrl("file:///android_asset/views/index.html");
        }

        mediaPlayer = MediaPlayer.create(this, R.raw.backsound);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        mediaPlayer.setVolume(0, (float) .1);

        fab = findViewById(R.id.fab);
        final Boolean[] muted = {false};
        fab.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View view) {
                muted[0] = !muted[0];
                if(muted[0]){
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.volume_mute));
                    mediaPlayer.setVolume(0,0);
                }else{
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.volume_high));
                    mediaPlayer.setVolume(0,(float) .1);
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()) {
            myWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initIPAddress() {
        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress()) + ":" + MyHTTPD.PORT;
        Preferences.setPrefs("ip", ip, this);
        Log.i("TAG", "onCreate: " + ip);
    }
}