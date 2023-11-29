package com.example.learningelectricityforbagrut;

import android.graphics.Bitmap;
import java.util.ArrayList;
import java.lang.Double;

public class Question {
    private String questionBody;
    private String image;
    private String[] answers;
    private int correctAnswer;
    private int questionLevel;
    private ArrayList<Double> times;



    public Question(String _questionBody, String _image, String[] _answers, int _correctAnswer)
    {
        this.questionBody=_questionBody;
        this.image=_image;
        this.answers=_answers;
        this.correctAnswer=_correctAnswer;
        times= new ArrayList<Double>();
    }
}
