package com.example.learningelectricityforbagrut;

public class difficultyTypeLevel {
    private int amountRank1, successRank1;
    private int amountRank2, successRank2;
    private int amountRank3, successRank3;
    private int amountRank4, successRank4;
    private int amountRank5, successRank5;
    private double overall;
    public difficultyTypeLevel(){
        this.amountRank1=0; this.successRank1=0;
        this.amountRank2=0; this.successRank2=0;
        this.amountRank3=0; this.successRank3=0;
        this.amountRank4=0; this.successRank4=0;
        this.amountRank5=0; this.successRank5=0;
        this.overall=0.0;
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

    public int getAmountRank1() {
        return amountRank1;
    }

    public int getAmountRank2() {
        return amountRank2;
    }

    public int getAmountRank3() {
        return amountRank3;
    }

    public int getAmountRank4() {
        return amountRank4;
    }

    public int getAmountRank5() {
        return amountRank5;
    }

    public int getSuccessRank1() {
        return successRank1;
    }

    public int getSuccessRank2() {
        return successRank2;
    }

    public int getSuccessRank3() {
        return successRank3;
    }

    public int getSuccessRank4() {
        return successRank4;
    }

    public int getSuccessRank5() {
        return successRank5;
    }

    public void setAmountRank1(int amountRank1) {
        this.amountRank1 = amountRank1;
    }

    public void setAmountRank2(int amountRank2) {
        this.amountRank2 = amountRank2;
    }

    public void setAmountRank3(int amountRank3) {
        this.amountRank3 = amountRank3;
    }

    public void setAmountRank4(int amountRank4) {
        this.amountRank4 = amountRank4;
    }

    public void setAmountRank5(int amountRank5) {
        this.amountRank5 = amountRank5;
    }

    public void setOverall(double overall) {
        this.overall = overall;
    }

    public void setSuccessRank1(int successRank1) {
        this.successRank1 = successRank1;
    }

    public void setSuccessRank2(int successRank2) {
        this.successRank2 = successRank2;
    }

    public void setSuccessRank3(int successRank3) {
        this.successRank3 = successRank3;
    }

    public void setSuccessRank4(int successRank4) {
        this.successRank4 = successRank4;
    }

    public void setSuccessRank5(int successRank5) {
        this.successRank5 = successRank5;
    }
}
