package com.example.nutrionall.activity.fragmentVisualizaRefeicao;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nutrionall.R;
import com.example.nutrionall.activity.InfoAlimentosActivity;
import com.example.nutrionall.adapters.IngredientPlusAdapter;
import com.example.nutrionall.adapters.SimilaresAdapter;
import com.example.nutrionall.api.Food.FoodApi;
import com.example.nutrionall.models.Food.Food;
import com.example.nutrionall.models.Meal.Ingredient;
import com.example.nutrionall.models.Meal.IngredientPlus;
import com.example.nutrionall.models.Meal.Meal;
import com.example.nutrionall.utils.Consts;
import com.example.nutrionall.utils.Methods;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class VisuRefeicaoIngredientes extends Fragment implements Methods {

    private RecyclerView listVisualizaRefeicao;
    private IngredientPlusAdapter mAdapter;

    private View v;
    private Context context;
    private Retrofit retrofit;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frag_visu_refeicao_ingredientes, container, false);
        retrofit = Consts.connection();
        this.context = context;

        // deve ser chamado após a instância da view
        getReferencesComponentes();

        // recuperando os dados para mostrar no tabview
        Meal meal = (Meal) getArguments().getSerializable("meal");

        ArrayList<IngredientPlus> ingredientsPlus = new ArrayList<IngredientPlus>();
        List<Ingredient> ingredients = meal.getIngredients();

        for(int i=0; i<ingredients.size(); i++){
            IngredientPlus aux_plus = new IngredientPlus();

            String id_ingredient = ingredients.get(i).getIdFood();
            aux_plus.setIdFood(id_ingredient);
            aux_plus.setPortion(ingredients.get(i).getPortion());
            aux_plus.setQtdPortion(ingredients.get(i).getQtdPortion());
            
            Food alimento_ingrediente = buscarIngredientPlus(id_ingredient);

            if (getPreferences().getBoolean("isPremium", false)) {

                aux_plus.setNameFood(alimento_ingrediente.getFood().getName().getValue());
                aux_plus.setCategory(alimento_ingrediente.getFood().getCategory().getValue());
            }else{
                aux_plus.setNameFood(alimento_ingrediente.getName().getValue());
                aux_plus.setCategory(alimento_ingrediente.getCategory().getValue());
            }

            ingredientsPlus.add(aux_plus);
        }
        setupRecycler(ingredientsPlus);
        return v;
    }

    private void setupRecycler(ArrayList<IngredientPlus> lstIngredients) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        listVisualizaRefeicao.setLayoutManager(layoutManager);

        // Adiciona o adapter que irá anexar os objetos à lista.

        for (int i = 0; i < lstIngredients.size(); i++) {
            Log.d("Visualizacao Ingr.", "setupRecycler: " + lstIngredients.get(i).getIdFood());
        }

        ArrayList<IngredientPlus> arrayIngredients = new ArrayList<>();
        arrayIngredients.addAll(lstIngredients);

        mAdapter = new IngredientPlusAdapter(context, arrayIngredients);


        listVisualizaRefeicao.setAdapter(mAdapter);

        // Configurando um divisor entre linhas, para uma melhor visualização.
        listVisualizaRefeicao.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));

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
                if (getPreferences().getBoolean("isPremium", false)) {

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
}
