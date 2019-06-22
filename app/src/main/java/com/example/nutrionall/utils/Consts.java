package com.example.nutrionall.utils;

import com.example.nutrionall.models.Food.Food;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Consts {
    public static final String API_BASE_URL = "https://backend-sd.herokuapp.com";
    public static final String ARQUIVO_PREFERENCIAS = "NutritionAllArquivoPreferencias";
    public static Food foodParameter;

    public static Retrofit connection() {
        return new Retrofit.Builder()
                .baseUrl(Consts.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static String falhaReq(int code, String msg, String raw) {
        return "Erro na requisição code: " + code + "\n" +
                "msg: " + msg + "\n" +
                "content: " + msg + "\n";
    }

    public static String[] mImages = new String[]{
            "https://nit.pt/wp-content/uploads/2017/12/840b2ccfec7171179aac43a1f6919c11-754x394.jpg",
            "https://nit.pt/wp-content/uploads/2017/12/c4ca4238a0b923820dcc509a6f75849b-10-754x394.jpg",
            "https://nit.pt/wp-content/uploads/2019/06/3db9f34c117da413eb2de25c06547fe8-754x394.jpg",
            "https://nit.pt/wp-content/uploads/2019/06/a0ea12235f3b00b1ace614e143be0eb2-1-754x394.jpg",
            "https://nit.pt/wp-content/uploads/2019/06/1a051d4e6c04508055d43a8f4f5cb313-754x394.jpg"
    };

    public static int getClassification(String classification){
        switch (classification){
            case "Jantar":
                return 1;
            case "Almoço":
                return 2;
            case "Lanche":
                return 3;
            default:
                return 4; // café da manhã
        }
    }
}
