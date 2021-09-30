package com.example.gramprojectpractice2.model;

import com.google.gson.annotations.SerializedName;

public class CommentWriteResponse {
    @SerializedName("content")
    String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
