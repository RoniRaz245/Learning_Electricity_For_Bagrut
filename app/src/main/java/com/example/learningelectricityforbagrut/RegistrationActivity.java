package com.example.learningelectricityforbagrut;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.Button;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class RegistrationActivity extends baseActivity {

    private EditText emailTextView, passwordTextView;
    private FirebaseAuth mAuth;
    private Button Btn;
    private RadioButton isTeacherCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ActionBar bar=getSupportActionBar();
        if(bar!=null)
            bar.hide();
        setContentView(R.layout.activity_registration);
        mAuth = FirebaseAuth.getInstance();
        emailTextView = findViewById(R.id.email);
        passwordTextView = findViewById(R.id.passwd);
        isTeacherCheck=findViewById(R.id.isTeacherCheck);
        Btn = findViewById(R.id.btnregister);
        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                registerNewUser();
            }
        });
    }

    private void registerNewUser()
    {
        // Take the value of two edit texts in Strings
        String email, password;
        email = emailTextView.getText().toString();
        password = passwordTextView.getText().toString();

        //make sure user put in parameters
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(Btn.getContext(),
                            "מייל בבקשה!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(Btn.getContext(),
                            "סיסמה בבקשה!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }

        mAuth
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {
                            //make a user with given+default parameters and upload to firebase
                            User user = new User(isTeacherCheck.isChecked());
                            FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid()).setValue(user);
                            Toast.makeText(Btn.getContext(),
                                            "נרשמת בהצלחה!",
                                            Toast.LENGTH_LONG)
                                    .show();

                            Intent intent
                                    = new Intent(Btn.getContext(),
                                    HomeActivity.class);
                            Btn.getContext().startActivity(intent);
                        }
                        else {
                            //generic error message

                            Toast.makeText(
                                            Btn.getContext(),
                                            "ההרשמה נכשלה. אנא נסה שוב מאוחר יותר",
                                            Toast.LENGTH_LONG)
                                    .show();

                        }
                    }
                });
    }
}