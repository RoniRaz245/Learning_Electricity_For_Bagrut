package com.example.learningelectricityforbagrut;

public class subjectLevel {
    int amountAnswered;
    int amountAnsweredCorrectly;
    double percentage;
    public subjectLevel(){
        amountAnswered=0;
        amountAnsweredCorrectly=0;
        percentage=0;
    }

    public void addAmountAnswered(int add) {
        this.amountAnswered += add;
    }

    public void addAmountAnsweredCorrectly(int add) {
        this.amountAnsweredCorrectly += add;
    }
    public void updatePercentage(){
        double modifier= 100.0/this.amountAnswered;
        percentage= amountAnsweredCorrectly*modifier;
    }

    public double getPercentage() {
        return percentage;
    }
}
