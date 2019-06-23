package com.example.nutrionall.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.example.nutrionall.R;
import com.example.nutrionall.api.Food.Search;
import com.example.nutrionall.api.Meal.MealApi;
import com.example.nutrionall.models.Food.Food;
import com.example.nutrionall.models.Meal.Meal;
import com.example.nutrionall.utils.Consts;
import com.example.nutrionall.utils.Methods;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refeicoes);

        retrofit = Consts.connection();

        CarouselView carouselView = findViewById(R.id.CarouselRefeicoes);
        carouselView.setPageCount(mImages.length);
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                Picasso.get().load(mImages[position]).fit().centerCrop().into(imageView);
            }
        });


        carouselView.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
            }
        });

        CarouselView carouselFavoritesView = findViewById(R.id.CarouselRefeicoesFavoritos);
        carouselFavoritesView.setPageCount(mImages.length);
        carouselFavoritesView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                Picasso.get().load(mImages[position]).fit().centerCrop().into(imageView);
            }
        });

        carouselFavoritesView.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
            }
        });

        getRefeicao();
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

    public void criaRefeicao(View view){
        Intent intent = new Intent(RefeicoesActivity.this, CriarRefeicaoActivity.class);
        startActivity(intent);
    }

    public void getRefeicao(){
        final String TAG = "getRefeição";

        String id = "5d0bdec9bcccc223ae26c8fb";

        Log.d(TAG, "getRefeição: " + id);

        MealApi serviceApi = retrofit.create(MealApi.class);
        Call<Meal> call = serviceApi.getByID(id, "bearer " + getPreferences().getString("token", ""));

        call.enqueue(new Callback<Meal>() {
            @Override
            public void onResponse(Call<Meal> call, Response<Meal> response) {
                if(getPreferences().getBoolean("isPremium", false)){
                    if(response.isSuccessful()){
                        // o usuário é premium
                        Meal resp = response.body();
                    }else{
                        Log.d(TAG, "onResponse: erro");
                    }
                }else{
                    if(response.isSuccessful()){
                        // o usuário não é premium
                        Meal resp = response.body();
                    }
                }
            }

            @Override
            public void onFailure(Call<Meal> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
            }
        });
    }

    @Override
    public void getReferencesComponentes() {

    }

    @Override
    public SharedPreferences getPreferences() {
        SharedPreferences preferences = getSharedPreferences(Consts.ARQUIVO_PREFERENCIAS, 0);
        return preferences;
    }
}
