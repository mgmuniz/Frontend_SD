package com.example.nutrionall.api.Meal;

import com.example.nutrionall.models.Meal.Evaluate;
import com.example.nutrionall.models.Meal.Favorite;
import com.example.nutrionall.models.Meal.Meal;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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
    Call<List<Meal>> listAllMeal(
            @Header("Authorization") String authHeader
    );

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

    @GET("meal/evaluation/{id}")
    Call<JsonObject> getEvaluation(
            @Path("id") String id,
            @Header("Authorization") String authHeader
    );

    @PUT("meal/evaluation/{id}")
    Call<JsonObject> changeEvaluation(
            @Path("id") String id,
            @Body JsonObject body,
            @Header("Authorization") String authHeader
    );

    @POST("meal/favorite/")
    Call<JsonObject> newFavorite(
            @Body JsonObject body,
            @Header("Authorization") String authHeader
    );

    @GET("meal/favorite/{id}")
    Call<JsonObject> getFavorite(
            @Path("id") String id,
            @Header("Authorization") String authHeader
    );

    @DELETE("meal/favorite/{id}")
    Call<JsonObject> removeFavorite(
            @Path("id") String id,
            @Header("Authorization") String authHeader
    );

    @GET("meal/favorite/listAll")
    Call<ArrayList<Favorite>> listAllFavorite(
            @Header("Authorization") String authHeader
    );
}