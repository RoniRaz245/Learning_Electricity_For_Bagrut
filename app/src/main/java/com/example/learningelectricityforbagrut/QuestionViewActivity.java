package com.example.learningelectricityforbagrut;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class QuestionViewActivity extends baseActivity /*implements TextToSpeech.OnInitListener*/ {
    private TextView questionBody;

    /*private TextToSpeech tts;
    private Button ttsButton;*/
    //TTS not in use till further notice
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_view);
        questionBody=findViewById(R.id.questionBody);
        Intent testIntent = getIntent();
        Test currTest=testIntent.getSerializableExtra("test", Test.class);
        /* ttsButton=findViewById(R.id.ttsButton);
        tts= new TextToSpeech(this,this);;

        ttsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textToSpeak(); //calls function that reads out text
            }
        });*/
    }
    /* private void textToSpeak(){
        text=textView.getText().toString();
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            Locale Hebrew= new Locale("he");
            int result= tts.setLanguage(Hebrew);

            if (result == TextToSpeech.LANG_MISSING_DATA ) {
                Log.e("error", "download info");
            }
            else if(result == TextToSpeech.LANG_NOT_SUPPORTED) {
                //if tts is initialized but hebrew isn't supported
                Log.e("error", "This Language is not supported");
            }
        }
        else {
            Log.e("error", "Failed to Initialize");
        }
    }*/
}