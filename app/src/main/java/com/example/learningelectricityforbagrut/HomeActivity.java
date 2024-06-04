package com.example.learningelectricityforbagrut;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class HomeActivity extends baseActivity
        implements questionAmountForTest.NoticeDialogListener {
    private Button addReminder, uploadQuestion, startQuiz, openStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setting all buttons
        setContentView(R.layout.activity_home);
        setSupportActionBar(findViewById(R.id.my_toolbar));
        getSupportActionBar().show();
        addReminder = findViewById(R.id.addReminder);
        uploadQuestion = findViewById(R.id.makeQuestion);
        startQuiz = findViewById(R.id.startQuiz);
        openStats=findViewById(R.id.viewStats);


        //set intents for buttons that open other activities
        addReminder.setOnClickListener(v -> addReminder.getContext().startActivity(new Intent(addReminder.getContext(), SetReminderActivity.class)));
        openStats.setOnClickListener(v -> getApplicationContext().startActivity(new Intent(getApplicationContext(), userStatsActivity.class)));
        startQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open fragment that lets user set test settings
                showAmountDialog();
            }
        });

        //check if user is a teacher and set the "make question" button to only have use if they are
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        database.child(getString(R.string.users)).child(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful())
                    Toast.makeText(uploadQuestion.getContext(),
                                    getString(R.string.error),
                                    Toast.LENGTH_LONG)
                            .show();
                else {
                    User currentUser = task.getResult().getValue(User.class);
                    assert currentUser != null;
                    boolean isTeacher = currentUser.isTeacher();
                    if (isTeacher) {
                        uploadQuestion.setOnClickListener(v -> uploadQuestion.getContext().startActivity(new Intent(uploadQuestion.getContext(), MakeQuestionActivity.class)));
                    } else {
                        uploadQuestion.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(uploadQuestion.getContext(),
                                                getString(R.string.no_access),
                                                Toast.LENGTH_LONG)
                                        .show();
                            }
                        });
                    }
                }
            }
        });
    }

        public void showAmountDialog () {
            // Create an instance of the dialog fragment and show it.
            DialogFragment dialog = new questionAmountForTest();
            dialog.show(getSupportFragmentManager(), "questionAmountFragment");
        }
        @Override
        public void onDialogPositiveClick ( int amount){
            //get what's needed to start creating test

            DatabaseReference database = FirebaseDatabase.getInstance().getReference();
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            database.child(getString(R.string.users)).child(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(
                    new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (!task.isSuccessful())
                                Toast.makeText(startQuiz.getContext(), getString(R.string.error), Toast.LENGTH_LONG).show();
                            else {
                                User currentUser = task.getResult().getValue(User.class);
                                assert currentUser != null;
                                String UID = currentUser.getUID();
                                int level = currentUser.getLevel();
                                makeTest(UID, level, amount, database);
                            }
                        }
                    });
            }
        public void makeTest (String UID, int level, int questionAmount, DatabaseReference database){
            String levelName=getString(R.string.level_)+level;
            database.child(getString(R.string.questions)).child(levelName).get().addOnCompleteListener(
                    new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if(!task.isSuccessful())
                                Toast.makeText(startQuiz.getContext(), getString(R.string.error), Toast.LENGTH_LONG).show();

                            else {
                                HashMap<String, Question> questions = new HashMap<String,Question>();
                                for(DataSnapshot question: task.getResult().getChildren()){
                                    questions.put(question.getKey(), question.getValue(Question.class));
                                }
                                if(questions==null)
                                    Toast.makeText(startQuiz.getContext(),  getString(R.string.no_questions), Toast.LENGTH_LONG).show();

                                else if(questions.size()<questionAmount)
                                    Toast.makeText(startQuiz.getContext(), getString(R.string.only) +questions.size() + getString(R.string.too_little_questions), Toast.LENGTH_LONG).show();

                                else{
                                    //access questions in random order to run
                                    List keys = new ArrayList(questions.keySet());
                                    Collections.shuffle(keys);
                                    keys=keys.subList(0, questionAmount);
                                    ArrayList<Question> questionsForTest=new ArrayList<>(questionAmount);
                                    for (int i=0; i<questionAmount; i++)
                                        questionsForTest.add(questions.get(keys.get(i)));

                                    //make array for timers at correct size
                                    ArrayList<Integer> timers=new ArrayList<>(questionAmount);
                                    for(int i=0;i<questionAmount;i++)
                                        timers.add(0);

                                    //get time test was taken to save test with
                                    Locale locale= new Locale(getString(R.string.hebrew_locale));
                                    Date date = new Date();
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss", locale);
                                    String timeTaken= dateFormat.format(date);

                                    //array to put if answers were correct in
                                    ArrayList<Boolean> correctAnswers=new ArrayList<>(questionAmount);
                                    for(int i=0;i<questionAmount;i++)
                                        correctAnswers.add(false);

                                    //array to put answers in
                                    ArrayList<Integer> answers=new ArrayList<>(questionAmount);
                                    for(int i=0;i<questionAmount;i++)
                                        answers.add(-1);

                                    Test test=new Test(level, UID, questionsForTest, correctAnswers, answers, timers, timeTaken);
                                    Intent startTest = new Intent(getApplicationContext(), QuestionViewActivity.class);
                                    startTest.putExtra(getString(R.string.test), test);
                                    startActivity(startTest);
                                }
                            }
                        }
                    });

        }
}