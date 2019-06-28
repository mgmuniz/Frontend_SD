package com.example.nutrionall.api.Meal;

import com.example.nutrionall.models.Meal.Evaluate;
import com.example.nutrionall.models.Meal.Meal;
import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MealApi {
    @Multipart
    @POST("/meal")
    Call<Meal> save(
            @Part MultipartBody.Part urlImg,
            @Part("meal") Meal meal,
            @Header("Authorization") String authHeader);

    @GET("/meal/{id}")
    Call<Meal> getByID(
            @Path("id") String id,
            @Header("Authorization") String authHeader);

    @DELETE("/meal/{id}")
    Call<JsonObject> deleteByID(
            @Path("id") String id,
            @Header("Authorization") String authHeader);

    @GET("/meal")
    Call<List<Meal>> listAll(@Header("Authorization") String authHeader);

    @GET("meal/searchByName/")
    Call<List<Meal>> searchByName(
            @Query("name") String name,
            @Query("category") int category,
            @Header("Authorization") String authHeader
    );

    @POST("meal/evaluation/")
    Call<Evaluate> newEvaluate(
            @Body Evaluate body,
            @Header("Authorization") String authHeader
    );
}