package com.example.learningelectricityforbagrut;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class makeNotification extends SetReminderActivity {
    public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            Notification notif = new NotificationCompat.Builder(this, channelid)
                .setSmallIcon(R.drawable.electricity_icon)
                .setContentTitle(getResources().getString(R.string.app_name_hebrew))
                .setContentText(textForNotif.getText().toString())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setChannelId(channelid)
                .build();
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(currentid, notif);
    }
}
