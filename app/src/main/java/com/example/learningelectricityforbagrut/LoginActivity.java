package com.example.learningelectricityforbagrut;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.Button;
import android.text.TextUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity {

    private Button TakeToSignUp ;
    private EditText emailTextView ;
    private EditText passwordTextView ;
    private Button login ;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.login);
        emailTextView = findViewById(R.id.email);
        passwordTextView = findViewById(R.id.password);
        TakeToSignUp = findViewById(R.id.TakeToSignUp);
        TakeToSignUp.setOnClickListener(v -> TakeToSignUp.getContext().startActivity(new Intent(TakeToSignUp.getContext(), RegistrationActivity.class)));
        mAuth = FirebaseAuth.getInstance();
        login.setOnClickListener(v -> loginUserAccount());
    }

    private void loginUserAccount()
    {

        String email, password;
        email = emailTextView.getText().toString();
        password = passwordTextView.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(login.getContext(),
                            "Please enter email!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(login.getContext(),
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
                                    Toast.makeText(login.getContext(),
                                                    "Login successful!!",
                                                    Toast.LENGTH_LONG)
                                            .show();

                                    Intent goHome
                                            = new Intent(login.getContext(),
                                            HomeActivity.class);
                                    login.getContext().startActivity(goHome);
                                }

                                else {

                                    Toast.makeText(login.getContext(),
                                                    "Login failed!!",
                                                    Toast.LENGTH_LONG)
                                            .show();

                                }
                            }
                        });
    }
}