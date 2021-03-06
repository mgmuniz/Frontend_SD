package com.example.nutrionall.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nutrionall.R;
import com.example.nutrionall.api.Meal.MealApi;
import com.example.nutrionall.models.Meal.Evaluate;
import com.example.nutrionall.models.Meal.Favorite;
import com.example.nutrionall.models.Meal.Meal;
import com.example.nutrionall.utils.Consts;
import com.example.nutrionall.utils.Methods;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RefeicoesActivity extends AppCompatActivity implements Methods {
    private String[] mImages = Consts.mImages;
    private Retrofit retrofit;
    private List<Meal> topMeals;
    private ArrayList<Favorite> topFavorites;

    private EditText buscaRefeicao;
    private FloatingActionButton buttonCadastrarRefeicao;
    private CarouselView CarouselRefeicoesFavoritos;
    private TextView textViewFavoritosRefeicoes;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refeicoes);
        getReferencesComponentes();

        retrofit = Consts.connection();

//        CarouselRefeicoesFavoritos.setPageCount(5);
//        CarouselRefeicoesFavoritos.setImageListener(new ImageListener() {
//            @Override
//            public void setImageForPosition(int position, ImageView imageView) {
//                Picasso.get().load(mImages[position+10]).fit().centerCrop().into(imageView);
//            }
//        });
//
//        CarouselRefeicoesFavoritos.setImageClickListener(new ImageClickListener() {
//            @Override
//            public void onClick(int position) {
//            }
//        });

        if (!getPreferences().getBoolean("isPremium", false)) {
            buttonCadastrarRefeicao.setVisibility(View.INVISIBLE);
            CarouselRefeicoesFavoritos.setVisibility(View.INVISIBLE);
            textViewFavoritosRefeicoes.setVisibility(View.INVISIBLE);
        }

