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

import androidx.core.app.NotificationCompat;

public class Pengingat extends BroadcastReceiver {
    MediaPlayer player;
    String channelNotif = "channelKu";
    String channelId = "default";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        player = MediaPlayer.create(context, R.raw.alarm);
        player.start();

        Bundle b = new Bundle();

        //Menyisipkan tipe data String ke dalam obyek bundle
        b.putString("index", "cuci-tangan");

        Intent i = new Intent(context, MainActivity.class);
        i.putExtras(b);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Ini adalah title")
                .setContentText("Ini adalah message");
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