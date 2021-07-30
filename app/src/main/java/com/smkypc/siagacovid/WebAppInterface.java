package com.smkypc.siagacovid;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;


import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;

import java.sql.Blob;
import java.util.Calendar;

public class WebAppInterface {
    Context mContext;
    MediaPlayer clicked;

    /** Instantiate the interface and set the context */
    WebAppInterface(Context c) {
        mContext = c;
    }

    /** Show a toast from the web page */
    @JavascriptInterface
    public void saveData(String params) {
        View rootView = ((Activity)mContext).getWindow().getDecorView().findViewById(android.R.id.content);
        Snackbar.make(rootView, "Alarm telah diperbarui.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        Preferences.setPrefs("reminder",params, mContext);
        try{
            String result = getData();
            JSONArray array = new JSONArray(result);
            for (int i=0;i<array.length();i++){
                String[] info = array.getString(i).split(":");
                addNotif(Integer.parseInt(info[0]),Integer.parseInt(info[1]), i);
            }
        }catch (Throwable t){
            Log.e("Error::", t.getMessage());
        }
    }

    @JavascriptInterface
    public String getData(){
        return Preferences.getPrefs("reminder", mContext);
    }

    @JavascriptInterface
    public void addNotif(int jam, int menit, int requestCode){
        Calendar cal = Calendar.getInstance();

        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.HOUR_OF_DAY, jam);
        cal.set(Calendar.MINUTE, menit);
        cal.set(Calendar.SECOND, 0);

        if (Calendar.getInstance().after(cal)) {
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }

        Intent intent = new Intent(mContext.getApplicationContext(), Pengingat.class);
        intent.putExtra("requestCode", requestCode);
        PendingIntent pintent = PendingIntent.getBroadcast(mContext.getApplicationContext(), requestCode, intent, 0);

        AlarmManager alarm = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pintent);
    }

    @JavascriptInterface
    public void click(){
        clicked = MediaPlayer.create(mContext, R.raw.click);
        clicked.start();
    }

    @JavascriptInterface
    public void game(){
        Intent game = new Intent(mContext, GameActivity.class);
        game.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mContext.startActivity(game);
    }

}