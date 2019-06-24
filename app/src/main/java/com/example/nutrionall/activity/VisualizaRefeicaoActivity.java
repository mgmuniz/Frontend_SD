package com.example.nutrionall.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nutrionall.R;
import com.example.nutrionall.activity.adapterVisualizaRefeicao.MyFragPageAdapterVisuRefeicao;
import com.example.nutrionall.api.Meal.MealApi;
import com.example.nutrionall.models.Meal.Meal;
import com.example.nutrionall.utils.Consts;
import com.example.nutrionall.utils.Methods;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class VisualizaRefeicaoActivity extends AppCompatActivity implements Methods {

    private Retrofit retrofit;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private TextView refeicaoTitulo;
    private ImageView imagemVisualizaRefeicao;
    private TextView descricaoRefeicao;

    private ImageButton imgbuttonFavoriteRefeicao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualiza_refeicao);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout_visualiza_refeicao);
        mViewPager = (ViewPager) findViewById(R.id.view_pager_visu_ref);

        mViewPager.setAdapter(new MyFragPageAdapterVisuRefeicao(getSupportFragmentManager(),getResources().getStringArray(R.array.titles_tab_visualiza_refeicao) ));
        mTabLayout.setupWithViewPager(mViewPager);

    }

    private void setInfo(Meal refeicao){
        refeicaoTitulo = findViewById(R.id.nomeVisuRefeicao);
        imagemVisualizaRefeicao = findViewById(R.id.ImagemVisualizaRefeicao);
        descricaoRefeicao = findViewById(R.id.descricaoVisuRefeicao);

    }

    public void getRefeicao() {
        final String TAG = "getRefeição";

        String id = "5d0bdec9bcccc223ae26c8fb";

        Log.d(TAG, "getRefeição: " + id);

        MealApi serviceApi = retrofit.create(MealApi.class);
        Call<Meal> call = serviceApi.getByID(id, "bearer " + getPreferences().getString("token", ""));

        call.enqueue(new Callback<Meal>() {
            @Override
            public void onResponse(Call<Meal> call, Response<Meal> response) {
                if (getPreferences().getBoolean("isPremium", false)) {
                    if (response.isSuccessful()) {
                        // o usuário é premium
                        Meal resp = response.body();
                        setInfo(resp);
                    } else {
                        Log.d(TAG, "onResponse: erro");
                    }
                } else {
                    if (response.isSuccessful()) {
                        // o usuário não é premium
                        Meal resp = response.body();
                        setInfo(resp);
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
