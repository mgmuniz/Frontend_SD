package com.example.nutrionall.api.User;

import com.example.nutrionall.models.User.UserCadastro;

import okhttp3.MultipartBody;
import retrofit2.Call;
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
