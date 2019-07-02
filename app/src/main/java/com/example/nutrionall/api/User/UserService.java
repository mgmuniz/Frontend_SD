package com.example.nutrionall.api.User;

import com.example.nutrionall.models.User.User;
import com.example.nutrionall.models.User.UserCadastro;
import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface UserService {
    // cadastro de usuário
    @Multipart
    @POST("/user")
    Call<UserCadastro> cadastrar(
            @Part MultipartBody.Part urlImg,
            @Part("user") UserCadastro user
    );

    @PUT("/user/{id}")
    Call<JsonObject> alterarDados(
        @Path("id") String id,
        @Body User body,
        @Header("Authorization") String authHeader
    );
}
