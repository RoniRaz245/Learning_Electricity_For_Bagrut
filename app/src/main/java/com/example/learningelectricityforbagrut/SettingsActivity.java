package com.example.learningelectricityforbagrut;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends baseActivity {
    Button signOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setSupportActionBar(findViewById(R.id.my_toolbar));
        getSupportActionBar().show();

        signOut=findViewById(R.id.logout);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //clear sharedPreferences data
                SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(signOut.getContext());
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear();
                editor.commit();
                //log out through firebase auth
                FirebaseAuth.getInstance().signOut();
                Intent loginScreen=new Intent(signOut.getContext(), LoginActivity.class);
                signOut.getContext().startActivity(loginScreen);
                finish();
            }
        });

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings_container, new SettingsFragment())
                .commit();
    }

}