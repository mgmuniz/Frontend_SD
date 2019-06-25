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
import android.widget.ProgressBar;
import android.widget.RadioButton;

import com.example.nutrionall.R;
import com.example.nutrionall.adapters.BuscaRefeicaoAdapter;
import com.example.nutrionall.adapters.FoodAdapter;
import com.example.nutrionall.api.Meal.MealApi;
import com.example.nutrionall.models.Food.Food;
import com.example.nutrionall.models.Meal.Meal;
import com.example.nutrionall.utils.Consts;
import com.example.nutrionall.utils.Methods;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BuscaRefeicaoActivity extends AppCompatActivity implements Methods {

    private EditText editBuscaRefeicaoBusca;
    private Retrofit retrofit;
    private ListView listBuscaRefeicaoResults;

    private RadioButton radioDesjejum;
    private RadioButton radioAlmoco;
    private RadioButton radioLanche;
    private RadioButton radioJantar;


    private Meal refeicao_clicada;

    private ProgressBar progressBarBuscaRefeicaoActivity;

    private ArrayList<Meal> arrayRefeicoes;

    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca_refeicao);
        getReferencesComponentes();

        retrofit = Consts.connection();
        this.context = this;

        Intent intent = getIntent();
        String termo_busca = intent.getStringExtra("termo_busca");
        int modo_busca = intent.getIntExtra("modo_busca",1);

        editBuscaRefeicaoBusca.setText(termo_busca);

        if(modo_busca == 1){radioJantar.setChecked(true);}
        if(modo_busca == 2){radioAlmoco.setChecked(true);}
        if(modo_busca == 3){radioLanche.setChecked(true);}
        if(modo_busca == 4){radioDesjejum.setChecked(true);}

        seachByName(null);

        listBuscaRefeicaoResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), VisualizaRefeicaoActivity.class);
                intent.putExtra("meal", arrayRefeicoes.get(position));
                startActivity(intent);
            }
        });
    }

    public void seachByName(View view) {
        final String TAG = "searchByName";

        String name = editBuscaRefeicaoBusca.getText().toString();
        int classification = getRadioActivate();

        MealApi serviceApi = retrofit.create(MealApi.class);
        Call<List<Meal>> call = serviceApi.searchByName(name, classification, "bearer " + getPreferences().getString("token", ""));

        call.enqueue(new Callback<List<Meal>>() {
            @Override
            public void onResponse(Call<List<Meal>> call, Response<List<Meal>> response) {
                if (response.isSuccessful()) {
                    List<Meal> resp = response.body();
                    arrayRefeicoes = new ArrayList<>();
                    arrayRefeicoes.addAll(resp);

                    progressBarBuscaRefeicaoActivity.setVisibility(View.INVISIBLE);

                    ArrayAdapter adapter = new BuscaRefeicaoAdapter(context, arrayRefeicoes);
                    listBuscaRefeicaoResults.setAdapter(adapter);


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


    private int getRadioActivate(){
        if(radioJantar.isChecked()){return 1;}
        if(radioAlmoco.isChecked()){return 2;}
        if(radioLanche.isChecked()){return 3;}
        if(radioDesjejum.isChecked()){return 4;}

        return 1;
    }

    public void desRadioLessDesjejum(View view) {
        radioJantar.setChecked(false);
        radioLanche.setChecked(false);
        radioAlmoco.setChecked(false);
    }

    public void desRadioLessAlmoco(View view) {
        radioJantar.setChecked(false);
        radioLanche.setChecked(false);
        radioDesjejum.setChecked(false);
    }

    public void desRadioLessLanche(View view) {
        radioJantar.setChecked(false);
        radioAlmoco.setChecked(false);
        radioDesjejum.setChecked(false);
    }

    public void desRadioLessJantar(View view) {
        radioLanche.setChecked(false);
        radioAlmoco.setChecked(false);
        radioDesjejum.setChecked(false);
    }

    public void getReferencesComponentes() {
        editBuscaRefeicaoBusca = findViewById(R.id.editBuscaRefeicaoBusca);
        listBuscaRefeicaoResults = findViewById(R.id.listBuscaRefeicaoResults);
        progressBarBuscaRefeicaoActivity = findViewById(R.id.progressBarBuscaRefeicaoActivity);
        radioDesjejum = findViewById(R.id.radioButtonBuscaRefeicaoDesjejum);
        radioAlmoco = findViewById(R.id.radioButtonBuscaRefeicaoAlmoco);
        radioLanche = findViewById(R.id.radioButtonBuscaRefeicaoLanche);
        radioJantar = findViewById(R.id.radioButtonBuscaRefeicaoJantar);

    }

    public SharedPreferences getPreferences() {
        SharedPreferences preferences = getSharedPreferences(Consts.ARQUIVO_PREFERENCIAS, 0);
        return preferences;
    }
}
