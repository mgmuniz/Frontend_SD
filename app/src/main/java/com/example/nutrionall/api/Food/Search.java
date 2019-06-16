package com.example.nutrionall.api.Food;

import com.example.nutrionall.models.Food.Food;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Search {
    @POST("/food/search/name/")
    Call<List<Food>> searchByName(@Body Food food, @Header("Authorization") String authHeader);
}
