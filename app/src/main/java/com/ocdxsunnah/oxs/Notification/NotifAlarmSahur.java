package com.ocdxsunnah.oxs.Notification;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.ocdxsunnah.oxs.MainActivity;
import com.ocdxsunnah.oxs.R;
import com.ocdxsunnah.oxs.Receiver.Helper.AlarmSahurHelper;

public class NotifAlarmSahur extends ContextWrapper {

    public static final String channelID1 = "channel1";
    public static final String channelName = "Channel Name";
    private NotificationManager mManager;

    public NotifAlarmSahur(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel1 = new NotificationChannel(channelID1, channelName, NotificationManager.IMPORTANCE_LOW);
        getManager().createNotificationChannel(channel1);

    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    public NotificationCompat.Builder getChannelNotification() {

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent intent1 = new Intent(this, AlarmSahurHelper.class);
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(this, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);


        return new NotificationCompat.Builder(getApplicationContext(), channelID1)
                .setContentTitle("Pengingat")
                .setContentText("Bangun! Sudah Waktu Sahur!")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setColor(Color.GREEN)
                .addAction(R.drawable.ic_launcher_background, "Berhenti", pendingIntent1);
    }
}