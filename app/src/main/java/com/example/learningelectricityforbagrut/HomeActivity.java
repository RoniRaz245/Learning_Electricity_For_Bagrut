package com.example.learningelectricityforbagrut;

import android.content.Intent;
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
import java.util.Date;

public class HomeActivity extends baseActivity
        /*implements questionAmountForTest.NoticeDialogListener*/ {
    private Button addReminder, uploadQuestion, startQuiz, openSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setting all buttons
        setContentView(R.layout.activity_home);
        addReminder = findViewById(R.id.addReminder);
        uploadQuestion = findViewById(R.id.makeQuestion);
        startQuiz = findViewById(R.id.startQuiz);


        //set intents for buttons that open other activities
        addReminder.setOnClickListener(v -> addReminder.getContext().startActivity(new Intent(addReminder.getContext(), SetReminderActivity.class)));
        /*startQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open fragment that lets user set test settings
                showAmountDialog();
            }
        });*/

        //check if user is a teacher and set the "make question" button to only have use if they are
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        database.child("users").child(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful())
                    Toast.makeText(uploadQuestion.getContext(),
                                    "הייתה שגיאה, נסה שוב מאוחר יותר",
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
                                                "אין לך את ההרשאות להעלות שאלה!",
                                                Toast.LENGTH_LONG)
                                        .show();
                            }
                        });
                    }
                }
            }
        });
    }

        /*public void showAmountDialog () {
            // Create an instance of the dialog fragment and show it.
            DialogFragment dialog = new questionAmountForTest();
            dialog.show(getSupportFragmentManager(), "questionAmountFragment");
        }
        @Override
        public void onDialogPositiveClick ( int amount){
            //get what's needed to start creating test

            DatabaseReference database = FirebaseDatabase.getInstance().getReference();
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            database.child("users").child(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(
                    new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (!task.isSuccessful())
                                Toast.makeText(startQuiz.getContext(), "הייתה שגיאה, נסה שוב מאוחר יותר", Toast.LENGTH_LONG).show();
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
            String levelName="level_"+level;
            database.child("questions").child(levelName).get().addOnCompleteListener(
                    new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {

                        }
                    })

        }*/
}