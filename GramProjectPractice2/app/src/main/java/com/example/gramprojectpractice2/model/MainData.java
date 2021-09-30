package com.example.gramprojectpractice2.model;

import com.google.gson.annotations.SerializedName;

public class MainData {

    @SerializedName("id_pk")
    private String  id_pk;

    @SerializedName("title")
    private String title;

    @SerializedName("name")
    private String name;

    @SerializedName("content")
    private String content;

    public MainData(String id_pk, String title, String name, String content) {
        this.id_pk = id_pk;
        this.title = title;
        this.name = name;
        this.content = content;
    }

    public String getId_pk() {
        return id_pk;
    }

    public void setId_pk(String id_pk) {
        this.id_pk = id_pk;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
