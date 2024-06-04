package com.example.learningelectricityforbagrut;

import android.graphics.Bitmap;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.lang.Double;

public class Question implements Serializable {
    private String questionBody;
    private String imageUrl;
    private ArrayList<String> answers;
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
    public Question(String _questionBody, String _image, ArrayList<String> _answers, int _correctAnswer, int _questionLevel, String UID, long num)
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

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
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

    public void checkQuestionLevel() {
        ArrayList<Double> timesForCalc= new ArrayList<>(this.getTimes());
        if(timesForCalc.size()<10)
            return;
        int size= timesForCalc.size();
        double timeSum=0;
        for(int i=0;i<size;i++){
            if(timesForCalc.get(i)<0.3) //to account for people who skip question because they gave up
                timeSum+=timesForCalc.get(i);
            else
                size--;
        }
        double avg=timeSum/size;

        int level=getQuestionLevel();
        switch (level) {
            case 1:
                if(avg>5)
                    this.setQuestionLevel(level+1);
            case 2:
                if(avg<0.7)
                    this.setQuestionLevel(level-1);
                if(avg>7)
                    this.setQuestionLevel(level+1);
            case 3:
                if(avg<5)
                    this.setQuestionLevel(level-1);
                if(avg>15)
                    this.setQuestionLevel(level+1);
            case 4:
                if(avg<10)
                    this.setQuestionLevel(level-1);
                if(avg>20)
                    this.setQuestionLevel(level+1);
            case 5:
                if(avg<15)
                    this.setQuestionLevel(level-1);
        }
        if(level==this.getQuestionLevel())
            return;
        this.setTimes(null);
        DatabaseReference questionRef=FirebaseDatabase.getInstance().getReference("questions").child("level_"+level).child(String.valueOf(this.getSerialNumber()));
        questionRef.child("questionLevel").setValue(this.getQuestionLevel());
        questionRef.child("times").setValue(null);
    }
}
