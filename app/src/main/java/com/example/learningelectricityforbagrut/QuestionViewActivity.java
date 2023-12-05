package com.example.learningelectricityforbagrut;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

public class QuestionViewActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    protected TextToSpeech tts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_view);

        TextToSpeech tts= new TextToSpeech(this.getApplicationContext(),this);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            Locale Hebrew= new Locale("he");
            int result= tts.setLanguage(Hebrew);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("error", "This Language is not supported");
            } else {
                textToSpeak();
            }
        }
        else {
            Log.e("error", "Failed to Initialize");
        }
    }
    private void textToSpeak(){

    }
}