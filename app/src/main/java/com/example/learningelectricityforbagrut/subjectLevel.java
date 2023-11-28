package com.example.learningelectricityforbagrut;

public class subjectLevel {
    private int amountAnswered;
    private int amountAnsweredCorrectly;
    private double percentage;
    public subjectLevel(){
        this.amountAnswered=0;
        this.amountAnsweredCorrectly=0;
        this.percentage=0.0;
    }

    public void addAmountAnswered(int add) {

        this.amountAnswered += add;
    }

    public void addAmountAnsweredCorrectly(int add) {

        this.amountAnsweredCorrectly += add;
    }
    public void updatePercentage(){
        if (amountAnswered != 0) {
            double modifier = 100.0 / this.amountAnswered;
            percentage = amountAnsweredCorrectly * modifier;
        }
        else{
            percentage=0.0;
        }
    }

    public double getPercentage() {

        return percentage;
    }

    public int getAmountAnswered() {
        return amountAnswered;
    }

    public int getAmountAnsweredCorrectly() {
        return amountAnsweredCorrectly;
    }

    public void setAmountAnswered(int amountAnswered) {
        this.amountAnswered = amountAnswered;
    }

    public void setAmountAnsweredCorrectly(int amountAnsweredCorrectly) {
        this.amountAnsweredCorrectly = amountAnsweredCorrectly;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}
