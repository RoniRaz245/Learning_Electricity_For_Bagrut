package com.example.learningelectricityforbagrut;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {
    private Button addReminder;
    private Button uploadQuestion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        addReminder=findViewById(R.id.addReminder);
        uploadQuestion=findViewById(R.id.makeQuestion);
        addReminder.setOnClickListener(v-> addReminder.getContext().startActivity(new Intent(addReminder.getContext(), SetReminderActivity.class)));
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth mAuth= FirebaseAuth.getInstance();
        database.child("users").child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User currentUser = dataSnapshot.getValue(User.class);
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

}