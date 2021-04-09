package com.example.apptreineeongrid;

import com.example.apptreineeongrid.Answer;
import com.example.apptreineeongrid.TextHolder;

public class Question implements TextHolder {
    private String text;
    private Answer answer;
    private char difficulty;

    Question(String text, char difficulty) {
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

    public void setDifficulty(char difficulty) {
        this.difficulty = difficulty;
    }
}
