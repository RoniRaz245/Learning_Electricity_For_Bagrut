package com.example.learningelectricityforbagrut;

public class difficultyTypeLevel {
    int amountRank1, successRank1;
    int amountRank2, successRank2;
    int amountRank3, successRank3;
    int amountRank4, successRank4;
    int amountRank5, successRank5;
    double overall;
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
    public double getOverall(){
        updateOverall();
        return overall;
    }
    public void updateOverall(){
        double rank1Percentage= (double)successRank1/amountRank1;
        double rank2Percentage= (double)successRank2/amountRank2;
        double rank3Percentage= (double)successRank3/amountRank3;
        double rank4Percentage= (double)successRank4/amountRank4;
        double rank5Percentage= (double)successRank5/amountRank5;
        double weightedSum= 1*rank1Percentage+2*rank2Percentage+3*rank3Percentage+4*rank4Percentage+5*rank5Percentage;
        double normalisation=5/(1+2+3+4+5); //I want to end up with a ranking between 0 and 5
        overall= weightedSum*normalisation;
    }
}
