package com.example.nutrionall.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.nutrionall.R;
import com.example.nutrionall.api.Food.Search;
import com.example.nutrionall.models.Food.Definition;
import com.example.nutrionall.models.Food.Food;
import com.example.nutrionall.utils.Consts;
import com.example.nutrionall.utils.Methods;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Methods {

    private String[] mImages = new String[]{
            "https://nit.pt/wp-content/uploads/2017/12/840b2ccfec7171179aac43a1f6919c11-754x394.jpg",
            "https://nit.pt/wp-content/uploads/2017/12/c4ca4238a0b923820dcc509a6f75849b-10-754x394.jpg",
            "https://nit.pt/wp-content/uploads/2019/06/3db9f34c117da413eb2de25c06547fe8-754x394.jpg",
            "https://nit.pt/wp-content/uploads/2019/06/a0ea12235f3b00b1ace614e143be0eb2-1-754x394.jpg",
            "https://nit.pt/wp-content/uploads/2019/06/1a051d4e6c04508055d43a8f4f5cb313-754x394.jpg"
    };


    private EditText editHomeBusca;
    private TextView titulo;
    private TextView textNavHeaderEmail;
    private TextView textNavHeaderName;
    private ImageView editCadastroImgUser;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getReferencesComponentes();

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        CarouselView carouselView = findViewById(R.id.CarouselHomeRefeicoes);
        carouselView.setPageCount(mImages.length);
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
//                imageView.setImageResource(mImages[position]);
                Picasso.get().load(mImages[position]).fit().centerCrop().into(imageView);
            }
        });


        carouselView.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
//                titulo.setText(mTitles[position]);
            }
        });

        retrofit = Consts.connection();


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
    }

    public void desRadioNutriente(View view) {

        RadioButton radioNutrient = findViewById(R.id.radioBuscaNutriente);

        radioNutrient.setChecked(false);
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
    }

    @Override
    public SharedPreferences getPreferences() {
        SharedPreferences preferences = getSharedPreferences(Consts.ARQUIVO_PREFERENCIAS, 0);
        return preferences;
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

}
