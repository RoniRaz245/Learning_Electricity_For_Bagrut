package com.example.learningelectricityforbagrut;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import android.os.Bundle;

public class MakeQuestionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makequestion);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
    }
}