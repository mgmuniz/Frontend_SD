package com.example.nutrionall.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nutrionall.R;
import com.example.nutrionall.activity.adapterVisualizaRefeicao.MyFragPageAdapterVisuRefeicao;
import com.example.nutrionall.models.Meal.Meal;
import com.example.nutrionall.utils.Consts;
import com.example.nutrionall.utils.Methods;
import com.squareup.picasso.Picasso;

import retrofit2.Retrofit;

public class VisualizaRefeicaoActivity extends AppCompatActivity implements Methods {

    private Retrofit retrofit;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private TextView nomeVisuRefeicao;
    private ImageView imagemVisualizaRefeicao;
    private TextView descricaoVisuRefeicao;

    private Context context;

    private Meal meal;

    private boolean favoritou = false; // Verificar se o user já favoritou a refeição (mudar ícone)

    private ImageButton imgbuttonFavoriteRefeicao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualiza_refeicao);
        this.context = this;
        getReferencesComponentes();

        retrofit = Consts.connection();

        mViewPager.setAdapter(new MyFragPageAdapterVisuRefeicao(getSupportFragmentManager(),getResources().getStringArray(R.array.titles_tab_visualiza_refeicao) ));
        mTabLayout.setupWithViewPager(mViewPager);

    }

    public void onAttachedToWindow(){
        super.onAttachedToWindow();
        setInfo();
    }

    // essa função que vai pegar os dados da refeicao da API e colocar na tela
    private void setInfo(){

        meal = (Meal) getIntent().getSerializableExtra("meal");
        Log.d("Teste recuperacao", meal.getName());
        nomeVisuRefeicao = findViewById(R.id.nomeVisuRefeicao);
        imagemVisualizaRefeicao = findViewById(R.id.imagemVisualizaRefeicao);
        descricaoVisuRefeicao = findViewById(R.id.descricaoVisuRefeicao);



        nomeVisuRefeicao.setText(meal.getName());
        descricaoVisuRefeicao.setText(meal.getDescription());
        Picasso.get().load(meal.getUrlImg()).fit().centerCrop().into(imagemVisualizaRefeicao);
    }

    public void favoritar(View view){

        ImageButton btnFavoriteRefeicao = findViewById(R.id.btnFavoriteRefeicao);

        if(favoritou == true){
            btnFavoriteRefeicao.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            favoritou = false;

        }

        else{
            btnFavoriteRefeicao.setImageResource(R.drawable.ic_favorite_red_24dp);
            favoritou = true;
        }

    }
//
//    public void getRefeicao() {
//        final String TAG = "getRefeição";
//
//        String id = "5d0bdec9bcccc223ae26c8fb";
//
//        Log.d(TAG, "getRefeição: " + id);
//
//        MealApi serviceApi = retrofit.create(MealApi.class);
//        Call<Meal> call = serviceApi.getByID(id, "bearer " + getPreferences().getString("token", ""));
//
//        call.enqueue(new Callback<Meal>() {
//            @Override
//            public void onResponse(Call<Meal> call, Response<Meal> response) {
//                if (getPreferences().getBoolean("isPremium", false)) {
//                    if (response.isSuccessful()) {
//                        // o usuário é premium
//                        Meal resp = response.body();
//                        setInfo(resp);
//                    } else {
//                        Log.d(TAG, "onResponse: erro");
//                    }
//                } else {
//                    if (response.isSuccessful()) {
//                        // o usuário não é premium
//                        Meal resp = response.body();
//                        setInfo(resp);
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Meal> call, Throwable t) {
//                Log.d(TAG, "onFailure: " + t.toString());
//            }
//        });
//    }

    @Override
    public void getReferencesComponentes() {
        mTabLayout = findViewById(R.id.tab_layout_visualiza_refeicao);
        mViewPager = findViewById(R.id.view_pager_visu_ref);
        nomeVisuRefeicao = findViewById(R.id.nomeVisuRefeicao);
        imagemVisualizaRefeicao = findViewById(R.id.imagemVisualizaRefeicao);
        descricaoVisuRefeicao = findViewById(R.id.descricaoVisuRefeicao);

    }

    @Override
    public SharedPreferences getPreferences() {
        SharedPreferences preferences = getSharedPreferences(Consts.ARQUIVO_PREFERENCIAS, 0);
        return preferences;
    }

}
