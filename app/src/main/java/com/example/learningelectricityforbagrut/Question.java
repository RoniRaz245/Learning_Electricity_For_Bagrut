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
    //constructors
    public Question(){
        this.questionBody=null;
        this.questionLevel=0;
        this.imageUrl=null;
        this.answers=null;
        this.correctAnswer=0;
        this.times= null;
        this.teacher=null;
        this.serialNumber=0;
    }
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
    
    //getting and setting functions
    public int getQuestionLevel() {
        return questionLevel;
    }

    public ArrayList<Double> getTimes() {
        return times;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public String getQuestionBody() {
        return questionBody;
    }

    public long getSerialNumber() {
        return serialNumber;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTeacher() {
        return teacher;
    }

    public String[] getAnswers() {
        return answers;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public void setQuestionBody(String questionBody) {
        this.questionBody = questionBody;
    }

    public void setQuestionLevel(int questionLevel) {
        this.questionLevel = questionLevel;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setSerialNumber(long serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public void setTimes(ArrayList<Double> times) {
        this.times = times;
    }
}
