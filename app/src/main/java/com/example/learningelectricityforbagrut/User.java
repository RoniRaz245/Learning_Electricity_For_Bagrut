package com.example.learningelectricityforbagrut;

public class User {
    private int level;

    public User(){
        this.level=3;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    public void updateLevel(double grade){
        //if user got low grade take him down levels, if got a high grade take up levels
        //TODO: decide on grade ranges later
    }
}
