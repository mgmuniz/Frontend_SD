package com.example.nutrionall.api.Food;

import com.example.nutrionall.models.Food.Food;
import com.example.nutrionall.models.Food.SearchNutrientFood;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Search {
    @Headers("Content-Type: application/json")
    @POST("/food/search/name/")
    Call<List<Food>> searchByName(@Body JsonObject food, @Header("Authorization") String authHeader);

    @GET("/food/search/nutrient/")
    Call<SearchNutrientFood> searchByNutrient(
            @Query("page") int page,
            @Query("limit") int limit,
            @Query("sort") String sort,
            @Query("nutrient") String nutrient,
            @Header("Authorization") String authHeader
    );
}
