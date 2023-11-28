package com.example.learningelectricityforbagrut;

public class User {
    private difficultyTypeLevel calcLevel,  memoryLevel;
    private subjectLevel electroStatic, magField, electroMagInsp;
    private String UID;

    public User(String _UID){
        this.calcLevel= new difficultyTypeLevel();
        this.memoryLevel= new difficultyTypeLevel();
        this.electroStatic= new subjectLevel();
        this.magField= new subjectLevel();
        this.electroMagInsp= new subjectLevel();
        this.UID=_UID;
    }

    public difficultyTypeLevel getCalcLevel() {
        return calcLevel;
    }

    public difficultyTypeLevel getMemoryLevel() {
        return memoryLevel;
    }

    public subjectLevel getElectroMagInsp() {
        return electroMagInsp;
    }

    public subjectLevel getElectroStatic() {
        return electroStatic;
    }

    public subjectLevel getMagField() {
        return magField;
    }

    public String getUID() {
        return UID;
    }

    public void setCalcLevel(difficultyTypeLevel _calcLevel) {
        this.calcLevel = _calcLevel;
    }

    public void setElectroMagInsp(subjectLevel _electroMagInsp) {
        this.electroMagInsp = _electroMagInsp;
    }

    public void setElectroStatic(subjectLevel _electroStatic) {
        this.electroStatic = _electroStatic;
    }

    public void setMagField(subjectLevel _magField) {
        this.magField = _magField;
    }

    public void setMemoryLevel(difficultyTypeLevel _memoryLevel) {
        this.memoryLevel = _memoryLevel;
    }

    public void setUID(String _UID) {
        this.UID = _UID;
    }
}
