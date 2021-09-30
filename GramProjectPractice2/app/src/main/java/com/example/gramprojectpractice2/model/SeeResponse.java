package com.example.gramprojectpractice2.model;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class SeeResponse {

    @SerializedName("posts")
    List<JsonObject> getPost;

    public List<JsonObject> getGetPost() {
        return getPost;
    }

    public void setGetPost(List<JsonObject> getPost) {
        this.getPost = getPost;
    }
}