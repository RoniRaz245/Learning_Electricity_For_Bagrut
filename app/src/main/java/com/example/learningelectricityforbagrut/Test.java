package com.example.learningelectricityforbagrut;

import java.io.Serializable;

public class Test implements Serializable  { //serializable so it can be passed as an extra on the intent used to start quizz
    private String UID;
    private Question[] questions;
    private boolean[] correctAnswerGiven;
    private double[] timers;
    private String timeTaken;

    //constructors
    public Test(){
        this.UID=null;
        this.questions=null;
        this.correctAnswerGiven=null;
        this.timers=null;
        this.timeTaken=null;
    }
    public Test(String _UID, Question[] _questions, boolean[] _correctAnswerGiven, double[] _timers, String _timeTaken){
        this.UID=_UID;
        this.questions=_questions;
        this.correctAnswerGiven=_correctAnswerGiven;
        this.timers=_timers;
        this.timeTaken=_timeTaken;

    }
    //getting and setting functions
    public boolean[] getCorrectAnswerGiven() {
        return correctAnswerGiven;
    }

    public double[] getTimers() {
        return timers;
    }

    public Question[] getQuestions() {
        return questions;
    }

    public String getTimeTaken() {
        return timeTaken;
    }

    public String getUID() {
        return UID;
    }

    public void setCorrectAnswerGiven(boolean[] correctAnswerGiven) {
        this.correctAnswerGiven = correctAnswerGiven;
    }

    public void setQuestions(Question[] questions) {
        this.questions = questions;
    }

    public void setTimers(double[] timers) {
        this.timers = timers;
    }

    public void setTimeTaken(String timeTaken) {
        this.timeTaken = timeTaken;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }
}
