package com.example.learningelectricityforbagrut;

import android.app.Application;
import android.content.SharedPreferences;

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
            setTheme(R.style.dark_mode);
        else
            setTheme(R.style.light_mode);
    }
}
