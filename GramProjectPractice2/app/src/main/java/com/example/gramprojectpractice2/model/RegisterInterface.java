package com.example.gramprojectpractice2.model;

import com.example.gramprojectpractice2.model.RegisterRequest;
import com.example.gramprojectpractice2.model.ValidateRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegisterInterface {
    @POST("/signup")
    Call<Void> register(@Body RegisterRequest body);

    @POST("/auth")
    Call<Void> validate(@Body ValidateRequest body);
}
