package com.example.learningelectricityforbagrut;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends baseActivity
        implements questionAmountForTest.NoticeDialogListener {
    private Button addReminder, uploadQuestion, startQuiz, openSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setting all buttons
        setContentView(R.layout.activity_home);
        addReminder=findViewById(R.id.addReminder);
        uploadQuestion=findViewById(R.id.makeQuestion);
        startQuiz=findViewById(R.id.startQuiz);


        //set intents for buttons that open other activities
        addReminder.setOnClickListener(v-> addReminder.getContext().startActivity(new Intent(addReminder.getContext(), SetReminderActivity.class)));
        startQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //make test based on user
                showAmountDialog();
            }
        });

        //check if user is a teacher and set the "make question" button to only have use if they are
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth mAuth= FirebaseAuth.getInstance();

        database.child("users").child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User currentUser = dataSnapshot.getValue(User.class);
                        assert currentUser != null;
                        boolean isTeacher=currentUser.isTeacher();
                        if(isTeacher==true) {
                            uploadQuestion.setOnClickListener(v -> uploadQuestion.getContext().startActivity(new Intent(uploadQuestion.getContext(), MakeQuestionActivity.class)));
                        }
                        else {
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

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(uploadQuestion.getContext(),
                                        "הייתה שגיאה, נסה שוב מאוחר יותר",
                                        Toast.LENGTH_LONG)
                                .show();
                    }
                });
    }
    public void showAmountDialog() {
        // Create an instance of the dialog fragment and show it.
        DialogFragment dialog = new questionAmountForTest();
        dialog.show(getSupportFragmentManager(), "questionAmountFragment");
    }
    @Override
    public void onDialogPositiveClick(int amount) {
        // User taps the dialog's positive button.

    }
}