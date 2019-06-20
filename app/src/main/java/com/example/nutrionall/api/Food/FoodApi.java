package com.example.nutrionall.api.Food;

import com.example.nutrionall.models.Food.Food;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface FoodApi {
    @GET("/food/{id}")
    Call<Food> getFood(@Path("id") String id, @Header("Authorization") String authHeader);
}
