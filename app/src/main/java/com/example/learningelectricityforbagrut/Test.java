package com.example.learningelectricityforbagrut;

import java.io.Serializable;

public class Test implements Serializable  { //serializable so it can be passed as an extra on the intent used to start quizz
    private String UID;
    private Question[] questions;
    private boolean[] correctAnswerGiven;
    private int[] answerGiven;
    private int[] timers;
    private String timeTaken;

    //constructors
    public Test(){
        this.UID=null;
        this.questions=null;
        this.correctAnswerGiven=null;
        this.answerGiven=null;
        this.timers=null;
        this.timeTaken=null;
    }
    public Test(String _UID, Question[] _questions, boolean[] _correctAnswerGiven, int[] _answerGiven, int[] _timers, String _timeTaken){
        this.UID=_UID;
        this.questions=_questions;
        this.correctAnswerGiven=_correctAnswerGiven;
        this.answerGiven=_answerGiven;
        this.timers=_timers;
        this.timeTaken=_timeTaken;

    }
    //getting and setting functions
    public boolean[] getCorrectAnswerGiven() {
        return correctAnswerGiven;
    }

    public int[] getAnswerGiven() {
        return answerGiven;
    }

    public int[] getTimers() {
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

    public void setAnswerGiven(int[] answerGiven) {
        this.answerGiven = answerGiven;
    }

    public void setQuestions(Question[] questions) {
        this.questions = questions;
    }

    public void setTimers(int[] timers) {
        this.timers = timers;
    }

    public void setTimeTaken(String timeTaken) {
        this.timeTaken = timeTaken;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }
}
