package com.example.learningelectricityforbagrut;
import static com.example.learningelectricityforbagrut.SetReminderActivity.channelid;
import static com.example.learningelectricityforbagrut.SetReminderActivity.currentid;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class makeNotification extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Notification notif = new NotificationCompat.Builder(context.getApplicationContext(), channelid)
                .setSmallIcon(R.drawable.electricity_icon)
                .setContentTitle(context.getString(R.string.app_name_hebrew))
                .setContentText(SetReminderActivity.textForNotif.getText().toString())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setChannelId(channelid)
                .build();
        NotificationManager manager = context.getSystemService(NotificationManager.class);
        manager.notify(currentid, notif);
    }
}
