package com.example.gramprojectpractice2.model;

import com.google.gson.annotations.SerializedName;

public class WriteRequest {
    @SerializedName("title")
    String title;

    @SerializedName("content")
    String content;

    public String getTitle(){
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent(){
        return this.content;
    }

    public void setContent(String content){
        this.content = content;
    }
}
