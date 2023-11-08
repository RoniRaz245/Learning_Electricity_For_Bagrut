package com.example.learningelectricityforbagrut;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.os.Bundle;
import androidx.core.app.NotificationCompat;
import android.app.Notification;

public class SetReminderActivity extends AppCompatActivity {
    TimePicker timePicker;
    EditText textForNotif;
    Button makeNotif;
    ImageButton homeButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_reminder);
        timePicker=findViewById(R.id.timePicker);
        textForNotif=findViewById(R.id.textForNotif);
        makeNotif=findViewById(R.id.makeNotif);
        homeButton=findViewById(R.id.goHome);
        homeButton.setOnClickListener(v -> homeButton.getContext().startActivity(new Intent(homeButton.getContext(), HomeActivity.class)));
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.electricity_icon)
                .setContentTitle(R.s)
                .setContentText(textForNotif.getText().toString())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
    }

}