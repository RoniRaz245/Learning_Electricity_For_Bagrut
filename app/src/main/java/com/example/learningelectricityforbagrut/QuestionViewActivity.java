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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

public class QuestionViewActivity extends baseActivity  implements endQuizFragment.endQuizListener/*implements TextToSpeech.OnInitListener*/ {
    TextView questionBody, chronometerExplanation;
    RadioGroup answers;
    RadioButton firstAnswer, secondAnswer, thirdAnswer, fourthAnswer;
    ImageView imageView;
    BottomNavigationView questionNav;
    Chronometer chronoView;
    boolean chronoIsRunning;
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
        int questionAmount=currTest.getQuestions().size();
        ArrayList<Integer> currAnswers= currTest.getAnswerGiven();
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
                        Toast.makeText(getApplicationContext(), getString(R.string.first_question), Toast.LENGTH_LONG).show();
                }
                else if(id==R.id.pause) {
                    if(chronoIsRunning) {
                        chronoView.stop();
                        menuItem.setTitle(getString(R.string.timer_cont));
                        chronoIsRunning = false;
                        ArrayList<Integer> timers= currTest.getTimers();
                        int currTime=getTimeFromChronometer();
                        timers.set(currQuestion[0], currTime);
                        currTest.setTimers(timers);
                    }
                    else{
                        chronoView.setBase(SystemClock.elapsedRealtime() - currTest.getTimers().get(currQuestion[0])* 1000L);
                        chronoIsRunning=true;
                        chronoView.start();
                        }

                }

                return true;
            }
        });
    }
    private void saveQuestionState(int[] currQuestion, ArrayList<Integer> currAnswers, Test test){
        //save state of timer
        chronoView.stop();
        int currTime=getTimeFromChronometer();
        ArrayList<Integer> timers=test.getTimers();
        timers.set(currQuestion[0],currTime);
        test.setTimers(timers);
        //save currently checked answer
        int currAnswerID=answers.getCheckedRadioButtonId();
        int currAnswer;
        if (currAnswerID == R.id.firstAnswer)
            currAnswer = 0;
        else if(currAnswerID==R.id.secondAnswer)
            currAnswer=1;
        else if (currAnswerID == R.id.thirdAnswer)
            currAnswer = 2;
        else if(currAnswerID==R.id.fourthAnswer)
            currAnswer=3;
        else
            currAnswer=-1;
        answers.clearCheck();
        currAnswers.set(currQuestion[0], currAnswer);
    }

    private void setUpQuestion(int[] questionNum, Test test, ArrayList<Integer> currAnswers){
        Question question=test.getQuestions().get(questionNum[0]);
        questionBody.setText(question.getQuestionBody());
        firstAnswer.setText(question.getAnswers().get(0));
        secondAnswer.setText(question.getAnswers().get(1));
        thirdAnswer.setText(question.getAnswers().get(2));
        fourthAnswer.setText(question.getAnswers().get(3));

        switch (currAnswers.get(questionNum[0])){
            case -1 : //means this question hasn't gotten an answer yet
                break;
            case 0 :
                firstAnswer.setChecked(true);
                break;
            case 1 :
                secondAnswer.setChecked(true);
                break;
            case 2 :
                thirdAnswer.setChecked(true);
                break;
            case 3 :
                fourthAnswer.setChecked(true);
                break;
        }
        //start fitting timer
        chronoView.setBase(SystemClock.elapsedRealtime() - test.getTimers().get(questionNum[0])* 1000L);
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
                    Toast.makeText(getApplicationContext(), getString(R.string.error), Toast.LENGTH_LONG).show();
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

    @Override
    public void endQuiz() {
        Intent testIntent = getIntent();
        Test test=testIntent.getSerializableExtra("test", Test.class);
        assert test != null;
        int questionAmount=test.getQuestions().size();
        ArrayList<Integer> answers= test.getAnswerGiven();
        ArrayList<Boolean> correctAnswer=test.getCorrectAnswerGiven();
        ArrayList<Question> questions=test.getQuestions();
        ArrayList<Integer> timers=test.getTimers();

        //save state of last question
        final int[] currQuestion = {questionAmount-1};
        saveQuestionState(currQuestion,answers,test);

        //update times list for all questions
        for(int i=0; i<questionAmount; i++){
            ArrayList<Double> questionTimers=questions.get(i).getTimes();
            if(questionTimers==null) {
                questionTimers = new ArrayList<Double>();
            }
            questionTimers.add(((double)(timers.get(i)))/60);
            questions.get(i).setTimes(questionTimers);

            DatabaseReference timesRef= FirebaseDatabase.getInstance().getReference()
                    .child("questions").child("level_"+test.getLevel()).child(String.valueOf(questions.get(i).getSerialNumber())).child("times");
            timesRef.setValue(questionTimers);

            //check if question level needs change
            questions.get(i).checkQuestionLevel();
        }

        double amountRight=0;
        for(int i=0;i<questionAmount;i++){
            correctAnswer.set(i, answers.get(i)==questions.get(i).getCorrectAnswer());
            if(answers.get(i)==questions.get(i).getCorrectAnswer())
                amountRight++;
        }
        double grade= (amountRight*100)/questionAmount;
        test.setGrade(grade);
        test.setCorrectAnswerGiven(correctAnswer);

        //save finished test to database
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("tests").add(test).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Intent stats=new Intent(getApplicationContext(), quizzStatsActivity.class);
                        stats.putExtra("test", test);
                        stats.putExtra("generalStats", false); //means activity wasn't started from userStats
                        QuestionViewActivity.this.startActivity(stats);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(QuestionViewActivity.this, getString(R.string.error), Toast.LENGTH_LONG).show();
                    }
                });
    }
}