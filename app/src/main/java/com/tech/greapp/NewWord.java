package com.tech.greapp;

import android.widget.Spinner;

public class NewWord {
    private String newword;
    private String newmeaning;
    private String spinner;


    public NewWord(){

    }

    public NewWord(String spinner) {
        this.spinner = spinner;
    }

    public String getSpinner() {
        return spinner;
    }

    public void setSpinner(String spinner) {
        this.spinner = spinner;
    }

    public NewWord(String newword, String newmeaning) {
        this.newword = newword;
        this.newmeaning = newmeaning;

    }




    public String getNewword() {
        return newword;
    }

    public void setNewword(String newword) {
        this.newword = newword;
    }

    public String getNewmeaning() {
        return newmeaning;
    }

    public void setNewmeaning(String newmeaning) {
        this.newmeaning = newmeaning;
    }


}
