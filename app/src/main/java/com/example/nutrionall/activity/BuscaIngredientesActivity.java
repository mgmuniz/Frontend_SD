package com.example.nutrionall.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;

import com.example.nutrionall.R;
import com.example.nutrionall.adapters.BuscaIngredienteAdapter;
import com.example.nutrionall.adapters.FoodAdapter;
import com.example.nutrionall.api.Food.FoodApi;
import com.example.nutrionall.api.Food.Search;
import com.example.nutrionall.models.Food.Food;
import com.example.nutrionall.utils.Consts;
import com.example.nutrionall.utils.Methods;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BuscaIngredientesActivity extends AppCompatActivity implements Methods {

    private EditText editBuscaIngredienteBusca;
    private Retrofit retrofit;
    private ListView listBuscaIngredienteResults;
    private Food alimento_clicado;
    private ImageView imgBuscaIngredienteButton;


    private ArrayList<Food> arrayAlimentos;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca_ingredientes);

        getReferencesComponentes();
        retrofit = Consts.connection();
        this.context = this;

        listBuscaIngredienteResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                alimento_clicado = arrayAlimentos.get(position);
                buscarAlimento(null, alimento_clicado.get_id());
            }
        });
    }

    public void buscar(View view) {
        final String TAG = "search";
        String query = editBuscaIngredienteBusca.getText().toString();

        Log.d(TAG, "buscar: " + query);

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

                    arrayAlimentos = new ArrayList<>();
                    arrayAlimentos.addAll(list);

                    ArrayAdapter adapter = new BuscaIngredienteAdapter(context, arrayAlimentos);
                    listBuscaIngredienteResults.setAdapter(adapter);

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

    public void buscarAlimento(View view, String id) {
        final String TAG = "searchFood";
        Log.d(TAG, "buscarAlimento: " + id);

        FoodApi serviceApi = retrofit.create(FoodApi.class);
        Call<Food> call = serviceApi.getFood(id, "bearer " + getPreferences().getString("token", ""));


        call.enqueue(new Callback<Food>() {
            @Override
            public void onResponse(Call<Food> call, Response<Food> response) {
                if (getPreferences().getBoolean("isPremium", false)) {

                    if (response.isSuccessful()) {
                        // se o usuário for premium e a resposta do server for 200OK

                        Food resp = response.body();
                        Log.d(TAG, "name: " + resp.getFood().getName().getValue());
                        Log.d(TAG, "category: " + resp.getFood().getCategory().getValue());
                        for (int i = 0; i < resp.getLstSimilars().size(); i++) {
                            Log.d(TAG, "similar: " + resp.getLstSimilars().get(i).getName().getValue());
                        }

                        // se o cara for premium
                        Intent intent = new Intent(getApplicationContext(), InfoAlimentosActivity.class);
                        intent.putExtra("food", resp);
                        startActivity(intent);
                    } else {
                        // tratamento de erro
                    }
                } else {

                    if (response.isSuccessful()) {
                        // se o usuário NÃO for premium e a resposta do server for 200OK

                        Food resp = response.body();
                        Log.d(TAG, "name: " + resp.getName().getValue());
                        Log.d(TAG, "category: " + resp.getCategory().getValue());

                        Intent intent = new Intent(getApplicationContext(), InfoAlimentosActivity.class);
                        intent.putExtra("food", resp);
                        startActivity(intent);
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
    }

    public void getReferencesComponentes() {
        editBuscaIngredienteBusca = findViewById(R.id.editBuscaIngredienteBusca);
        listBuscaIngredienteResults = findViewById(R.id.listBuscaIngredienteResults);
        imgBuscaIngredienteButton = findViewById(R.id.imgBuscaIngredienteButton);
    }
}
