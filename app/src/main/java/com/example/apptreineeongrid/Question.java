package com.example.apptreineeongrid;

import android.os.Parcel;
import android.os.Parcelable;

public class Question {
    private String text;
    private String answer;
    private String incorrect1;
    private String incorrect2;
    private String curiosity;
    private char difficulty;

    Question(String text, String answer, String incorrect1, String incorrect2, String curiosity, char difficulty) {
        this.text       = text;
        this.answer     = answer;
        this.incorrect1 = incorrect1;
        this.incorrect2 = incorrect2;
        this.curiosity  = curiosity;
        this.difficulty = difficulty;
    }

    public String getText() {
        return text;
    }

    public String getAnswer() {
        return answer;
    }

    public String getCorrect() {
        return answer;
    }

    public String getIncorrect1() {
        return incorrect1;
    }

    public String getIncorrect2() {
        return incorrect2;
    }

    public String getCuriosity() {
        return curiosity;
    }

    public char getDifficulty() {
        return difficulty;
    }
}
