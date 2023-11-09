package com.example.learningelectricityforbagrut;
import android.app.Notification;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class makeNotif extends SetReminderActivity{
    Notification notif = new NotificationCompat.Builder(this, channelid)
            .setSmallIcon(R.drawable.electricity_icon)
            .setContentTitle(getResources().getString(R.string.app_name_hebrew))
            .setContentText(textForNotif.getText().toString())
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build();
    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
    notificationManager.notify(currentid, notif);
}
