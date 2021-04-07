package com.example.apptreineeongrid;

import com.example.apptreineeongrid.Answer;
import com.example.apptreineeongrid.TextHolder;

public class Question implements TextHolder {
    private String text;
    private Answer answer;
    private int difficulty;

    Question(String text, int difficulty) {
        this.text = text;
        this.difficulty = difficulty;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}
