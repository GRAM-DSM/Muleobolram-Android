package com.example.gramprojectpractice2.network;

import com.example.gramprojectpractice2.model.LoginRequest;
import com.example.gramprojectpractice2.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginInterface {
    @POST("/login")
    Call<LoginResponse> login(@Body LoginRequest body);
}