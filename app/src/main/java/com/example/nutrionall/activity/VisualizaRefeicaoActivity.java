package com.example.nutrionall.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.nutrionall.R;
import com.example.nutrionall.activity.adapterVisualizaRefeicao.MyFragPageAdapterVisuRefeicao;
import com.example.nutrionall.api.Food.FoodApi;
import com.example.nutrionall.models.Food.Food;
import com.example.nutrionall.models.Meal.Meal;
import com.example.nutrionall.models.User.AuthUser;
import com.example.nutrionall.utils.Consts;
import com.example.nutrionall.utils.Methods;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class VisualizaRefeicaoActivity extends AppCompatActivity implements Methods {

    // componentes
    private ImageView imgVisualizaRefeicao;
    private TextView nomeVisualizaRefeicao;
    private TextView descVisualizaRefeicao;
    private RatingBar ratingBarVisualizaRefeicao;
    private ImageView btnFavoriteRefeicao;

    private Retrofit retrofit;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

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

        meal = (Meal) getIntent().getSerializableExtra("meal");

        AuthUser user = new AuthUser();
        user.setUrlImg(getPreferences().getString("urlImg", ""));
        user.set_id(getPreferences().getString("_id",""));
        user.setEmail(getPreferences().getString("email", ""));
        user.setName(getPreferences().getString("name", ""));
        user.setPremium(getPreferences().getBoolean("isPremium", false));
        user.setToken(getPreferences().getString("token", ""));

        mViewPager.setAdapter(
                new MyFragPageAdapterVisuRefeicao(getSupportFragmentManager(),
                        getResources().getStringArray(R.array.titles_tab_visualiza_refeicao),
                        meal,
                        user));
        mTabLayout.setupWithViewPager(mViewPager);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                final String TAG = "tablayout";
                Log.d(TAG, "onTabSelected: " + tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

//        setInfo();
    }

    // essa função que vai pegar os dados da refeicao da API e colocar na tela
    private void setInfo(){
        String TAG = "setInfo";

        meal = (Meal) getIntent().getSerializableExtra("meal");
        Log.d("Teste recuperacao", meal.getName());
        nomeVisualizaRefeicao = findViewById(R.id.nomeVisualizaRefeicao);
        imgVisualizaRefeicao = findViewById(R.id.imgVisualizaRefeicao);
        descVisualizaRefeicao = findViewById(R.id.descVisualizaRefeicao);

        nomeVisualizaRefeicao.setText(meal.getName());
        descVisualizaRefeicao.setText(meal.getDescription());
        Picasso.get().load(meal.getUrlImg()).fit().centerCrop().into(imgVisualizaRefeicao);
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


    @Override
    public void getReferencesComponentes() {
        imgVisualizaRefeicao = findViewById(R.id.imgVisualizaRefeicao);
        nomeVisualizaRefeicao = findViewById(R.id.nomeVisualizaRefeicao);
        descVisualizaRefeicao = findViewById(R.id.descVisualizaRefeicao);
        ratingBarVisualizaRefeicao = findViewById(R.id.ratingBarVisualizaRefeicao);
        btnFavoriteRefeicao = findViewById(R.id.btnFavoriteRefeicao);
        mTabLayout = findViewById(R.id.tab_layout_visualiza_refeicao);
        mViewPager = findViewById(R.id.view_pager_visu_ref);
    }

    @Override
    public SharedPreferences getPreferences() {
        SharedPreferences preferences = getSharedPreferences(Consts.ARQUIVO_PREFERENCIAS, 0);
        return preferences;
    }

}