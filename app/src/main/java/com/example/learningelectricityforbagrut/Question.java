package com.example.learningelectricityforbagrut;

import android.graphics.Bitmap;

public class Question {
    private String questionBody;
    private String image;
    private Answer option1;
    private Answer option2;
    private Answer option3;
    private Answer option4;

    //difficulty rankings
    private int calc;
    private int memory;
    private String subject;

    public Question(String _questionBody, String _image, Answer _option1, Answer _option2, Answer _option3, Answer _option4, int _calc, int _memory)
    {
        this.questionBody=_questionBody;
        this.image=_image;
        this.option1=_option1; this.option2=_option2; this.option3=_option3; this.option4=_option4;
        this.calc=_calc;
        this.memory=_memory;
    }
}
