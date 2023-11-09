package com.example.learningelectricityforbagrut;

import static android.view.View.*;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.os.Bundle;
import androidx.core.app.NotificationCompat;
import android.app.Notification;
import android.widget.Toast;

import java.util.Calendar;

public class SetReminderActivity extends AppCompatActivity {
    TimePicker timePicker;
    EditText textForNotif;
    Button makeNotif;
    ImageButton homeButton;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_reminder);
        timePicker=findViewById(R.id.timePicker);
        textForNotif=findViewById(R.id.textForNotif);
        makeNotif=findViewById(R.id.makeNotif);
        homeButton=findViewById(R.id.goHome);
        homeButton.setOnClickListener(v -> homeButton.getContext().startActivity(new Intent(homeButton.getContext(), HomeActivity.class)));
        makeNotif.setOnClickListener(v -> createNotif());
    }
    @SuppressLint("ScheduleExactAlarm")
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotif(){
        String CHANNEL_ID="MYCHANNEL";
        NotificationChannel notificationChannel=new NotificationChannel(CHANNEL_ID,"name",NotificationManager.IMPORTANCE_LOW);
        Notification notification=new Notification.Builder(getApplicationContext(),CHANNEL_ID)
                .setContentText(textForNotif.getText())
                .setContentTitle(getResources().getString(R.string.app_name_hebrew))
                .setChannelId(CHANNEL_ID)
                .setSmallIcon(R.drawable.electricity_icon)
                .build();

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), Notification.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
        calendar.set(Calendar.MINUTE, timePicker.getMinute());
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        Toast.makeText(getApplicationContext(), "התראה נקבעה", Toast.LENGTH_LONG).show();
    }

}