//        listAllFavorite();
        ListAllMealsUserTask task = new ListAllMealsUserTask();
        task.execute("bearer " + getPreferences().getString("token",""));

        ListAllFavoriteTask task2 = new ListAllFavoriteTask();
        task2.execute("bearer " + getPreferences().getString("token",""));
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

    public void listAllMeal() {
        final String TAG = "listAll";

        MealApi serviceApi = retrofit.create(MealApi.class);
        Call<List<Meal>> call = serviceApi.listAllMeal("bearer " + getPreferences().getString("token", ""));

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

    public void listAllFavorite(){
        final String TAG = "listAllFavorite";

        MealApi serviceApi = retrofit.create(MealApi.class);
        Call<ArrayList<Favorite>> call = serviceApi.listAllFavorite("bearer " + getPreferences().getString("token", ""));

        call.enqueue(new Callback<ArrayList<Favorite>>() {
            @Override
            public void onResponse(Call<ArrayList<Favorite>> call, Response<ArrayList<Favorite>> response) {
                Log.d(TAG, "onResponse: " + response.toString());
                if(response.isSuccessful()){
                    ArrayList<Favorite> resp = response.body();
                    for(int i = 0; i < resp.size(); i++){
                        Log.d(TAG, "onResponse: " + resp.get(i).getName());
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Favorite>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
            }
        });
    }

    @Override
    public void getReferencesComponentes() {
        buscaRefeicao = findViewById(R.id.buscaRefeicao);
        buttonCadastrarRefeicao = findViewById(R.id.buttonCadastrarRefeicao);
        CarouselRefeicoesFavoritos = findViewById(R.id.CarouselRefeicoesFavoritos);
        textViewFavoritosRefeicoes = findViewById(R.id.textViewFavoritosRefeicoes);
    }

    @Override
    public SharedPreferences getPreferences() {
        SharedPreferences preferences = getSharedPreferences(Consts.ARQUIVO_PREFERENCIAS, 0);
        return preferences;
    }

    class ListAllMealsUserTask extends AsyncTask<String, Call, List<Meal>> {
        String TAG = "listAllMealsUserTask";

        @Override
        protected List<Meal> doInBackground(String... strings) {
            MealApi serviceApi = retrofit.create(MealApi.class);
            Call<List<Meal>> call = serviceApi.listAllMealUser(strings[0]);
            Response<List<Meal>> x = null;

            try {
                x = call.execute();
                Log.d(TAG, "doInBackground: " + x.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return x.body();
        }

        class SortByAvgEvaluation implements Comparator<Meal>
        {
            // Used for sorting in ascending order of
            // roll number
            public int compare(Meal a, Meal b)
            {
                return (int) (b.getAvgEvaluation() - a.getAvgEvaluation());
            }
        }

        @Override
        protected void onPostExecute(List<Meal> meals) {
            super.onPostExecute(meals);
            topMeals = meals;

            Log.d(TAG, "onPostExecute: " + topMeals.size());

            Collections.sort(topMeals, new SortByAvgEvaluation());

            for(int i = 0; i < topMeals.size(); i++){
                Log.d(TAG, "onPostExecute: " + topMeals.get(i).getAvgEvaluation());
            }

            CarouselView carouselView = findViewById(R.id.CarouselRefeicoes);

            carouselView.setImageListener(new ImageListener() {
                @Override
                public void setImageForPosition(int position, ImageView imageView) {
                    Picasso.get().load(topMeals.get(position).getUrlImg()).fit().centerCrop().into(imageView);
                }
            });
            carouselView.setImageClickListener(new ImageClickListener() {
                @Override
                public void onClick(int position) {
                    Intent intent = new Intent(getApplicationContext(), VisualizaRefeicaoActivity.class);
                    intent.putExtra("meal", topMeals.get(position));
                    startActivity(intent);
                }
            });

            if (topMeals.size() < 7) {
                carouselView.setPageCount(topMeals.size());
            } else {
                carouselView.setPageCount(7);
            }

        }
    }

    class ListAllFavoriteTask extends AsyncTask<String, Call, ArrayList<Favorite>> {
        String TAG = "ListAllFavoriteTask";

        @Override
        protected ArrayList<Favorite> doInBackground(String... strings) {
            MealApi serviceApi = retrofit.create(MealApi.class);
            Call<ArrayList<Favorite>> call = serviceApi.listAllFavorite(strings[0]);
            Response<ArrayList<Favorite>> x = null;

            try {
                x = call.execute();
                Log.d(TAG, "doInBackground: " + x.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return x.body();
        }


        @Override
        protected void onPostExecute(ArrayList<Favorite> meals) {
            super.onPostExecute(meals);
            topFavorites = meals;

            Log.d(TAG, "onPostExecute: " + topFavorites.size());

            CarouselView carouselView = findViewById(R.id.CarouselRefeicoesFavoritos);

            carouselView.setImageListener(new ImageListener() {
                @Override
                public void setImageForPosition(int position, ImageView imageView) {
                    Picasso.get().load(topFavorites.get(position).getUrlImg()).fit().centerCrop().into(imageView);
                }
            });

            carouselView.setImageClickListener(new ImageClickListener() {
                @Override
                public void onClick(int position) {
                    // bloco para correção de erro
                    //https://stackoverflow.com/questions/25093546/android-os-networkonmainthreadexception-at-android-os-strictmodeandroidblockgua
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                            .permitAll().build();
                    StrictMode.setThreadPolicy(policy);

                    MealApi serviceApi = retrofit.create(MealApi.class);
                    Call<Meal> call = serviceApi.getByID(topFavorites.get(position).getIdMeal(),"bearer " +getPreferences().getString("token",""));
                    Response<Meal> x = null;

                    try {
                        x = call.execute();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(getApplicationContext(), VisualizaRefeicaoActivity.class);
                    intent.putExtra("meal", x.body());
                    startActivity(intent);
                }
            });

            if (topFavorites.size() < 7) {
                carouselView.setPageCount(topFavorites.size());
            } else {
                carouselView.setPageCount(7);
            }

        }
    }
}
