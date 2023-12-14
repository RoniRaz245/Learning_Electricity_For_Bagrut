package com.example.learningelectricityforbagrut;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

public class SettingsFragment extends PreferenceFragmentCompat {
    public int themeId;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
        Context context = getPreferenceManager().getContext();
        findPreference("theme").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
                myApp app=(myApp)context.getApplicationContext();
                app.setMyTheme();
                Toast.makeText(context, "פלטת הצבעים שונתה!", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        findPreference("volume").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
                Toast.makeText(context, "הווליום שונה!", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        findPreference("compressImage").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
                Toast.makeText(context, "העדפות איכות תמונה שונו!", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }


}