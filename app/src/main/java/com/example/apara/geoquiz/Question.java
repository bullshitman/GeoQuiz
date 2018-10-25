package com.example.apara.geoquiz;

public class Question {
    private int mTextResId;
    private boolean mAnswerTrue;

    Question(int textResId, boolean AnswerTrue){
        mAnswerTrue = AnswerTrue;
        mTextResId = textResId;
    }

    int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }
}
