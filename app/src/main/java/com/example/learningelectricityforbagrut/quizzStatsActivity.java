package com.example.learningelectricityforbagrut;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class quizzStatsActivity extends baseActivity {

    TextView titleView, gradeView, levelView, timeTakenView;
    ImageButton levelInfo;
    Button viewQuestions, backToUserStats;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz_stats);

        setSupportActionBar(findViewById(R.id.my_toolbar));
        getSupportActionBar().show();

        titleView=findViewById(R.id.title);
        gradeView=findViewById(R.id.grade);
        levelView=findViewById(R.id.updateOnLevel);
        timeTakenView=findViewById(R.id.timeTaken);
        levelInfo=findViewById(R.id.levelInfo);
        viewQuestions=findViewById(R.id.viewQuestions);
        backToUserStats=findViewById(R.id.goBackToStats);

        backToUserStats.setVisibility(View.GONE);

        levelInfo.setOnClickListener(v->giveInfo());

        Intent intent = getIntent();

        boolean fromUserStats=intent.getBooleanExtra("generalStats", false);
        if(fromUserStats){
            backToUserStats.setVisibility(View.VISIBLE);
            backToUserStats.setOnClickListener(v -> backToUserStats.getContext().startActivity(new Intent(backToUserStats.getContext(), userStatsActivity.class)));
        }
        Test test=intent.getSerializableExtra("test", Test.class);
        assert test!=null;


        int testLevel= test.getLevel();
        titleView.setText(getString(R.string.congrats)+" " +testLevel+"!");

        double grade=test.getGrade();
        gradeView.setText(getString(R.string.grade)+" " +grade);

        ArrayList<Integer> times=test.getTimers();
        int questionAmount=0;
        int totalTime=0;
        while(questionAmount<=times.size()){
            totalTime+=times.get(questionAmount-1);
            questionAmount++;
        }
        double totalTimeMinutes= (double) totalTime /60;
        double avgTimeMinutes= totalTimeMinutes/questionAmount;
        timeTakenView.setText(getString(R.string.time_one)+" " +totalTimeMinutes+ " " +getString(R.string.time_two)+" " +avgTimeMinutes+" " +getString(R.string.time_three));

        viewQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewQuestionsAgain=new Intent(getApplicationContext(), QuestionViewAfterTestActivity.class);
                viewQuestionsAgain.putExtra("test", test);
                viewQuestionsAgain.putExtra("generalStats", fromUserStats);
                startActivity(viewQuestionsAgain);
            }
        });

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        database.child("users").child(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    User user=task.getResult().getValue(User.class);
                    int userLevel=user.getLevel();
                    int levelUpdated = user.updateLevel();
                    if(levelUpdated>0) //went up a level
                            levelView.setText(getString(R.string.lvl_up) + " " + userLevel + getString(R.string.v_nice));
                    else if(levelUpdated==0) //stayed in same level
                            levelView.setText(getString(R.string.stayed_in_level)+" " +userLevel);
                    else //went down a level
                            levelView.setText(getString(R.string.lvl_down)+ " " +userLevel+" " +getString(R.string.cope));

                }
            }
        });
    }
}