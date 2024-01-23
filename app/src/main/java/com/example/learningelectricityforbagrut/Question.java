package com.example.learningelectricityforbagrut;

import android.graphics.Bitmap;
import java.util.ArrayList;
import java.lang.Double;

public class Question {
    private String questionBody;
    private String imageUrl;
    private String[] answers;
    private int correctAnswer;
    private int questionLevel;
    private ArrayList<Double> times;
    private String teacher;
    private long serialNumber;



    public Question(String _questionBody, String _image, String[] _answers, int _correctAnswer, int _questionLevel, String UID, long num)
    {
        this.questionBody=_questionBody;
        this.questionLevel=_questionLevel;
        this.imageUrl=_image;
        this.answers=_answers;
        this.correctAnswer=_correctAnswer;
        this.times= new ArrayList<Double>();
        this.teacher=UID;
        this.serialNumber=num;
    }
}
