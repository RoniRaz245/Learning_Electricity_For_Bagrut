package com.example.learningelectricityforbagrut;
import static com.example.learningelectricityforbagrut.SetReminderActivity.channelid;
import static com.example.learningelectricityforbagrut.SetReminderActivity.currentid;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class makeNotification extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction()!=null){
            if (intent.getAction().equals("com.example.learningelectricityforbagrut.MY_NOTIFICATION")) {
                Notification notif = new NotificationCompat.Builder(context.getApplicationContext(), channelid)
                        .setSmallIcon(R.drawable.electricity_icon)
                        .setContentTitle(context.getString(R.string.app_name_hebrew))
                        .setContentText(intent.getStringExtra("text"))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setChannelId(channelid)
                        .build();
                NotificationManager manager = context.getSystemService(NotificationManager.class);
                manager.notify(currentid, notif);
            }
        }
    }
}
