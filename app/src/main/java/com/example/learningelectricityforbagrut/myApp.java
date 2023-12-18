package com.example.learningelectricityforbagrut;

import android.app.Application;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

public class myApp extends Application {
    public void onCreate() {
        super.onCreate();
        setMyTheme();//gets theme from shared preferences and sets whole app to it

    }
    public void setMyTheme(){
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        boolean darkThemeOn=prefs.getBoolean("darkTheme", false);
        if(darkThemeOn)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }
}
