package com.example.nutrionall.activity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.nutrionall.R;
import com.example.nutrionall.activity.adapterCriaRefeicao.MyFragPageAdapterCriaRefeicao;
import com.example.nutrionall.api.Meal.MealApi;
import com.example.nutrionall.models.Meal.Ingredient;
import com.example.nutrionall.models.Meal.Meal;
import com.example.nutrionall.utils.Consts;
import com.example.nutrionall.utils.Methods;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CriarRefeicaoActivity extends AppCompatActivity implements Methods {

    private Retrofit retrofit;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ImageView editCadastroImgMeal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_refeicao);

        getReferencesComponentes();
        // estabelece comunicação com a api
        retrofit = Consts.connection();

//        cadastrar(null);

        mViewPager.setAdapter(new MyFragPageAdapterCriaRefeicao(getSupportFragmentManager(), getResources().getStringArray(R.array.titles_tab)));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private Meal getMealData(){
        Meal newMeal = new Meal();

        ArrayList<Ingredient> ingredients = new ArrayList<>();
        Ingredient x1 = new Ingredient();
        x1.setIdFood("5cf845fe245413a64b40500e");
        x1.setPortion("50");
        x1.setQtdPortion("3");
        Ingredient x2 = new Ingredient();
        x2.setIdFood("5cf845fe245413a64b404fad");
        x2.setPortion("50");
        x2.setQtdPortion("3");

        ingredients.add(x1);
        ingredients.add(x2);

        newMeal.setName("Refeição teste Android 1");
        newMeal.setDescription("Apenas uma descrição");
        newMeal.setVisibility(true);
        newMeal.setClassification(Consts.getClassification("Jantar"));
        newMeal.setIngredients(ingredients);

        return newMeal;
    }

    public void cadastrar(View view) {
        final String TAG = "cadastrar Refeição";

        Log.d(TAG, "cadastrar: ");
        // Monta os dados para envio
        Meal newMeal = getMealData();

        // Cria o multpart file de envio para imagem
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "urlImg");
        MultipartBody.Part urlImg = null;
        try {
            urlImg = multipart();
        } catch (IOException e) {
            e.printStackTrace();
        }

        MealApi serviceApi = retrofit.create(MealApi.class);
        Call<Meal> call = serviceApi.save(urlImg, newMeal, "bearer " + getPreferences().getString("token", ""));

        call.enqueue(new Callback<Meal>() {
            @Override
            public void onResponse(Call<Meal> call, Response<Meal> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "onResponse: " + response.message());
                }else{
                    Log.d(TAG, "onResponse: " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<Meal> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
            }
        });
    }

    private byte[] imgToString() {
        editCadastroImgMeal.setDrawingCacheEnabled(true);
        editCadastroImgMeal.buildDrawingCache();

        // recupera o bitmap da imagem a ser enviada
        Bitmap bitmap = editCadastroImgMeal.getDrawingCache();

        // comprime o bitmap para algum formato de imagem
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        // converte o byteArrayOutputStream em array
        byte[] imgByte = byteArrayOutputStream.toByteArray();

        return imgByte;
    }

    private MultipartBody.Part multipart() throws IOException {
//        File filesDir = getApplicationContext().getFilesDir();
//        File file = new File(filesDir, "imageMeal" + ".png");
//
//        byte[] img = imgToString();
//
//        FileOutputStream fos = new FileOutputStream(file);
//        fos.write(img);
//        fos.flush();
//        fos.close();
//
//        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
//        MultipartBody.Part body = MultipartBody.Part.createFormData("urlImg", file.getName(), reqFile);

        // return body;
        return null;
    }

    @Override
    public void getReferencesComponentes() {
        // inicializa variáveis dos componentes
        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager);
        //editCadastroImgMeal = findViewById(R.id.editCadastroImgMeal);
    }

    @Override
    public SharedPreferences getPreferences() {
        SharedPreferences preferences = getSharedPreferences(Consts.ARQUIVO_PREFERENCIAS, 0);
        return preferences;
    }
}
