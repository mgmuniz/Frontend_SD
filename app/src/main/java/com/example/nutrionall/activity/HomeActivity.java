package com.example.nutrionall.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.nutrionall.R;
import com.example.nutrionall.adapters.AdapterSearchBarHome;
import com.example.nutrionall.api.Meal.MealApi;
import com.example.nutrionall.models.Meal.Meal;
import com.example.nutrionall.utils.Consts;
import com.example.nutrionall.utils.Methods;
import com.example.nutrionall.utils.RecyclerItemClickListener;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Methods {

    private List<Meal> topMeals;


    private EditText editHomeBusca;
    private TextView titulo;
    private TextView textNavHeaderEmail;
    private TextView textNavHeaderName;
    private ImageView editCadastroImgUser;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private Retrofit retrofit;
    private ImageView imgHomeSearchButton;
    private RecyclerView recyclerSearchHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getReferencesComponentes();

        setSupportActionBar(toolbar);

        retrofit = Consts.connection();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        ListAllMealsTask task = new ListAllMealsTask();
        task.execute("bearer " + getPreferences().getString("token", ""));

        setRecycler();
    }

    public void listAllMeal() {
        final String TAG = "listAll";

        MealApi serviceApi = retrofit.create(MealApi.class);
        Call<List<Meal>> call = serviceApi.listAllMeal("bearer " + getPreferences().getString("token", ""));


        call.enqueue(new Callback<List<Meal>>() {
            @Override
            
            public void onResponse(Call<List<Meal>> call, Response<List<Meal>> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: " + response.toString());
                    topMeals = response.body();

                    for (int i = 0; i < topMeals.size(); i++) {
                        Log.d(TAG, "onResponse: " + topMeals.get(i).getName());
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Meal>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
            }
        });
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getReferencesComponentes();

        // Recupera informações do perfil do usuario
        Log.d("home", "onCreate: " + getPreferences().contains("email"));

        String defaultImg = "https://cdn4.iconfinder.com/data/icons/avatars-xmas-giveaway/128/anime_spirited_away_no_face_nobody-512.png";

        textNavHeaderEmail.setText(getPreferences().getString("email", ""));
        textNavHeaderName.setText(getPreferences().getString("name", ""));
        Picasso.get().load(getPreferences().getString("urlImg", defaultImg)).fit().centerCrop().into(editCadastroImgUser);

        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_user) {
            // Handle the camera action
        } else if (id == R.id.nav_refeicoes) {
            Intent intent = new Intent(HomeActivity.this, RefeicoesActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_premium) {

        } else if (id == R.id.navLogout) {
            logout();
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void desRadioAlimento(View view) {
        RadioButton radioAlimento = findViewById(R.id.radioBuscaAlimento);
        radioAlimento.setChecked(false);

        imgHomeSearchButton.setVisibility(View.INVISIBLE);
        editHomeBusca.setVisibility(View.INVISIBLE);
        recyclerSearchHome.setVisibility(View.VISIBLE);
    }

    public void desRadioNutriente(View view) {
        RadioButton radioNutrient = findViewById(R.id.radioBuscaNutriente);
        radioNutrient.setChecked(false);

        imgHomeSearchButton.setVisibility(View.VISIBLE);
        editHomeBusca.setVisibility(View.VISIBLE);
        recyclerSearchHome.setVisibility(View.INVISIBLE);
    }

    public void buscar(View view) {
        Intent intent = new Intent(HomeActivity.this, BuscaActivity.class);
        intent.putExtra("termo_busca", editHomeBusca.getText().toString());
        startActivity(intent);
    }

    @Override
    public void getReferencesComponentes() {
        textNavHeaderEmail = findViewById(R.id.textNavHeaderEmail);
        textNavHeaderName = findViewById(R.id.textNavHeaderName);
        editCadastroImgUser = findViewById(R.id.editCadastroImgUser);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        editHomeBusca = findViewById(R.id.editHomeBusca);
        recyclerSearchHome = findViewById(R.id.recyclerSearchHome);
        imgHomeSearchButton = findViewById(R.id.imgHomeSearchButton);
    }

    @Override
    public SharedPreferences getPreferences() {
        SharedPreferences preferences = getSharedPreferences(Consts.ARQUIVO_PREFERENCIAS, 0);
        return preferences;
    }

    private void setRecycler(){
        // adapter recyclerView search bar nutrient
        Consts.generateNutrient();
        AdapterSearchBarHome adapter = new AdapterSearchBarHome(Consts.nutrients);

        recyclerSearchHome.setVisibility(View.INVISIBLE);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(
                getApplicationContext(),
                LinearLayoutManager.HORIZONTAL,
                false);
        recyclerSearchHome.setLayoutManager(layoutManager);
        recyclerSearchHome.setHasFixedSize(true);
        recyclerSearchHome.setAdapter(adapter);
        recyclerSearchHome.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerSearchHome,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent intent = new Intent(getApplicationContext(), BuscaActivity.class);

                                ArrayList<String> keys = new ArrayList<>(Consts.nutrients.keySet());
                                // Log.d("searchBar", "onItemClick: " + Consts.getNutrient(keys.get(position)));
                                intent.putExtra("nutrient", Consts.getNutrient(keys.get(position)));
                                startActivity(intent);
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

    private void logout() {
        // Remove as preferencias do usuário
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.remove("name");
        editor.remove("email");
        editor.remove("token");
        editor.remove("_id");
        editor.remove("isPremium");
        editor.remove("urlImg");
        editor.commit();
    }


    class ListAllMealsTask extends AsyncTask<String, Call, List<Meal>> {
        String TAG = "listAllMealsTask";

        @Override
        protected List<Meal> doInBackground(String... strings) {
            MealApi serviceApi = retrofit.create(MealApi.class);
            Call<List<Meal>> call = serviceApi.listAllMeal(strings[0]);
            Response<List<Meal>> x = null;

            try {
                x = call.execute();
                Log.d(TAG, "doInBackground: " + x.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return x.body();
        }

        class SortByAvgEvaluation implements Comparator<Meal>
        {
            // Used for sorting in ascending order of
            // roll number
            public int compare(Meal a, Meal b)
            {
                return (int) (b.getAvgEvaluation() - a.getAvgEvaluation());
            }
        }

        @Override
        protected void onPostExecute(List<Meal> meals) {
            super.onPostExecute(meals);
            topMeals = meals;

            Collections.sort(topMeals, new SortByAvgEvaluation());

            for(int i = 0; i < topMeals.size(); i++){
                Log.d(TAG, "onPostExecute: " + topMeals.get(i).getAvgEvaluation());
            }

            CarouselView carouselView = findViewById(R.id.CarouselHomeRefeicoes);

            carouselView.setImageListener(new ImageListener() {
                @Override
                public void setImageForPosition(int position, ImageView imageView) {
                    Picasso.get().load(topMeals.get(position).getUrlImg()).fit().centerCrop().into(imageView);
                }
            });
            carouselView.setImageClickListener(new ImageClickListener() {
                @Override
                public void onClick(int position) {
                }
            });

            if (topMeals.size() < 7) {
                carouselView.setPageCount(topMeals.size());
            } else {
                carouselView.setPageCount(7);
            }

        }
    }
}
