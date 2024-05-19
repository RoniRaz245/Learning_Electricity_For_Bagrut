package com.example.learningelectricityforbagrut;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

public class QuestionViewActivity extends baseActivity  /*implements TextToSpeech.OnInitListener*/ {
    TextView questionBody;
    RadioGroup answers;
    RadioButton firstAnswer, secondAnswer, thirdAnswer, fourthAnswer;
    ImageView imageView;
    BottomNavigationView questionNav;
    Chronometer[] chronometers;
    /*private TextToSpeech tts;
    private Button ttsButton;*/
    //TTS not in use till further notice
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_view);
        questionBody=findViewById(R.id.questionBody);
        answers=findViewById(R.id.answers);
        firstAnswer=findViewById(R.id.firstAnswer);
        secondAnswer=findViewById(R.id.secondAnswer);
        thirdAnswer=findViewById(R.id.thirdAnswer);
        fourthAnswer=findViewById(R.id.fourthAnswer);
        imageView=findViewById(R.id.imageView);
        questionNav=findViewById(R.id.bottom_navigation);

        Intent testIntent = getIntent();
        Test currTest=testIntent.getSerializableExtra("test", Test.class);
        assert currTest != null;
        int questionAmount=currTest.getQuestions().length;
        int[] currAnswers= new int[questionAmount]; //to save what answers user gives
        int currQuestion=0;

        chronometers= new Chronometer[questionAmount];
        for(int i=0;i<questionAmount;i++){
            chronometers[i]=new Chronometer(getApplicationContext());
        }

        questionNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id=menuItem.getItemId();
                if(id==R.id.next){

                }
                return true;
            }
        });


        /* ttsButton=findViewById(R.id.ttsButton);
        tts= new TextToSpeech(this,this);;

        ttsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textToSpeak(); //calls function that reads out text
            }
        });*/
    }
    private void nextQuestion(int currQuestion, int nextQuestion, int[] currAnswers, Test test){
        chronometers[currQuestion].stop();
        int currAnswerID=answers.getCheckedRadioButtonId();
        int currAnswer;
        switch(currAnswerID){
            case R.id.firstAnswer:
                currAnswer=1;
                break;

        }
        setUpQuestion(nextQuestion, test);
    }

    private void setUpQuestion(int questionNum, Test test){
        Question question=test.getQuestions()[questionNum];
        questionBody.setText(question.getQuestionBody());
        firstAnswer.setText(question.getAnswers()[0]);
        firstAnswer.setText(question.getAnswers()[1]);
        firstAnswer.setText(question.getAnswers()[2]);
        firstAnswer.setText(question.getAnswers()[3]);
        String imageURL=question.getImageUrl();
        if(!Objects.equals(imageURL, "0")){
            StorageReference path=FirebaseStorage.getInstance().getReference().child("Images").child(imageURL);
            path.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    imageView.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });
        }
        chronometers[questionNum].start();
    }
    /* private void textToSpeak(){
        text=textView.getText().toString();
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            Locale Hebrew= new Locale("he");
            int result= tts.setLanguage(Hebrew);

            if (result == TextToSpeech.LANG_MISSING_DATA ) {
                Log.e("error", "download info");
            }
            else if(result == TextToSpeech.LANG_NOT_SUPPORTED) {
                //if tts is initialized but hebrew isn't supported
                Log.e("error", "This Language is not supported");
            }
        }
        else {
            Log.e("error", "Failed to Initialize");
        }
    }*/
}