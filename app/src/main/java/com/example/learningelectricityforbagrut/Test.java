package com.example.learningelectricityforbagrut;

import java.io.Serializable;
import java.util.ArrayList;

public class Test implements Serializable  { //serializable so it can be passed as an extra on the intent used to start quizz
    private int level;
    private String UID;
    private ArrayList<Question> questions;
    private ArrayList<Boolean> correctAnswerGiven;
    private ArrayList<Integer> answerGiven;
    private ArrayList<Integer> timers;
    private String timeTaken;
    private double grade;

    //constructors
    public Test(){
        this.level=0;
        this.UID=null;
        this.questions=null;
        this.correctAnswerGiven=null;
        this.answerGiven=null;
        this.timers=null;
        this.timeTaken=null;
    }
    public Test(int _level, String _UID, ArrayList<Question> _questions, ArrayList<Boolean> _correctAnswerGiven, ArrayList<Integer> _answerGiven, ArrayList<Integer> _timers, String _timeTaken){
        this.level=_level;
        this.UID=_UID;
        this.questions=_questions;
        this.correctAnswerGiven=_correctAnswerGiven;
        this.answerGiven=_answerGiven;
        this.timers=_timers;
        this.timeTaken=_timeTaken;

        int trueCount = 0;
        for (int i = 0; i < correctAnswerGiven.size(); i++) {
            if (correctAnswerGiven.get(i))
                ++trueCount;
        }
        this.grade= ((double) trueCount /(correctAnswerGiven.size()))*100;
    }
    //getting and setting functions

    public int getLevel() {
        return level;
    }

    public ArrayList<Boolean> getCorrectAnswerGiven() {
        return correctAnswerGiven;
    }

    public ArrayList<Integer> getAnswerGiven() {
        return answerGiven;
    }

    public ArrayList<Integer> getTimers() {
        return timers;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public String getTimeTaken() {
        return timeTaken;
    }

    public String getUID() {
        return UID;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setCorrectAnswerGiven(ArrayList<Boolean> correctAnswerGiven) {
        this.correctAnswerGiven = correctAnswerGiven;
    }

    public void setAnswerGiven(ArrayList<Integer> answerGiven) {
        this.answerGiven = answerGiven;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public void setTimers(ArrayList<Integer> timers) {
        this.timers = timers;
    }

    public void setTimeTaken(String timeTaken) {
        this.timeTaken = timeTaken;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public double getGrade(){
       return this.grade;
    }

}
