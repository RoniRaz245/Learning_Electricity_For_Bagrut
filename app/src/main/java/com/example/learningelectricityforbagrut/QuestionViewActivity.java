package com.example.learningelectricityforbagrut;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

public class QuestionViewActivity extends baseActivity  implements endQuizFragment.endQuizListener/*implements TextToSpeech.OnInitListener*/ {
    TextView questionBody;
    RadioGroup answers;
    RadioButton firstAnswer, secondAnswer, thirdAnswer, fourthAnswer;
    ImageView imageView;
    BottomNavigationView questionNav;
    Chronometer chronoView;
    boolean chronoIsRunning;
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
        chronoView=findViewById(R.id.chronometer);
        questionNav=findViewById(R.id.bottom_navigation);

        Intent testIntent = getIntent();
        Test currTest=testIntent.getSerializableExtra("test", Test.class);
        assert currTest != null;
        int questionAmount=currTest.getQuestions().length;
        int[] currAnswers= currTest.getAnswerGiven();
        final int[] currQuestion = {0};

        chronoIsRunning=true;
        setUpQuestion(currQuestion, currTest, currAnswers);

        questionNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id=menuItem.getItemId();
                if(id==R.id.next) {
                    if(currQuestion[0] +1<questionAmount) {
                        saveQuestionState(currQuestion, currAnswers, currTest);
                        currQuestion[0] += 1;
                        setUpQuestion(currQuestion, currTest, currAnswers);
                    }
                    else
                        askIfQuizEnd(); //check if user wants to end quiz
                }
                else if(id==R.id.back) {
                    if (currQuestion[0] > 0) {//if this isn't the first question
                        saveQuestionState(currQuestion, currAnswers, currTest);
                        currQuestion[0] -= 1;
                        setUpQuestion(currQuestion, currTest, currAnswers);
                    }
                    else
                        Toast.makeText(getApplicationContext(), "זוהי השאלה הראשונה!", Toast.LENGTH_LONG).show();
                }
                else if(id==R.id.pause) {
                    if(chronoIsRunning) {
                        chronoView.stop();
                        menuItem.setTitle("המשך טיימר");
                        chronoIsRunning = false;
                        int[] timers= currTest.getTimers();
                        timers[currQuestion[0]]=getTimeFromChronometer();
                        currTest.setTimers(timers);
                    }
                    else{
                        chronoView.setBase(SystemClock.elapsedRealtime() - currTest.getTimers()[currQuestion[0]]* 1000L);
                        chronoIsRunning=true;
                        chronoView.start();
                        }

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
    private void saveQuestionState(int[] currQuestion, int[] currAnswers, Test test){
        //save state of timer
        chronoView.stop();
        int currTime=getTimeFromChronometer();
        int[] timers=test.getTimers();
        timers[currQuestion[0]]=currTime;
        test.setTimers(timers);
        //save currently checked answer
        int currAnswerID=answers.getCheckedRadioButtonId();
        int currAnswer;
        if (currAnswerID == R.id.firstAnswer)
            currAnswer = 1;
        else if(currAnswerID==R.id.secondAnswer)
            currAnswer=2;
        else if (currAnswerID == R.id.thirdAnswer)
            currAnswer = 3;
        else if(currAnswerID==R.id.fourthAnswer)
            currAnswer=4;
        else
            currAnswer=-1;
        answers.clearCheck();
        currAnswers[currQuestion[0]]=currAnswer;
    }

    private void setUpQuestion(int[] questionNum, Test test, int[] currAnswers){
        Question question=test.getQuestions()[questionNum[0]];
        questionBody.setText(question.getQuestionBody());
        firstAnswer.setText(question.getAnswers().get(0));
        secondAnswer.setText(question.getAnswers().get(1));
        thirdAnswer.setText(question.getAnswers().get(2));
        fourthAnswer.setText(question.getAnswers().get(3));

        switch (currAnswers[questionNum[0]]){
            case(0): //means this question hasn't gotten an answer yet
                break;
            case(1):
                firstAnswer.setChecked(true);
                break;
            case(2):
                secondAnswer.setChecked(true);
                break;
            case(3):
                thirdAnswer.setChecked(true);
                break;
            case(4):
                fourthAnswer.setChecked(true);
                break;
        }
        //start fitting timer
        chronoView.setBase(SystemClock.elapsedRealtime() - test.getTimers()[questionNum[0]]* 1000L);
        chronoView.start();
        chronoIsRunning=true;

        String imageURL=question.getImageUrl();
        if(!Objects.equals(imageURL, "0")){
            StorageReference path=FirebaseStorage.getInstance().getReference().child("Images").child(imageURL);
            path.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    imageView.setVisibility(View.VISIBLE);
                    imageView.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(getApplicationContext(), "הייתה שגיאה בהעלאת התמונה, אנא עבור שאלה וחזור מאוחר יותר", Toast.LENGTH_LONG).show();
                }
            });
        }
        else{
            imageView.setVisibility(View.INVISIBLE);
        }
    }
    int getTimeFromChronometer(){
        int timeOnChronometer = 0;
        String chronoText = chronoView.getText().toString();
        String[] array = chronoText.split(":");
        if (array.length == 2) {
            timeOnChronometer = Integer.parseInt(array[0]) * 60
                    + Integer.parseInt(array[1]);
        } else if (array.length == 3) {
            timeOnChronometer = Integer.parseInt(array[0]) * 60 * 60
                    + Integer.parseInt(array[1]) * 60
                    + Integer.parseInt(array[2]) ;
        }
        return timeOnChronometer;
    }
    public void askIfQuizEnd(){
        DialogFragment dialog=new endQuizFragment();
        dialog.show(getSupportFragmentManager(), "questionAmountFragment");
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    public void endQuiz() {
        Intent testIntent = getIntent();
        Test test=testIntent.getSerializableExtra("test", Test.class);
        assert test != null;
        int questionAmount=test.getQuestions().length;
        int[] answers= test.getAnswerGiven(); //to save what answers user gives
        final int[] currQuestion = {questionAmount-1};
        saveQuestionState(currQuestion,answers,test);

        //save finished test to database
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("tests").add(test).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Intent stats=new Intent(getApplicationContext(), quizzStatsActivity.class);
                        getApplicationContext().startActivity(stats);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(QuestionViewActivity.this, "הייתה שגיאה, אנא נסו שוב", Toast.LENGTH_LONG).show();
                    }
                });
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