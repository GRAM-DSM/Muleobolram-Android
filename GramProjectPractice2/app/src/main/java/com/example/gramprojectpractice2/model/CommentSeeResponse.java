package com.example.gramprojectpractice2.model;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.util.List;

public class CommentSeeResponse {

    @SerializedName("comment_join")
    List<JsonObject> comment_join;

    public List<JsonObject> getComment_join() {
        return comment_join;
    }

    public void setComment_join(List<JsonObject> comment_join) {
        this.comment_join = comment_join;
    }
}
