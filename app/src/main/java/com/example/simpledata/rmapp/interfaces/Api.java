package com.example.simpledata.rmapp.interfaces;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface Api {
    String BASE_URL = "https://rickandmortyapi.com/api/";

    @GET("character")
    Call<ResponseBody> getCharactersList();

    @GET
    Call<ResponseBody> getCharacterDetail(@Url String url);
}
