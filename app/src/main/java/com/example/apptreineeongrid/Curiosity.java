package com.example.apptreineeongrid;

import com.example.apptreineeongrid.TextHolder;

public class Curiosity implements TextHolder {
    private String text;

    Curiosity(String text){
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
