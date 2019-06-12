package com.example.nutrionall.api;

import com.example.nutrionall.models.AuthUser;
import com.example.nutrionall.models.UserLogin;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {
    @POST("/user/auth")
    Call<AuthUser> login(@Body UserLogin login);
}