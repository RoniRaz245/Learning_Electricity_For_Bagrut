package com.example.learningelectricityforbagrut;

public class User {
    private int level;
    private boolean teacher;

    public User(){
        this.level=3;
        this.teacher=false;
    }
    public User(boolean _isTeacher){
        this.level=3;
        this.teacher=_isTeacher;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    public void setTeacher(boolean _isTeacher){
        this.teacher=_isTeacher;
    }
    public boolean getTeacher(){
        return teacher;
    }
    public void updateLevel(double grade){
        //if user got low grade take him down levels, if got a high grade take up levels
        //TODO: decide on grade ranges later
    }
}
