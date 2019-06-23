package com.example.nutrionall.api.Meal;

import com.example.nutrionall.models.Meal.Meal;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

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
}