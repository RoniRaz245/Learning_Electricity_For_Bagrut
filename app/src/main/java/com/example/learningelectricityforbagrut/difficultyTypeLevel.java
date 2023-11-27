package com.example.learningelectricityforbagrut;

public class difficultyTypeLevel {
    int amountRank1, successRank1;
    int amountRank2, successRank2;
    int amountRank3, successRank3;
    int amountRank4, successRank4;
    int amountRank5, successRank5;
    int overall;
    public difficultyTypeLevel(){
        amountRank1=0; successRank1=0;
        amountRank2=0; successRank2=0;
        amountRank3=0; successRank3=0;
        amountRank4=0; successRank4=0;
        amountRank5=0; successRank5=0;
        overall=0;
    }
    public void addAmountRank1(int add){
        this.amountRank1+=add;
    }
    public void addSuccessRank1(int add){
        this.successRank1+=add;
    }
    public void addAmountRank2(int add){
        this.amountRank2+=add;
    }
    public void addSuccessRank2(int add){
        this.successRank2+=add;
    }
    public void addAmountRank3(int add){
        this.amountRank3+=add;
    }
    public void addSuccessRank3(int add){
        this.successRank3+=add;
    }
    public void addAmountRank4(int add){
        this.amountRank4+=add;
    }
    public void addSuccessRank4(int add) {
        this.successRank4 += add;
    }
    public void addAmountRank5(int add){
        this.amountRank5+=add;
    }
    public void addSuccessRank5(int add) {
        this.successRank5 += add;
    }
}
