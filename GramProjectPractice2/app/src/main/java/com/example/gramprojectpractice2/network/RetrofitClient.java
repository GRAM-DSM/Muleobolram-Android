package com.example.gramprojectpractice2.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private final String baseURI = "http://13.209.87.43:5000"; //서버 배포되면 나오는 주소 넣기

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(baseURI)
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    public GramAPI getRetrofit() {
        return retrofit.create(GramAPI.class);
    }
    public Retrofit getRetrofit1(){
        return retrofit;
    }
}
