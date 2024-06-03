package com.example.learningelectricityforbagrut;

import static com.google.android.material.internal.ContextUtils.getActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

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

    private EditText emailTextView, passwordTextView, enterId, enterPhoneNum;
    private FirebaseAuth mAuth;
    private Button Btn;
    private RadioButton isTeacherCheck, keepLoggedIn;

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
        enterId=findViewById(R.id.teacherLicense);
        enterPhoneNum=findViewById(R.id.phoneNumber);
        keepLoggedIn=findViewById(R.id.keepLoggedIn);
        Btn = findViewById(R.id.btnregister);

        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                registerNewUser();
            }
        });
        isTeacherCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterId.setVisibility(passwordTextView.VISIBLE);
                enterPhoneNum.setVisibility(passwordTextView.VISIBLE);
            }
        });
        keepLoggedIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(keepLoggedIn.getContext());
                SharedPreferences.Editor edit=prefs.edit();
                edit.putBoolean("keepLoggedIn", true);
                edit.apply();
            }
        });
    }

    private void registerNewUser()
    {
        // Take the value of edit texts
        String email, password, name;
        email = emailTextView.getText().toString();
        password = passwordTextView.getText().toString();
        long ID=0;
        int phoneNumber=0;
            if (isTeacherCheck.isChecked()){
                try {
                    ID = Long.parseLong(enterId.getText().toString());
                } catch (Exception e) {
                    Toast.makeText(enterId.getContext(),
                                    "מספר רישוי כרצף מספרים בבקשה!",
                                    Toast.LENGTH_LONG)
                            .show();
                    return;
                }
            try {
                phoneNumber = Integer.parseInt(enterId.getText().toString());
            } catch (Exception e) {
                Toast.makeText(enterId.getContext(),
                                "טלפון כרצף מספרים בבקשה!",
                                Toast.LENGTH_LONG)
                        .show();
                return;
            }
        }


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

        long finalID = ID;
        int finalPhoneNumber = phoneNumber;
        mAuth
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {
                            //make a user with given+default parameters and upload to firebase
                            String UID=mAuth.getCurrentUser().getUid();
                            User user = new User(isTeacherCheck.isChecked(), UID, finalID, finalPhoneNumber);
                            FirebaseDatabase.getInstance().getReference().child("users").child(UID).setValue(user);

                            //save user prefs to sharedPreferences
                            SharedPreferences prefs= getPreferences(Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("UID", UID);
                            editor.apply();

                            //take user to home screen
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