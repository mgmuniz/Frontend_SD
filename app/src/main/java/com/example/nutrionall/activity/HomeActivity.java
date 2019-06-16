package com.example.nutrionall.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.nutrionall.R;
import com.example.nutrionall.utils.Consts;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
//    private int[] mImages = new int[]{
//            R.drawable.refeicoes_a, R.drawable.refeicoes_b, R.drawable.refeicoes_c,
//            R.drawable.refeicoes_d,R.drawable.refeicoes_e
//    };
    private String[] mImages = new String[]{
            "https://nit.pt/wp-content/uploads/2017/12/840b2ccfec7171179aac43a1f6919c11-754x394.jpg",
            "https://nit.pt/wp-content/uploads/2017/12/c4ca4238a0b923820dcc509a6f75849b-10-754x394.jpg",
            "https://nit.pt/wp-content/uploads/2019/06/3db9f34c117da413eb2de25c06547fe8-754x394.jpg",
            "https://nit.pt/wp-content/uploads/2019/06/a0ea12235f3b00b1ace614e143be0eb2-1-754x394.jpg",
            "https://nit.pt/wp-content/uploads/2019/06/1a051d4e6c04508055d43a8f4f5cb313-754x394.jpg"
    };


    private TextView titulo;
    private TextView textNavHeaderEmail;
    private TextView textNavHeaderName;
    private ImageView editCadastroImgUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
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

        // Adiciona informações ao perfil do usuario
        SharedPreferences preferences = getSharedPreferences(Consts.ARQUIVO_PREFERENCIAS, 0);
        Log.d("home", "onCreate: " + preferences.contains("email"));

        String defaultImg = "https://cdn4.iconfinder.com/data/icons/avatars-xmas-giveaway/128/anime_spirited_away_no_face_nobody-512.png";

        textNavHeaderEmail = findViewById(R.id.textNavHeaderEmail);
        textNavHeaderName = findViewById(R.id.textNavHeaderName);
        editCadastroImgUser = findViewById(R.id.editCadastroImgUser);

        textNavHeaderEmail.setText(preferences.getString("email", ""));
        textNavHeaderName.setText(preferences.getString("name", ""));
        Picasso.get().load(preferences.getString("urlImg",defaultImg)).fit().centerCrop().into(editCadastroImgUser);

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

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void desRadioAlimento(View view){

        RadioButton radioAlimento = (RadioButton) findViewById(R.id.radioHomeAlimento);

        radioAlimento.setChecked(false);
    }

    public void desRadioNutriente(View view){

        RadioButton radioNutrient = (RadioButton) findViewById(R.id.radioHomeNutriente);
        
        radioNutrient.setChecked(false);
    }
}
