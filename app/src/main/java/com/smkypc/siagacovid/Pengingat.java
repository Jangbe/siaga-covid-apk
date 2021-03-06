package com.smkypc.siagacovid;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class Pengingat extends BroadcastReceiver {
    MediaPlayer player;
    String channelNotif = "channelKu";
    String channelId = "default";

    @Override
    public void onReceive(Context context, Intent intent) {
        player = MediaPlayer.create(context, R.raw.alarm);
        player.setVolume(0, (float) .5);
        player.start();

        Bundle b = new Bundle();

        int requestCode = intent.getIntExtra("requestCode", 0);
        String message;
        if(Integer.compare(requestCode, 0)==0){
            message = "Waktunya bangun tidur, jangan lupa basuh muka!";
            b.putString("index","bangun-tidur");
        }else if(Integer.compare(requestCode, 1)==0){
            message = "Waktunya tidur, jangan lupa basuh muka dan gosok gigi!";
            b.putString("index","tidur");
        }else if(Integer.compare(requestCode, 2)==0){
            message = "Waktunya sarapan pagi, jangan lupa cuci tangan sebelum makan!";
            b.putString("index","sarapan-pagi");
        }else if(Integer.compare(requestCode, 3)==0 || Integer.compare(requestCode, 5)==0||Integer.compare(requestCode, 6)==0){
            message = "Jangan lupa bawa masker dan hand sanitizer ketika keluar rumah";
            b.putString("index","aktifitas-luar");
        }else if(Integer.compare(requestCode, 4)==0){
            message = "Jangan lupa cuci tangan setelah aktifitas diluar rumah";
            b.putString("index","aktifitas-dalam");
        }else{
            message = "Berjemur matahari 10-15 menit antara jam 10.00-13.00";
            b.putString("index","jam-10");
        }
        Intent i = new Intent(context, MainActivity.class);
        i.putExtras(b);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
        setNotif(message, context);
    }

    private void setNotif(String message, Context context){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Pengingat")
                .setContentText(message);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(channelNotif, "contoh channel", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            mBuilder.setChannelId(channelNotif);
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        assert mNotificationManager != null;
        mNotificationManager.notify((int) System.currentTimeMillis(),mBuilder.build());
    }
}