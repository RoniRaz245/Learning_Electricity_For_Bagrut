package com.example.learningelectricityforbagrut;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MakeQuestionActivity extends AppCompatActivity {
    private EditText bodyTextView, option1TextView, option2TextView, option3TextView, option4TextView;
    private ImageButton goHome;
    private NumberPicker levelPicker, correctAnswerPicker;
    private Button upload, uploadImage;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makequestion);

        goHome=findViewById(R.id.goHome);
        bodyTextView=findViewById(R.id.questionBody);
        option1TextView=findViewById(R.id.firstAnswer);
        option2TextView=findViewById(R.id.secondAnswer);
        option3TextView=findViewById(R.id.thirdAnswer);
        option4TextView=findViewById(R.id.fourthAnswer);
        levelPicker=findViewById(R.id.levelPicker);
        correctAnswerPicker=findViewById(R.id.correctAnswerPicker);
        upload=findViewById(R.id.uploadQuestion);
        uploadImage=findViewById(R.id.uploadImage);

        mDatabase= FirebaseDatabase.getInstance().getReference();

        upload.setOnClickListener(v -> uploadGivenQuestion()); //calls function that takes info from fields and uploads question based on them
        goHome.setOnClickListener(v -> this.getApplicationContext().startActivity(new Intent(this.getApplicationContext(), HomeActivity.class)));
    }
    private void uploadGivenQuestion(){
        if(levelPicker.getValue()==0) {
            Toast.makeText(this.getApplicationContext(),"בבקשה תכניס רמה!", Toast.LENGTH_LONG);
        }//TODO: add other fail cases

        String body=bodyTextView.getText().toString();
        String option1= option1TextView.getText().toString();
        String option2= option2TextView.getText().toString();
        String option3= option3TextView.getText().toString();
        String option4= option4TextView.getText().toString();
        int level=levelPicker.getValue();
        int correctAnswer=correctAnswerPicker.getValue();
        int answerIndex=correctAnswer-1;


    }
}