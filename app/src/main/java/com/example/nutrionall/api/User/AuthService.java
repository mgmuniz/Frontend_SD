package com.example.nutrionall.api.User;

import com.example.nutrionall.models.User.AuthUser;
import com.example.nutrionall.models.User.UserLogin;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {
    @POST("/user/auth")
    Call<AuthUser> login(@Body UserLogin login);
    @POST("/user/auth/validateToken")
    Call<AuthUser> validateToken(@Body AuthUser user);
}