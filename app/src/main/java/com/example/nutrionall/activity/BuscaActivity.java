package com.example.nutrionall.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.nutrionall.R;
import com.example.nutrionall.adapters.AdapterSearchBarHome;
import com.example.nutrionall.adapters.FoodAdapter;
import com.example.nutrionall.api.Food.FoodApi;
import com.example.nutrionall.api.Food.Search;
import com.example.nutrionall.models.Food.Food;
import com.example.nutrionall.models.Food.SearchNutrientFood;
import com.example.nutrionall.utils.Consts;
import com.example.nutrionall.utils.Methods;
import com.example.nutrionall.utils.RecyclerItemClickListener;
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
    private Food alimento_clicado;
    private RecyclerView recyclerBusca;
    private ImageView imgHomeSearchButton;
    private ProgressBar progressBarBuscaActivity;
    private ImageView imgLogoAtivityBusca;
    private ImageView imgLogoAtivityBusca2;


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
        String nutrient = intent.getStringExtra("nutrient");

        resultadosBusca.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                alimento_clicado = arrayAlimentos.get(position);
                buscarAlimento(null, alimento_clicado.get_id());
            }
        });
        setRecycler();

        if(termo_busca != null){
            editBuscaBusca.setText(termo_busca);
            buscar(null);
        }else{
            buscarNutriente(nutrient);
            desRadioAlimento(null);
            radioNutrient.setChecked(true);
        }
    }

    private void setRecycler(){
        // adapter recyclerView search bar nutrient
        Consts.generateNutrient();
        AdapterSearchBarHome adapter = new AdapterSearchBarHome(Consts.nutrients);

        recyclerBusca.setVisibility(View.INVISIBLE);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(
                getApplicationContext(),
                LinearLayoutManager.HORIZONTAL,
                false);
        recyclerBusca.setLayoutManager(layoutManager);
        recyclerBusca.setHasFixedSize(true);
        recyclerBusca.setAdapter(adapter);
        recyclerBusca.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerBusca,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent intent = new Intent(getApplicationContext(), BuscaActivity.class);

                                ArrayList<String> keys = new ArrayList<>(Consts.nutrients.keySet());
                                buscarNutriente(Consts.getNutrient(keys.get(position)));
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        })

        );
    }


    public void buscar(View view) {
        final String TAG = "search";
        String query = editBuscaBusca.getText().toString();

        Log.d(TAG, "buscar: " + query);

        JsonObject food = new JsonObject();
        food.addProperty("name", query);

        // componente loading
        progressBarBuscaActivity.setVisibility(View.VISIBLE);

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

                    progressBarBuscaActivity.setVisibility(View.INVISIBLE);

                    ArrayAdapter adapter = new FoodAdapter(context, arrayAlimentos);
                    resultadosBusca.setAdapter(adapter);

                } else {
                    Log.d(TAG, "onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
                Toast.makeText(getApplicationContext(), "Ocorreu um erro na comunicação com o servidor", Toast.LENGTH_LONG);
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

        // componente loading
        progressBarBuscaActivity.setVisibility(View.VISIBLE);

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

                        progressBarBuscaActivity.setVisibility(View.INVISIBLE);

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

    public void buscarNutriente(String nutrient) {
        final String TAG = "searchNutrient";

        // nutriente q deve ser alterado
        Log.d(TAG, "buscarNutriente: " + nutrient);

        // componente loading
        progressBarBuscaActivity.setVisibility(View.VISIBLE);

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

                        arrayAlimentos = new ArrayList<>();
                        arrayAlimentos.addAll(resp.getData());

                        progressBarBuscaActivity.setVisibility(View.INVISIBLE);

                        ArrayAdapter adapter = new FoodAdapter(context, arrayAlimentos);
                        resultadosBusca.setAdapter(adapter);
                    } else {
                        Log.d(TAG, Consts.falhaReq(response.code(), response.message(), response.raw().toString()));
                    }
                } else {
                    if (response.isSuccessful()) {
                        // se o usuário não for premium
                        Log.d(TAG, "onResponse: n premium");
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

        editBuscaBusca.setVisibility(View.INVISIBLE);
        imgHomeSearchButton.setVisibility(View.INVISIBLE);
        imgLogoAtivityBusca.setVisibility(View.INVISIBLE);

        imgLogoAtivityBusca2.setVisibility(View.VISIBLE);
        recyclerBusca.setVisibility(View.VISIBLE);
    }

    public void desRadioNutriente(View view) {
        radioNutrient.setChecked(false);

        editBuscaBusca.setVisibility(View.VISIBLE);
        imgHomeSearchButton.setVisibility(View.VISIBLE);
        imgLogoAtivityBusca.setVisibility(View.VISIBLE);

        imgLogoAtivityBusca2.setVisibility(View.INVISIBLE);
        recyclerBusca.setVisibility(View.INVISIBLE);
    }

    public void getReferencesComponentes() {
        editBuscaBusca = findViewById(R.id.editBuscaBusca);
        resultadosBusca = findViewById(R.id.listBuscaResults);
        radioAlimento = findViewById(R.id.radioBuscaAlimento);
        radioNutrient = findViewById(R.id.radioBuscaNutriente);
        recyclerBusca = findViewById(R.id.recyclerBusca);
        imgHomeSearchButton = findViewById(R.id.imgHomeSearchButton);
        progressBarBuscaActivity = findViewById(R.id.progressBarBuscaActivity);
        imgLogoAtivityBusca = findViewById(R.id.imgLogoAtivityBusca);
        imgLogoAtivityBusca2  = findViewById(R.id.imgLogoAtivityBusca2);
    }
}
