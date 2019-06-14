package com.example.nutrionall.api;

import com.example.nutrionall.models.UserCadastro;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UserService {
    // cadastro de usuário
    @Multipart
    @POST("/user")
    Call<UserCadastro> cadastrar(
            @Part MultipartBody.Part urlImg,
            @Part("user") UserCadastro user
    );
}
