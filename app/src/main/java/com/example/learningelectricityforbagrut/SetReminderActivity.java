package com.example.learningelectricityforbagrut;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.os.Bundle;
import android.app.Notification;
import android.widget.Toast;
import android.content.Context;
import androidx.core.app.NotificationManagerCompat;
import java.util.Calendar;

public class SetReminderActivity extends AppCompatActivity {
    TimePicker timePicker;
    EditText textForNotif;
    Button makeNotif;
    ImageButton homeButton;
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
        homeButton = findViewById(R.id.goHome);
        homeButton.setOnClickListener(v -> homeButton.getContext().startActivity(new Intent(homeButton.getContext(), HomeActivity.class)));
        makeNotif.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ScheduleExactAlarm")
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                createNotificationChannel();
                int currentid=id;
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                Intent intent = new Intent(getApplicationContext(), makeNotification.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                id++;
                Toast.makeText(getApplicationContext(), "Scheduled ", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= 26) {
            channelid = "channelID";
            String name = "Alerts";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelid, name, importance);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            NotificationManagerCompat.from(this).createNotificationChannel(channel);
        }
    }
}