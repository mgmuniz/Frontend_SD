package com.example.nutrionall.api.Food;

import com.example.nutrionall.models.Food.Food;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Search {
    @Headers("Content-Type: application/json")
    @POST("/food/search/name/")
    Call<List<Food>> searchByName(@Body JsonObject food, @Header("Authorization") String authHeader);
}
