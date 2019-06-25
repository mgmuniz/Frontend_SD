package com.example.nutrionall.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.example.nutrionall.R;
import com.example.nutrionall.api.Meal.MealApi;
import com.example.nutrionall.models.Meal.Meal;
import com.example.nutrionall.utils.Consts;
import com.example.nutrionall.utils.Methods;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RefeicoesActivity extends AppCompatActivity implements Methods {
    private String[] mImages = Consts.mImages;
    private Retrofit retrofit;

    private EditText buscaRefeicao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refeicoes);
        getReferencesComponentes();

        retrofit = Consts.connection();

        CarouselView carouselView = findViewById(R.id.CarouselRefeicoes);
//        carouselView.setPageCount(mImages.length);
        carouselView.setPageCount(5);
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                Picasso.get().load(mImages[position+5]).fit().centerCrop().into(imageView);
            }
        });


        carouselView.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
            }
        });

        CarouselView carouselFavoritesView = findViewById(R.id.CarouselRefeicoesFavoritos);
//        carouselFavoritesView.setPageCount(mImages.length);
        carouselFavoritesView.setPageCount(5);
        carouselFavoritesView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                Picasso.get().load(mImages[position+10]).fit().centerCrop().into(imageView);
            }
        });

        carouselFavoritesView.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
            }
        });
    }

    public void buscarRefeicaoHome(View view){

        Intent intent = new Intent(RefeicoesActivity.this, BuscaRefeicaoActivity.class);
        intent.putExtra("termo_busca", buscaRefeicao.getText().toString());
        intent.putExtra("modo_busca", getRadioActivate());
        startActivity(intent);
    }

    private int getRadioActivate(){
        RadioButton radioDesjejum = findViewById(R.id.radioButtonDesjejum);
        RadioButton radioAlmoco = findViewById(R.id.radioButtonAlmoco);
        RadioButton radioLanche = findViewById(R.id.radioButtonLanche);
        RadioButton radioJantar = findViewById(R.id.radioButtonJantar);

        if(radioJantar.isChecked()){return 1;}
        if(radioAlmoco.isChecked()){return 2;}
        if(radioLanche.isChecked()){return 3;}
        if(radioDesjejum.isChecked()){return 4;}

        return 1;
    }

    public void desRadioLessDesjejum(View view) {

        RadioButton radioAlmoco = findViewById(R.id.radioButtonAlmoco);
        RadioButton radioLanche = findViewById(R.id.radioButtonLanche);
        RadioButton radioJantar = findViewById(R.id.radioButtonJantar);

        radioJantar.setChecked(false);
        radioLanche.setChecked(false);
        radioAlmoco.setChecked(false);
    }

    public void desRadioLessAlmoco(View view) {

        RadioButton radioDesjejum = findViewById(R.id.radioButtonDesjejum);
        RadioButton radioLanche = findViewById(R.id.radioButtonLanche);
        RadioButton radioJantar = findViewById(R.id.radioButtonJantar);

        radioJantar.setChecked(false);
        radioLanche.setChecked(false);
        radioDesjejum.setChecked(false);
    }

    public void desRadioLessLanche(View view) {

        RadioButton radioDesjejum = findViewById(R.id.radioButtonDesjejum);
        RadioButton radioAlmoco = findViewById(R.id.radioButtonAlmoco);
        RadioButton radioJantar = findViewById(R.id.radioButtonJantar);

        radioJantar.setChecked(false);
        radioAlmoco.setChecked(false);
        radioDesjejum.setChecked(false);
    }

    public void desRadioLessJantar(View view) {

        RadioButton radioDesjejum = findViewById(R.id.radioButtonDesjejum);
        RadioButton radioAlmoco = findViewById(R.id.radioButtonAlmoco);
        RadioButton radioLanche = findViewById(R.id.radioButtonLanche);

        radioLanche.setChecked(false);
        radioAlmoco.setChecked(false);
        radioDesjejum.setChecked(false);
    }

    public void criaRefeicao(View view) {
        Intent intent = new Intent(RefeicoesActivity.this, CriarRefeicaoActivity.class);
        startActivity(intent);
    }

    public void deleteRefeicao() {
        final String TAG = "deleteRefeicao";

        String id = "5d0febc47d90bc001729957e";

        Log.d(TAG, "deleteRefeicao: " + id);

        MealApi serviceApi = retrofit.create(MealApi.class);
        Call<JsonObject> call = serviceApi.deleteByID(id, "bearer " + getPreferences().getString("token", ""));

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject resp = response.body();
                    Log.d(TAG, "onResponse: " + resp.get("msg"));
                } else {
                    Log.d(TAG, "onResponse: " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
            }
        });
    }

    public void listAll() {
        final String TAG = "listAll";

        MealApi serviceApi = retrofit.create(MealApi.class);
        Call<List<Meal>> call = serviceApi.listAll("bearer " + getPreferences().getString("token", ""));

        call.enqueue(new Callback<List<Meal>>() {
            @Override
            public void onResponse(Call<List<Meal>> call, Response<List<Meal>> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: " + response.toString());
                    List<Meal> resp = response.body();

                    for (int i = 0; i < resp.size(); i++) {
                        Log.d(TAG, "onResponse: " + resp.get(i).getName());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Meal>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
            }
        });
    }


    @Override
    public void getReferencesComponentes() {
        buscaRefeicao = findViewById(R.id.buscaRefeicao);

    }

    @Override
    public SharedPreferences getPreferences() {
        SharedPreferences preferences = getSharedPreferences(Consts.ARQUIVO_PREFERENCIAS, 0);
        return preferences;
    }
}
