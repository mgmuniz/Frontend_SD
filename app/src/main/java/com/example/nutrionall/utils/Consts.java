package com.example.nutrionall.utils;

import com.example.nutrionall.models.Food.Food;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Consts {
    public static final String API_BASE_URL = "https://backend-sd.herokuapp.com";
    public static final String ARQUIVO_PREFERENCIAS = "NutritionAllArquivoPreferencias";
    public static Map<String, String> nutrients;
    private static Boolean flagNutrient = false;
    public static boolean flagFavorite = false;

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
            "https://nit.pt/wp-content/uploads/2019/06/1a051d4e6c04508055d43a8f4f5cb313-754x394.jpg",

            "https://4.bp.blogspot.com/-L3FXcJ7aHxA/UpfUwIO56EI/AAAAAAAAAQ8/47urGdL544c/s1600/alimenta%C3%A7%C3%A3o-saud%C3%A1vel.jpg",
            "http://file-service.riooportunidadesdenegocios.com.br/images/741x371/f15398b7-0de7-493a-af43-19f9e82caa36.jpg",
            "https://www.fdmoficial.com.br/wp/wp-content/uploads/2017/07/aoe.jpg",
            "https://www.fdmoficial.com.br/wp/wp-content/uploads/2017/07/Blog_170515_02.png",
            "https://www.fdmoficial.com.br/wp/wp-content/uploads/2017/07/dietasreceitaspraticasblogspot-Shake-de-Aveia-e-Linha%C3%A7a.jpg",

            "https://www.fdmoficial.com.br/wp/wp-content/uploads/2017/06/anab%C3%B3licos.jpg",
            "https://www.fdmoficial.com.br/wp/wp-content/uploads/2017/03/time-965184_1280.jpg",
            "https://s1.narvii.com/image/gv7drcwittpoxor7cajyegru3sgdumrb_hq.jpg",
            "https://s1.narvii.com/image/ry7qtqozaoend2yf3yr5clryzezrkgx6_hq.jpg",
            "https://s1.narvii.com/image/bwlctbqk2mx66huswau2d73kgnxrdjyb_hq.jpg"
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

    public static void generateNutrient(){
        nutrients = new HashMap<String, String>();
        nutrients.put("Umidade", "humidity");
        nutrients.put("Energia", "energy");
        nutrients.put("Proteína", "protein");
        nutrients.put("Lipídeos", "lipids");
        nutrients.put("Colesterol", "cholesterol");
        nutrients.put("Carboidrato", "carbohydrate");
        nutrients.put("Fibra Alimentar", "food fiber");
        nutrients.put("Cinzas", "ashes");
        nutrients.put("Cálcio", "calcium");
        nutrients.put("Magnésio", "magnesium");
        nutrients.put("Manganês", "manganese");
        nutrients.put("Fósforo", "phosphorus");
        nutrients.put("Ferro", "iron");
        nutrients.put("Sódio", "sodium");
        nutrients.put("Potássio", "potassium");
        nutrients.put("Cobre", "copper");
        nutrients.put("Zinco", "zinc");
        nutrients.put("Retinol", "retinol");
        nutrients.put("RE", "re");
        nutrients.put("RAE", "rae");
        nutrients.put("Tiamina", "thiamine");
        nutrients.put("Riboflavina", "riboflavin");
        nutrients.put("Piridoxina", "pyridoxine");
        nutrients.put("Niacina", "niacin");
        nutrients.put("Vitamina C", "vitamin c");
        flagNutrient = true;
    }

    public static String getNutrient(String name){
        if(flagNutrient == false){
            generateNutrient();
            flagNutrient = true;
        }

        return nutrients.get(name);
    }
}
