package com.example.learningelectricityforbagrut;


import androidx.annotation.RequiresApi;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.os.Bundle;
import android.widget.Toast;
import java.util.Calendar;

public class SetReminderActivity extends baseActivity {
    TimePicker timePicker;
    EditText textForNotif;
    Button makeNotif;
    int id=0;
    static public int currentid;
    public static String channelid;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_reminder);
        timePicker = findViewById(R.id.timePicker);
        textForNotif = findViewById(R.id.textForNotif);
        makeNotif = findViewById(R.id.makeNotif);
        makeNotif.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ScheduleExactAlarm")
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH),
                        timePicker.getHour(), timePicker.getMinute());

                createNotificationChannel();

                //puts relevant notification into pendingIntent to be handled by alarm manager
                int currentid=id;
                String textGiven=textForNotif.getText().toString();
                Intent intent = new Intent(makeNotif.getContext(), makeNotification.class);
                intent.putExtra("text",textGiven);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(makeNotif.getContext(), currentid, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

                long time=calendar.getTimeInMillis();
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time, pendingIntent);

                id++;
                //letting user know scheduling was successful
                Toast.makeText(getApplicationContext(), "ההתראה נקבעה", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= 26) {
            channelid = "channelID";
            CharSequence name = "Alerts";
            String description= "channel for studying reminders";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelid, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}