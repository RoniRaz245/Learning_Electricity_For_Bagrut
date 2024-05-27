package com.example.learningelectricityforbagrut;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
        SharedPreferences prefs= getPreferenceManager().getSharedPreferences();
        Context context = getPreferenceManager().getContext();
        findPreference("darkTheme").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
                assert prefs != null;
                prefs.edit().putBoolean("darkTheme",(boolean)newValue).apply();
                myApp app=(myApp)context.getApplicationContext();
                app.setMyTheme();
                return true;
            }
        });
        findPreference("volume").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
                assert prefs != null;
                prefs.edit().putInt("volume",(int)newValue).apply();
                return true;
            }
        });

        findPreference("keepLoggedIn").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
                assert prefs != null;
                prefs.edit().putBoolean("keepLoggedIn", (boolean)newValue).apply();
                return true;
            }
        });
    }
}