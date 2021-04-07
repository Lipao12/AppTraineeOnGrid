package com.example.apptreineeongrid;

import com.example.apptreineeongrid.TextHolder;

public class Answer implements TextHolder {
    private String text;

    Answer(String answer) {
        this.text = answer;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
