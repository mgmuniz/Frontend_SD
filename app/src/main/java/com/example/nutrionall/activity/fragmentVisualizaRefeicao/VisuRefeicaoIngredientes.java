package com.example.nutrionall.activity.fragmentVisualizaRefeicao;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.nutrionall.R;
import com.example.nutrionall.adapters.IngredientPlusAdapter;
import com.example.nutrionall.api.Food.FoodApi;
import com.example.nutrionall.models.Food.Food;
import com.example.nutrionall.models.Meal.Ingredient;
import com.example.nutrionall.models.Meal.IngredientPlus;
import com.example.nutrionall.models.Meal.Meal;
import com.example.nutrionall.models.User.AuthUser;
import com.example.nutrionall.utils.Consts;
import com.example.nutrionall.utils.Methods;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class VisuRefeicaoIngredientes extends Fragment implements Methods {

    private RecyclerView listVisualizaRefeicao;
    private IngredientPlusAdapter mAdapter;
    ArrayList<IngredientPlus> foods = new ArrayList<>();
    private ProgressBar progressBar33;
    private List<Ingredient> ingredients;
    private AuthUser user;
    private int disableProgressBar = 0;

    private View v;
    private Context context;
    private Retrofit retrofit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frag_visu_refeicao_ingredientes, container, false);
        retrofit = Consts.connection();
        this.context = getContext();
        final String TAG = "VisuRefeicaoIngredient";

        // deve ser chamado após a instância da view
        getReferencesComponentes();

        // recuperando os dados para mostrar no tabview
        Meal meal = (Meal) getArguments().getSerializable("meal");
        user = (AuthUser) getArguments().getSerializable("user");
        ingredients = meal.getIngredients();

        for (int i = 0; i < ingredients.size(); i++) {
            IngredientPlus aux_plus = new IngredientPlus();

            String id_ingredient = ingredients.get(i).getIdFood();
            aux_plus.setIdFood(id_ingredient);
            aux_plus.setPortion(ingredients.get(i).getPortion());
            aux_plus.setQtdPortion(ingredients.get(i).getQtdPortion());


            FoodApi serviceApi = retrofit.create(FoodApi.class);
            Call<Food> call = serviceApi.getFood(id_ingredient, "bearer " + user.getToken());

            JsonObject x = new JsonObject();
            x.addProperty("token", user.getToken());
            x.addProperty("id_ingredient", id_ingredient);
            x.addProperty("portion", ingredients.get(i).getPortion());
            x.addProperty("qtdPortion", ingredients.get(i).getQtdPortion());

            VisualizaRefeicaoIngredientesTask task = new VisualizaRefeicaoIngredientesTask();
            task.execute(x);
        }

        progressBar33 = v.findViewById(R.id.progressBar33);
        progressBar33.setVisibility(View.VISIBLE);
        return v;
    }

    private void setupRecycler(ArrayList<IngredientPlus> lstIngredients) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        listVisualizaRefeicao.setLayoutManager(layoutManager);

        // Adiciona o adapter que irá anexar os objetos à lista.
        for (int i = 1; i < lstIngredients.size(); i++) {
            Log.d("Visualizacao Ingr.", "setupRecycler: " + lstIngredients.get(i).getIdFood());
            Log.d("visualizacao", "setupRecycler: " + lstIngredients.get(i).getNameFood());
        }


        ArrayList<IngredientPlus> arrayIngredients = new ArrayList<>();
        arrayIngredients.addAll(lstIngredients);

        mAdapter = new IngredientPlusAdapter(context, arrayIngredients);


        listVisualizaRefeicao.setAdapter(mAdapter);

        // Configurando um divisor entre linhas, para uma melhor visualização.
        listVisualizaRefeicao.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL));
    }

    private Food buscarIngredientPlus(String id) {
        final String TAG = "buscarIngPlus";
        Log.d(TAG, "id do objeto " + id);

        FoodApi serviceApi = retrofit.create(FoodApi.class);
        Call<Food> call = serviceApi.getFood(id, "bearer " + user.getToken());
        final Food[] resp = new Food[1];

        call.enqueue(new Callback<Food>() {

            @Override
            public void onResponse(Call<Food> call, Response<Food> response) {
                if (user.getPremium()) {

                    if (response.isSuccessful()) {
                        // se o usuário for premium e a resposta do server for 200OK

                        resp[0] = response.body();
                        Log.d(TAG, "name: " + resp[0].getFood().getName().getValue());
                        Log.d(TAG, "category: " + resp[0].getFood().getCategory().getValue());
                        for (int i = 0; i < resp[0].getLstSimilars().size(); i++) {
                            Log.d(TAG, "similar: " + resp[0].getLstSimilars().get(i).getName().getValue());
                        }

                    } else {
                        // tratamento de erro
                    }
                } else {

                    if (response.isSuccessful()) {
                        // se o usuário NÃO for premium e a resposta do server for 200OK

                        resp[0] = response.body();
                        Log.d(TAG, "name: " + resp[0].getName().getValue());
                        Log.d(TAG, "category: " + resp[0].getCategory().getValue());

                    } else {
                        // tratamento de erro
                    }
                }
            }

            @Override
            public void onFailure(Call<Food> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
            }
        });
        return resp[0];
    }

    @Override
    public void getReferencesComponentes() {
        listVisualizaRefeicao = v.findViewById(R.id.listVisualizaRefeicao);
    }

    @Override
    public SharedPreferences getPreferences() {
        return null;
    }

    class VisualizaRefeicaoIngredientesTask extends AsyncTask<JsonObject, Call, IngredientPlus> {
        @Override
        protected IngredientPlus doInBackground(JsonObject... json) {
            JsonObject food = json[0];
            JsonElement id_ingredient = food.get("id_ingredient");
            JsonElement token = food.get("token");

            FoodApi serviceApi = retrofit.create(FoodApi.class);
            Call<Food> call = serviceApi.getFood(id_ingredient.getAsString(), "bearer " + token.getAsString());

            Food resp = null;
            try {
                resp = call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }

            IngredientPlus x = new IngredientPlus();
            x.setIdFood(id_ingredient.getAsString());
            x.setPortion(food.get("portion").getAsString());
            x.setQtdPortion(food.get("qtdPortion").getAsString());
            x.setNameFood(resp.getFood().getName().getValue());
            x.setCategory(resp.getFood().getCategory().getValue());

            return x;
        }

        @Override
        protected void onPostExecute(IngredientPlus ingredientPlus) {
            super.onPostExecute(ingredientPlus);
            disableProgressBar++;
            foods.add(ingredientPlus);
            // if para desativar o progress bar
            if (disableProgressBar >= ingredients.size()) {
                progressBar33.setVisibility(View.INVISIBLE);
            }
            setupRecycler(foods);
        }
    }
}
