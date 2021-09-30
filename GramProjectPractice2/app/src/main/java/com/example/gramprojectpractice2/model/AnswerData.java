package com.example.gramprojectpractice2.model;

import com.google.gson.annotations.SerializedName;

public class AnswerData {

    @SerializedName("content")
    private String answer;

    @SerializedName("id")
    private String name;

    public AnswerData(String answer, String name) {
        this.answer = answer;
        this.name = name;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
