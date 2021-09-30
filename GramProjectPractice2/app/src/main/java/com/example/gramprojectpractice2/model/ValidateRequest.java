package com.example.gramprojectpractice2.model;

import com.google.gson.annotations.SerializedName;

public class ValidateRequest {
    private String id;

    public String getId() {
        return id;
    }

    @SerializedName("id")
    public void setId(String id) {
        this.id = id;
    }
}
