package com.example.learningelectricityforbagrut;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {
    private Button addReminder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        addReminder=findViewById(R.id.addReminder);
        addReminder.setOnClickListener(v-> addReminder.getContext().startActivity(new Intent(addReminder.getContext(), SetReminderActivity.class)));
    }

}