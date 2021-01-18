package com.ocdxsunnah.oxs.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.ocdxsunnah.oxs.Notification.NotifAlarmSahur;

public class AlarmSahurReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        NotifAlarmSahur notif = new NotifAlarmSahur(context);
        NotificationCompat.Builder nb = notif.getChannelNotification();
        notif.getManager().notify(0, nb.build());

    }
}
