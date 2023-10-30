package com.example.learningelectricityforbagrut;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.Button;
import android.text.TextUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity {

    private final Button TakeToSignUp = findViewById(R.id.TakeToSignUp);
    private EditText emailTextView = findViewById(R.id.email);
    private EditText passwordTextView = findViewById(R.id.password);
    Button login = findViewById(R.id.login);
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // taking instance of FirebaseAuth
        mAuth = FirebaseAuth.getInstance();
        // Set on Click Listener on Sign-in button
        login.setOnClickListener(v -> loginUserAccount());
    }

    private void loginUserAccount()
    {

        // Take the value of two edit texts in Strings
        String email, password;
        email = emailTextView.getText().toString();
        password = passwordTextView.getText().toString();

        // validations for input email and password
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this,
                            "Please enter email!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this,
                            "Please enter password!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(
                                    @NonNull Task<AuthResult> task)
                            {
                                if (task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this,
                                                    "Login successful!!",
                                                    Toast.LENGTH_LONG)
                                            .show();

                                    // if sign-in is successful
                                    // intent to home activity
                                    Intent intent
                                            = new Intent(LoginActivity.this,
                                            MainActivity.class);
                                    startActivity(intent);
                                }

                                else {

                                    // sign-in failed
                                    Toast.makeText(LoginActivity.this,
                                                    "Login failed!!",
                                                    Toast.LENGTH_LONG)
                                            .show();

                                }
                            }
                        });
                        TakeToSignUp.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, MainActivity.class)));
    }
}
