package com.example.learningelectricityforbagrut;

public class Answer {
    private String answerBody;
    private boolean correct;
    public Answer(String _answerBody, boolean _isCorrect){
        this.answerBody=_answerBody;
        this.correct=_isCorrect;
    }

    public boolean isCorrect() {
        return correct;
    }

    public String getAnswerBody() {
        return answerBody;
    }
}
