package com.example.nutrionall.utils;

import android.content.SharedPreferences;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Consts {
    public static final String API_BASE_URL = "https://backend-sd.herokuapp.com/";
    public static final String ARQUIVO_PREFERENCIAS = "NutritionAllArquivoPreferencias";

    public static Retrofit connection() {
        return new Retrofit.Builder()
                .baseUrl(Consts.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
