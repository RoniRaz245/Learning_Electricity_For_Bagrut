package com.example.learningelectricityforbagrut;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.Button;
import android.text.TextUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends baseActivity {

    private Button TakeToSignUp ;
    private EditText emailTextView ;
    private EditText passwordTextView ;
    private Button login ;
    private FirebaseAuth mAuth;
    private TextView resetPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.login);
        emailTextView = findViewById(R.id.email);
        passwordTextView = findViewById(R.id.password);
        TakeToSignUp = findViewById(R.id.TakeToSignUp);
        resetPassword=findViewById(R.id.resetPassword);

        //take user to registration screen if asked
        TakeToSignUp.setOnClickListener(v -> TakeToSignUp.getContext().startActivity(new Intent(TakeToSignUp.getContext(), RegistrationActivity.class)));

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialog = new getEmail();
                dialog.show(getSupportFragmentManager(), "getEmail");
            }
        });

        mAuth = FirebaseAuth.getInstance();
        login.setOnClickListener(v -> loginUserAccount());

        //take user to homescreen if asked to be kept logged in
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        if(prefs.getBoolean("keepLoggedIn", false)){
            if(mAuth.getCurrentUser()!=null) {
                Intent goHome
                        = new Intent(login.getContext(),
                        HomeActivity.class);
                login.getContext().startActivity(goHome);
            }
            else{
                Toast.makeText(login.getContext(),
                                getString(R.string.error),
                                Toast.LENGTH_LONG)
                        .show();
            }
        }


    }

    private void loginUserAccount()
    {

        String email, password;
        email = emailTextView.getText().toString();
        password = passwordTextView.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(login.getContext(),
                            getString(R.string.email_req),
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(login.getContext(),
                            getString(R.string.pswrd_req),
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
                                    //take user to homescreen since login was successful
                                    Intent goHome
                                            = new Intent(login.getContext(),
                                            HomeActivity.class);
                                    login.getContext().startActivity(goHome);
                                }

                                else {
                                    //generic error message
                                    Toast.makeText(login.getContext(),
                                                    getString(R.string.error),
                                                    Toast.LENGTH_LONG)
                                            .show();

                                }
                            }
                        });
    }
}