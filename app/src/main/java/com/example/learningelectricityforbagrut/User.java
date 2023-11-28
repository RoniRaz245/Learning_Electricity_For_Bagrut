package com.example.learningelectricityforbagrut;

public class User {
    private difficultyTypeLevel calcLevel,  memoryLevel;
    private subjectLevel electroStatic, magField, electroMagInsp;

    public User(){
        this.calcLevel= new difficultyTypeLevel();
        this.memoryLevel= new difficultyTypeLevel();
        this.electroStatic= new subjectLevel();
        this.magField= new subjectLevel();
        this.electroMagInsp= new subjectLevel();
    }
}
