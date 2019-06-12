package com.example.nutrionall.api;

import com.example.nutrionall.models.UserCadastro;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {
    // cadastro de usuário
    @POST("/user")
    Call<UserCadastro> cadastrar(@Body UserCadastro user);
}
