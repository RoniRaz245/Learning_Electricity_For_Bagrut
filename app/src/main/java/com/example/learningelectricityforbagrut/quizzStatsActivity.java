package com.example.learningelectricityforbagrut;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageButton;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.learningelectricityforbagrut.databinding.ActivityQuizzStatsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class quizzStatsActivity extends baseActivity {

    TextView titleView, gradeView, levelView, timeTakenView;
    ImageButton levelInfo;
    Button viewQuestions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz_stats);
        titleView=findViewById(R.id.title);
        gradeView=findViewById(R.id.grade);
        levelView=findViewById(R.id.updateOnLevel);
        timeTakenView=findViewById(R.id.timeTaken);
        levelInfo=findViewById(R.id.levelInfo);
        viewQuestions=findViewById(R.id.viewQuestions);

        levelInfo.setOnClickListener(v->giveInfo());

        Intent intent = getIntent();
        Test test=intent.getSerializableExtra("test", Test.class);
        assert test!=null;


        int testLevel= test.getLevel();
        titleView.setText("כל הכבוד על השלמת בוחן ברמה "+testLevel+"!");

        double grade=test.getGrade();
        gradeView.setText("ציונך הוא "+grade);

        ArrayList<Integer> times=test.getTimers();
        int questionAmount=1;
        int totalTime=0;
        while(questionAmount<=times.size()){
            totalTime+=times.get(questionAmount-1);
            questionAmount++;
        }
        int avgTime=totalTime/questionAmount;
        double totalTimeMinutes= (double) totalTime /60;
        double avgTimeMinutes= (double) avgTime /60;
        timeTakenView.setText("הבוחן לקח לך "+totalTime+" שניות, כלומר "+totalTimeMinutes+" דקות. זה אומר שבממוצע, שאלה לוקחת לך "+avgTime+" שניות, כלומר "+avgTimeMinutes+" דקות.");

        viewQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewQuestionsAgain=new Intent(getApplicationContext(), QuestionViewAfterTestActivity.class);
                viewQuestionsAgain.putExtra("test", test);
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
                    switch(levelUpdated) {
                        case 1: //went up a level
                            levelView.setText("בזאת, עלית לרמה " + userLevel + ", יפה מאוד!");
                        case 0: //stayed in same level
                            levelView.setText("נשארת ברמה "+userLevel);
                        case -1:
                            levelView.setText("ירדת לרמה "+userLevel+". אל דאגה, השקעתך לא תתבזבז, בהצלחה בבוחן הבא!");
                    }
                }
            }
        });
    }
}