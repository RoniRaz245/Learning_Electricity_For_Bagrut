package com.example.learningelectricityforbagrut;


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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_reminder);

        setSupportActionBar(findViewById(R.id.my_toolbar));
        getSupportActionBar().show();

        timePicker = findViewById(R.id.timePicker);
        textForNotif = findViewById(R.id.textForNotif);
        makeNotif = findViewById(R.id.makeNotif);
        makeNotif.setOnClickListener(new View.OnClickListener() {

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
                intent.setAction("com.example.learningelectricityforbagrut.MY_NOTIFICATION");
                PendingIntent pendingIntent = PendingIntent.getBroadcast(makeNotif.getContext(), currentid, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

                long time=calendar.getTimeInMillis();
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                boolean canSchedule=true;
                    if(!alarmManager.canScheduleExactAlarms()) {
                        Toast.makeText(getApplicationContext(), getString(R.string.notif_req), Toast.LENGTH_LONG).show();
                        canSchedule=false;
                    }
                if(canSchedule) {
                    id++;
                    //letting user know scheduling was successful
                    Toast.makeText(getApplicationContext(), getString(R.string.notif_success), Toast.LENGTH_LONG).show();
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time, pendingIntent);
                }
            }
        });
    }

    private void createNotificationChannel() {
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