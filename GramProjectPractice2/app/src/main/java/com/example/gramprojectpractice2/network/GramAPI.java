package com.example.gramprojectpractice2.network;

import android.util.JsonReader;

import com.example.gramprojectpractice2.model.CommentSeeResponse;
import com.example.gramprojectpractice2.model.CommentWriteResponse;
import com.example.gramprojectpractice2.model.MainData;
import com.example.gramprojectpractice2.model.SeeResponse;
import com.example.gramprojectpractice2.model.WriteRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GramAPI {

    @POST("/post")
    Call<Void> write(
            @Header("Authorization") String header,
            @Body WriteRequest body
    );

    @GET("/post")
    Call<SeeResponse> seePost(
            @Header("Authorization") String header
    );

    @DELETE("/post/{id}")
    Call<Void> deletePost(
            @Header("Authorization") String header,
            @Path("id") Integer id //int or Integer
            );

    @POST("/comment")
    Call<Void> writeComment(
            @Header("Authorization") String header,
            @Body CommentWriteResponse body,
            @Query("post_id") Integer id
            );

    @GET("/comment")
    Call<CommentSeeResponse> seeComment(
            @Header("Authorization") String header,
            @Query("post_id") Integer id
    );
}
