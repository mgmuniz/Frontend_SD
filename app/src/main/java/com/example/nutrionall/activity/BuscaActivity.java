package com.example.nutrionall.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;

import com.example.nutrionall.R;
import com.example.nutrionall.adapters.FoodAdapter;
import com.example.nutrionall.api.Food.Search;
import com.example.nutrionall.models.Food.Definition;
import com.example.nutrionall.models.Food.Food;
import com.example.nutrionall.utils.Consts;
import com.example.nutrionall.utils.Methods;

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
    }

    
    public void buscar(View view) {
        final String TAG = "search";
        SharedPreferences preferences = getSharedPreferences(Consts.ARQUIVO_PREFERENCIAS, 0);
        String query = editBuscaBusca.getText().toString();

        Food food = new Food();
        Definition x = new Definition();
        x.setValue(query);
        food.setName(x);

        Search serviceApi = retrofit.create(Search.class);
        Call<List<Food>> call = serviceApi.searchByName(food, "bearer " + getPreferences().getString("token", ""));

        call.enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                if (response.isSuccessful()) {
                    List<Food> list = new ArrayList<>();
                    list = response.body();
                    ArrayList<Food> arrayListAux = new ArrayList<>();
//                    int a = list.size();
//                    Log.d(TAG,Integer.toString(a));
                    arrayListAux.addAll(list);

                    ArrayAdapter adapter = new FoodAdapter(context,arrayListAux);
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


    public void desRadioAlimento(View view) {radioAlimento.setChecked(false);}

    public void desRadioNutriente(View view) {radioNutrient.setChecked(false);}

    public void getReferencesComponentes() {
        editBuscaBusca = findViewById(R.id.editBuscaBusca);
        resultadosBusca = (ListView) findViewById(R.id.listBuscaResults);
        radioAlimento = findViewById(R.id.radioBuscaAlimento);
        radioNutrient = findViewById(R.id.radioBuscaNutriente);
    }

    public SharedPreferences getPreferences() {
        SharedPreferences preferences = getSharedPreferences(Consts.ARQUIVO_PREFERENCIAS, 0);
        return preferences;
    }




}
