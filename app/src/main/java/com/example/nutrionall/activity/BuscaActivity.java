package com.example.nutrionall.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;

import com.example.nutrionall.R;
import com.example.nutrionall.adapters.FoodAdapter;
import com.example.nutrionall.api.Food.FoodApi;
import com.example.nutrionall.api.Food.Search;
import com.example.nutrionall.models.Food.Food;
import com.example.nutrionall.models.Food.SearchNutrientFood;
import com.example.nutrionall.utils.Consts;
import com.example.nutrionall.utils.Methods;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BuscaActivity extends AppCompatActivity implements Methods {

    private EditText editBuscaBusca;
    private Retrofit retrofit;
    private ListView resultadosBusca;
    private RadioButton radioAlimento;
    private RadioButton radioNutrient;

    private ArrayList<Food> arrayAlimentos;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca);
        getReferencesComponentes();
        retrofit = Consts.connection();
        this.context = this;

        Intent intent = getIntent();
        String termo_busca = intent.getStringExtra("termo_busca");

        editBuscaBusca.setText(termo_busca);
        buscar(null);

        resultadosBusca.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Food alimento_clicado = arrayAlimentos.get(position);
                alimento_clicado = buscarAlimento(null,alimento_clicado.get_id());
//                Log.d("Despair2",alimento_clicado.get_id());


                Intent intent = new Intent(BuscaActivity.this, InfoAlimentosActivity.class);
                intent.putExtra("food", alimento_clicado);
                startActivity(intent);
            }
        });
    }

    public void buscar(View view) {
        final String TAG = "search";
        SharedPreferences preferences = getSharedPreferences(Consts.ARQUIVO_PREFERENCIAS, 0);
        String query = editBuscaBusca.getText().toString();

        Log.d(TAG, "buscar: " + query);
//        buscarAlimento(view);
//        buscarNutriente(view);

        JsonObject food = new JsonObject();
        food.addProperty("name", query);

        Search serviceApi = retrofit.create(Search.class);
        Call<List<Food>> call = serviceApi.searchByName(food, "bearer " + getPreferences().getString("token", ""));

        call.enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                if (response.isSuccessful()) {
                    List<Food> list;
                    list = response.body();

//                    for(int i = 0; i < list.size(); i++){
//                        Log.d(TAG, "id:" + list.get(i).get_id());
//                    }

                    arrayAlimentos = new ArrayList<>();
                    arrayAlimentos.addAll(list);

                    ArrayAdapter adapter = new FoodAdapter(context, arrayAlimentos);
                    resultadosBusca.setAdapter(adapter);

                } else {
                    Log.d(TAG, "onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
            }
        });
    }


    public SharedPreferences getPreferences() {
        SharedPreferences preferences = getSharedPreferences(Consts.ARQUIVO_PREFERENCIAS, 0);
        return preferences;
    }

    public Food buscarAlimento(View view, String id) {
        final String TAG = "searchFood";
        id = "5cf845fe245413a64b40500e";
        Log.d(TAG, "buscarAlimento: " + id);

        FoodApi serviceApi = retrofit.create(FoodApi.class);
        Call<Food> call = serviceApi.getFood(id, "bearer " + getPreferences().getString("token", ""));
        final Food[] food_result = {null};


        call.enqueue(new Callback<Food>() {
            @Override
            public void onResponse(Call<Food> call, Response<Food> response) {
                Log.d(TAG, "Despair: " + "Caiu aqui");
                if (getPreferences().getBoolean("isPremium", false)) {

                    if (response.isSuccessful()) {
                        // se o usuário for premium e a resposta do server for 200OK

                        Food resp = response.body();
                        Log.d(TAG, "name: " + resp.getFood().getName().getValue());
                        Log.d(TAG, "category: " + resp.getFood().getCategory().getValue());
                        for (int i = 0; i < resp.getLstSimilars().size(); i++) {
                            Log.d(TAG, "similar: " + resp.getLstSimilars().get(i).getName().getValue());
                        }

                        food_result[0] = resp;

                    } else {

                        // tratamento de erro
                    }
                } else {

                    if (response.isSuccessful()) {
                        // se o usuário NÃO for premium e a resposta do server for 200OK

                        Food resp = response.body();
                        Log.d(TAG, "name: " + resp.getName().getValue());
                        Log.d(TAG, "category: " + resp.getCategory().getValue());
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

        return food_result[0];
    }

    public void buscarNutriente(View view) {
        final String TAG = "searchNutrient";

        // nutriente q deve ser alterado
        String nutrient = "humidity";

        Log.d(TAG, "buscarNutriente: " + nutrient);

        Search serviceApi = retrofit.create(Search.class);
        Call<SearchNutrientFood> call = serviceApi.searchByNutrient(
                1, // manipulação necessária para recuperar os demais alimentos
                10, // o limite varia de acordo com a interface
                "desc",
                nutrient,
                "bearer " + getPreferences().getString("token", ""));

        call.enqueue(new Callback<SearchNutrientFood>() {
            @Override
            public void onResponse(Call<SearchNutrientFood> call, Response<SearchNutrientFood> response) {
                if (getPreferences().getBoolean("isPremium", false)) {
                    if (response.isSuccessful()) {
                        // se o usuário for premium
                        SearchNutrientFood resp = response.body();
                        Log.d(TAG, "page: " + resp.getPage());
                        Log.d(TAG, "limit: " + resp.getLimit());
                        for (int i = 0; i < resp.getLimit(); i++) {
                            Log.d(TAG, "data: " + resp.getData().get(i).getName().getValue());
                        }
                    } else {
                        Log.d(TAG, Consts.falhaReq(response.code(), response.message(), response.raw().toString()));
                    }
                } else {
                    if (response.isSuccessful()) {
                        // se o usuário não for premium
                    } else {
                        Log.d(TAG, Consts.falhaReq(response.code(), response.message(), response.raw().toString()));
                    }
                }
            }

            @Override
            public void onFailure(Call<SearchNutrientFood> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
            }
        });
    }


    public void desRadioAlimento(View view) {
        radioAlimento.setChecked(false);
    }

    public void desRadioNutriente(View view) {
        radioNutrient.setChecked(false);
    }

    public void getReferencesComponentes() {
        editBuscaBusca = findViewById(R.id.editBuscaBusca);
        resultadosBusca = findViewById(R.id.listBuscaResults);
        radioAlimento = findViewById(R.id.radioBuscaAlimento);
        radioNutrient = findViewById(R.id.radioBuscaNutriente);

    }
}
