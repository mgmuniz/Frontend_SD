package com.example.nutrionall.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nutrionall.R;
import com.example.nutrionall.adapters.FoodAdapter;
import com.example.nutrionall.adapters.SimilaresAdapter;
import com.example.nutrionall.models.Food.Definition;
import com.example.nutrionall.models.Food.Food;
import com.example.nutrionall.utils.Consts;
import com.example.nutrionall.utils.Methods;

import java.util.ArrayList;
import java.util.List;

public class InfoAlimentosActivity extends AppCompatActivity implements Methods {

    private RecyclerView listInfoAlimentosSimilares;
    private TextView txtInfoAlimentoTitulo;
    private TextView txtInfoAlimentoCategoria;
    private ImageView imgInfoAlimentoCategoria;

    private Context context;
    private SimilaresAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_alimentos);
        getReferencesComponentes();
        this.context = this;
        setDados();


    }

    private void setupRecycler(List<Food> lstSimilares) {

        // Configurando o gerenciador de layout para ser uma lista.
        Log.d("Despair","Vou setar o layout");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        listInfoAlimentosSimilares.setLayoutManager(layoutManager);

        // Adiciona o adapter que irá anexar os objetos à lista.


        ArrayList<Food> arraySimilares = new ArrayList<>();
        arraySimilares.addAll(lstSimilares);

        mAdapter = new SimilaresAdapter(context,arraySimilares);

        if(mAdapter.getItemCount() > 0) {
            listInfoAlimentosSimilares.setAdapter(mAdapter);

            // Configurando um divisor entre linhas, para uma melhor visualização.
            listInfoAlimentosSimilares.addItemDecoration(
                    new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        }
    }

    @SuppressWarnings("ResourceType")
    private void setDados() {
        Food food = (Food) getIntent().getSerializableExtra("food");
        txtInfoAlimentoTitulo.setText(food.getName().getValue());

        String txtCategoriaAlimento = food.getCategory().getValue();
        txtInfoAlimentoCategoria.setText(txtCategoriaAlimento);

        Resources res = context.getResources();
        TypedArray categorias = res.obtainTypedArray(R.array.categorias);


        switch (txtCategoriaAlimento) {
            case "carnes e derivados":
                imgInfoAlimentoCategoria.setImageDrawable(categorias.getDrawable(0));
                break;
            case "frutas e derivados":
                imgInfoAlimentoCategoria.setImageDrawable(categorias.getDrawable(1));
                break;
            case "verduras, hortaliças e derivados":
                imgInfoAlimentoCategoria.setImageDrawable(categorias.getDrawable(2));
                break;
            case "cereais e derivados":
                imgInfoAlimentoCategoria.setImageDrawable(categorias.getDrawable(3));
                break;
            case "pescados e frutos do mar":
                imgInfoAlimentoCategoria.setImageDrawable(categorias.getDrawable(4));
                break;
            case "leguminosas e derivados":
                imgInfoAlimentoCategoria.setImageDrawable(categorias.getDrawable(5));
                break;
            case "alimentos preparados":
                imgInfoAlimentoCategoria.setImageDrawable(categorias.getDrawable(6));
                break;
            case "leite e derivados":
                imgInfoAlimentoCategoria.setImageDrawable(categorias.getDrawable(7));
                break;
            case "produtos açucarados":
                imgInfoAlimentoCategoria.setImageDrawable(categorias.getDrawable(8));
                break;
            case "bebidas (alcoólicas e não alcoólicas)":
                imgInfoAlimentoCategoria.setImageDrawable(categorias.getDrawable(9));
                break;
            case "ovos e derivados":
                imgInfoAlimentoCategoria.setImageDrawable(categorias.getDrawable(10));
                break;
            case "miscelâneas":
                imgInfoAlimentoCategoria.setImageDrawable(categorias.getDrawable(11));
                break;
            case "outros alimentos industrializados":
                imgInfoAlimentoCategoria.setImageDrawable(categorias.getDrawable(12));
                break;
            default:
                imgInfoAlimentoCategoria.setImageDrawable(categorias.getDrawable(11));
        }

        setupRecycler(food.getLstSimilars());
    }


    public void getReferencesComponentes() {
        listInfoAlimentosSimilares = findViewById(R.id.listInfoAlimentosSimilares);
        txtInfoAlimentoTitulo = findViewById(R.id.txtInfoAlimentoTitulo);
        txtInfoAlimentoCategoria = findViewById(R.id.txtInfoAlimentoCategoria);
        imgInfoAlimentoCategoria = findViewById(R.id.imgInfoAlimentoCategoria);
    }

    public SharedPreferences getPreferences() {
        SharedPreferences preferences = getSharedPreferences(Consts.ARQUIVO_PREFERENCIAS, 0);
        return preferences;
    }
}
