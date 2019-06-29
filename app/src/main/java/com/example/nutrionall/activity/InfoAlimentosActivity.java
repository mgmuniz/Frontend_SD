package com.example.nutrionall.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.nutrionall.R;
import com.example.nutrionall.adapters.SimilaresAdapter;
import com.example.nutrionall.api.Food.FoodApi;
import com.example.nutrionall.models.Food.Food;
import com.example.nutrionall.utils.Consts;
import com.example.nutrionall.utils.Methods;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class InfoAlimentosActivity extends AppCompatActivity implements Methods {

    private Retrofit retrofit;

    private ProgressBar progressBarInfoAlimento;

    private RecyclerView listInfoAlimentosSimilares;
    private TextView txtInfoAlimentoTitulo;
    private TextView txtInfoAlimentoCategoria;
    private ImageView imgInfoAlimentoCategoria;

    private ImageButton imgbuttonInfoAlimentoLeft;
    private ImageButton imgbuttonInfoAlimentoRight;

    private TableLayout tableInfoAlimentoTableNutrientes;
    private TextView txtInfoAlimentoMsgPremium;


    // Elementos da tableView
    private TextView txtInfoAlimentoHumiditySodium;
    private TextView txtInfoAlimentoEnergyPotassium;
    private TextView txtInfoAlimentoProteinCopper;
    private TextView txtInfoAlimentoLipidsZinc;
    private TextView txtInfoAlimentoCholesterolRetinol;
    private TextView txtInfoAlimentoCarbohydrateRe;
    private TextView txtInfoAlimentoFiberRae;
    private TextView txtInfoAlimentoAshesThiamine;
    private TextView txtInfoAlimentoCalciumRiboflavin;
    private TextView txtInfoAlimentoMagnesiumPyridoxine;
    private TextView txtInfoAlimentoManganaseNiacin;
    private TextView txtInfoAlimentoPhosphorusVitaminC;
    private TextView txtInfoAlimentoIron;

    private TextView txtInfoAlimentoHumiditySodiumV;
    private TextView txtInfoAlimentoEnergyPotassiumV;
    private TextView txtInfoAlimentoProteinCopperV;
    private TextView txtInfoAlimentoLipidsZincV;
    private TextView txtInfoAlimentoCholesterolRetinolV;
    private TextView txtInfoAlimentoCarbohydrateReV;
    private TextView txtInfoAlimentoFiberRaeV;
    private TextView txtInfoAlimentoAshesThiamineV;
    private TextView txtInfoAlimentoCalciumRiboflavinV;
    private TextView txtInfoAlimentoMagnesiumPyridoxineV;
    private TextView txtInfoAlimentoManganaseNiacinV;
    private TextView txtInfoAlimentoPhosphorusVitaminCV;
    private TextView txtInfoAlimentoIronV;


    private Context context;
    private SimilaresAdapter mAdapter;
    private Food food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_alimentos);
        getReferencesComponentes();
        this.context = this;
        setDados();
        String TAG = "infoAlimentosActivity";
        retrofit = Consts.connection();

        addView(); // adiciona uma visualização ao alimento
    }

    private void addView() {
        final String TAG = "addView";
        String id = null;

        progressBarInfoAlimento.setVisibility(View.VISIBLE);

        if (getPreferences().getBoolean("isPremium", false)) {
            id = food.getFood().get_id();
        } else {
            id = food.get_id();
        }

        FoodApi serviceApi = retrofit.create(FoodApi.class);
        Call<JsonObject> call = serviceApi.addView(id, "bearer " + getPreferences().getString("token", ""));

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    progressBarInfoAlimento.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
            }
        });
    }

    private void setupRecycler(List<Food> lstSimilares) {
        String TAG = "Despair";
        // Configurando o gerenciador de layout para ser uma lista.
        Log.d("Despair", "Vou setar o layout");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        listInfoAlimentosSimilares.setLayoutManager(layoutManager);

        // Adiciona o adapter que irá anexar os objetos à lista.

        for (int i = 0; i < lstSimilares.size(); i++) {
            Log.d(TAG, "setupRecycler: " + lstSimilares.get(i).get_id());
        }

        ArrayList<Food> arraySimilares = new ArrayList<>();
        arraySimilares.addAll(lstSimilares);

        mAdapter = new SimilaresAdapter(context, arraySimilares);

        if (mAdapter.getItemCount() > 0) {
            listInfoAlimentosSimilares.setAdapter(mAdapter);

            // Configurando um divisor entre linhas, para uma melhor visualização.
            listInfoAlimentosSimilares.addItemDecoration(
                    new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        }
    }

    @SuppressWarnings("ResourceType")
    private void setDados() {
        String TAG = "setDados";
        this.food = (Food) getIntent().getSerializableExtra("food");

        String txtTituloAlimento;
        String txtCategoriaAlimento;

        if (getPreferences().getBoolean("isPremium", false)) {
            // o premium tem que acessar a função getFood para obter os dados

            txtTituloAlimento = this.food.getFood().getName().getValue();
            txtCategoriaAlimento = this.food.getFood().getCategory().getValue();
        } else {
            txtTituloAlimento = this.food.getName().getValue();
            txtCategoriaAlimento = this.food.getCategory().getValue();
        }

        txtInfoAlimentoTitulo.setText(txtTituloAlimento);
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
                imgInfoAlimentoCategoria.setImageDrawable(categorias.getDrawable(2));
        }
        if (getPreferences().getBoolean("isPremium", false)) {
            setupRecycler(food.getLstSimilars());
        }else{
            // se n for premium, n tem similares
        }

        setTablePart1(null);
    }

    public void setTablePart1(View view){

        if (getPreferences().getBoolean("isPremium", false)) {

            imgbuttonInfoAlimentoLeft.setClickable(false);
            imgbuttonInfoAlimentoLeft.setImageResource(R.drawable.ic_navigate_before_gray);
            imgbuttonInfoAlimentoRight.setClickable(true);
            imgbuttonInfoAlimentoRight.setImageResource(R.drawable.ic_navigate_next_black);

            txtInfoAlimentoHumiditySodium.setText("Umidade");
            txtInfoAlimentoHumiditySodiumV.setText(checkValue(this.food.getFood().getHumidity().getValue(), "%"));

            txtInfoAlimentoEnergyPotassium.setText("Energia");
            txtInfoAlimentoEnergyPotassiumV.setText(checkValue(this.food.getFood().getEnergy().getValue(), "Kj"));

            txtInfoAlimentoProteinCopper.setText("Proteína");
            txtInfoAlimentoProteinCopperV.setText(checkValue(this.food.getFood().getProtein().getValue(), "g"));

            txtInfoAlimentoLipidsZinc.setText("Lipídeos");
            txtInfoAlimentoLipidsZincV.setText(checkValue(this.food.getFood().getLipids().getValue(), "g"));

            txtInfoAlimentoCholesterolRetinol.setText("Colesterol");
            txtInfoAlimentoCholesterolRetinolV.setText(checkValue(this.food.getFood().getCholesterol().getValue(), "g"));

            txtInfoAlimentoCarbohydrateRe.setText("Carboidrato");
            txtInfoAlimentoCarbohydrateReV.setText(checkValue(this.food.getFood().getCarbohydrate().getValue(), "g"));

            txtInfoAlimentoFiberRae.setText("Fibra Alimentar");
            txtInfoAlimentoFiberRaeV.setText(checkValue(this.food.getFood().getFoodFiber().getValue(), "g"));

            txtInfoAlimentoAshesThiamine.setText("Cinzas");
            txtInfoAlimentoAshesThiamineV.setText(checkValue(this.food.getFood().getAshes().getValue(), "g"));

            txtInfoAlimentoCalciumRiboflavin.setText("Cálcio");
            txtInfoAlimentoCalciumRiboflavinV.setText(checkValue(this.food.getFood().getCalcium().getValue(), "g"));

            txtInfoAlimentoMagnesiumPyridoxine.setText("Magnésio");
            txtInfoAlimentoMagnesiumPyridoxineV.setText(checkValue(this.food.getFood().getMagnesium().getValue(), "g"));

            txtInfoAlimentoManganaseNiacin.setText("Manganês");
            txtInfoAlimentoManganaseNiacinV.setText(checkValue(this.food.getFood().getManganese().getValue(), "g"));

            txtInfoAlimentoPhosphorusVitaminC.setText("Fósforo");
            txtInfoAlimentoPhosphorusVitaminCV.setText(checkValue(this.food.getFood().getPhosphorus().getValue(), "g"));

            txtInfoAlimentoIron.setVisibility(TextView.VISIBLE);
            txtInfoAlimentoIronV.setVisibility(TextView.VISIBLE);

            txtInfoAlimentoIron.setText("Ferro");
            txtInfoAlimentoIronV.setText(checkValue(this.food.getFood().getIron().getValue(), "g"));

        }else{

            int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 167, getResources().getDisplayMetrics());
            tableInfoAlimentoTableNutrientes.getLayoutParams().height = height;

            imgbuttonInfoAlimentoLeft.setClickable(false);
            imgbuttonInfoAlimentoLeft.setImageResource(R.drawable.ic_navigate_before_gray);
            imgbuttonInfoAlimentoRight.setClickable(false);
            imgbuttonInfoAlimentoRight.setImageResource(R.drawable.ic_navigate_next_gray);

            txtInfoAlimentoMsgPremium.setVisibility(TextView.VISIBLE);


            txtInfoAlimentoHumiditySodium.setText("Umidade");
            txtInfoAlimentoHumiditySodiumV.setText(checkValue(this.food.getHumidity().getValue(), "%"));

            txtInfoAlimentoEnergyPotassium.setText("Energia");
            txtInfoAlimentoEnergyPotassiumV.setText(checkValue(this.food.getEnergy().getValue(), "Kj"));

            txtInfoAlimentoProteinCopper.setText("Proteína");
            txtInfoAlimentoProteinCopperV.setText(checkValue(this.food.getProtein().getValue(), "g"));

            txtInfoAlimentoLipidsZinc.setText("Lipídeos");
            txtInfoAlimentoLipidsZincV.setText(checkValue(this.food.getLipids().getValue(), "g"));

            txtInfoAlimentoCarbohydrateRe.setText("Carboidrato");
            txtInfoAlimentoCarbohydrateReV.setText(checkValue(this.food.getCarbohydrate().getValue(), "g"));

            txtInfoAlimentoCholesterolRetinol.setVisibility(TextView.INVISIBLE);
            txtInfoAlimentoFiberRae.setVisibility(TextView.INVISIBLE);
            txtInfoAlimentoAshesThiamine.setVisibility(TextView.INVISIBLE);
            txtInfoAlimentoCalciumRiboflavin.setVisibility(TextView.INVISIBLE);
            txtInfoAlimentoMagnesiumPyridoxine.setVisibility(TextView.INVISIBLE);
            txtInfoAlimentoManganaseNiacin.setVisibility(TextView.INVISIBLE);
            txtInfoAlimentoPhosphorusVitaminC.setVisibility(TextView.INVISIBLE);
            txtInfoAlimentoIron.setVisibility(TextView.INVISIBLE);

            txtInfoAlimentoCholesterolRetinolV.setVisibility(TextView.INVISIBLE);
            txtInfoAlimentoFiberRaeV.setVisibility(TextView.INVISIBLE);
            txtInfoAlimentoAshesThiamineV.setVisibility(TextView.INVISIBLE);
            txtInfoAlimentoCalciumRiboflavinV.setVisibility(TextView.INVISIBLE);
            txtInfoAlimentoMagnesiumPyridoxineV.setVisibility(TextView.INVISIBLE);
            txtInfoAlimentoManganaseNiacinV.setVisibility(TextView.INVISIBLE);
            txtInfoAlimentoPhosphorusVitaminCV.setVisibility(TextView.INVISIBLE);
            txtInfoAlimentoIronV.setVisibility(TextView.INVISIBLE);
        }
    }

    public void setTablePart2(View view){

        imgbuttonInfoAlimentoLeft.setClickable(true);
        imgbuttonInfoAlimentoLeft.setImageResource(R.drawable.ic_navigate_before_black);
        imgbuttonInfoAlimentoRight.setClickable(false);
        imgbuttonInfoAlimentoRight.setImageResource(R.drawable.ic_navigate_next_gray);

        txtInfoAlimentoHumiditySodium.setText("Sódio");
        txtInfoAlimentoHumiditySodiumV.setText(checkValue(this.food.getFood().getSodium().getValue(),"g"));

        txtInfoAlimentoEnergyPotassium.setText("Potássio");
        txtInfoAlimentoEnergyPotassiumV.setText(checkValue(this.food.getFood().getPotassium().getValue(),"g"));

        txtInfoAlimentoProteinCopper.setText("Cobre");
        txtInfoAlimentoProteinCopperV.setText(checkValue(this.food.getFood().getCopper().getValue(),"g"));

        txtInfoAlimentoLipidsZinc.setText("Zinco");
        txtInfoAlimentoLipidsZincV.setText(checkValue(this.food.getFood().getZinc().getValue(),"g"));

        txtInfoAlimentoCholesterolRetinol.setText("Retinol");
        txtInfoAlimentoCholesterolRetinolV.setText(checkValue(this.food.getFood().getRetinol().getValue(),"g"));

        txtInfoAlimentoCarbohydrateRe.setText("RE");
        txtInfoAlimentoCarbohydrateReV.setText(checkValue(this.food.getFood().getRe().getValue(),"g"));

        txtInfoAlimentoFiberRae.setText("RAE");
        txtInfoAlimentoFiberRaeV.setText(checkValue(this.food.getFood().getRae().getValue(),"g"));

        txtInfoAlimentoAshesThiamine.setText("Tiamina");
        txtInfoAlimentoAshesThiamineV.setText(checkValue(this.food.getFood().getThiamine().getValue(),"g"));

        txtInfoAlimentoCalciumRiboflavin.setText("Riboflavina");
        txtInfoAlimentoCalciumRiboflavinV.setText(checkValue(this.food.getFood().getRiboflavin().getValue(),"g"));

        txtInfoAlimentoMagnesiumPyridoxine.setText("Piridoxina");
        txtInfoAlimentoMagnesiumPyridoxineV.setText(checkValue(this.food.getFood().getPyridoxine().getValue(),"g"));

        txtInfoAlimentoManganaseNiacin.setText("Niacina");
        txtInfoAlimentoManganaseNiacinV.setText(checkValue(this.food.getFood().getNiacin().getValue(),"g"));

        txtInfoAlimentoPhosphorusVitaminC.setText("Vitamina C");
        txtInfoAlimentoPhosphorusVitaminCV.setText(checkValue(this.food.getFood().getVitaminc().getValue(),"g"));

        txtInfoAlimentoIron.setVisibility(TextView.INVISIBLE);
        txtInfoAlimentoIronV.setVisibility(TextView.INVISIBLE);

    }

    private String checkValue(String value, String notation){
        if(value.intern() == "Null"){
            return "-";
        }else{
            return (value + notation);
        }
    }


    public void getReferencesComponentes() {
        listInfoAlimentosSimilares = findViewById(R.id.listInfoAlimentosSimilares);
        txtInfoAlimentoTitulo = findViewById(R.id.txtInfoAlimentoTitulo);
        txtInfoAlimentoCategoria = findViewById(R.id.txtInfoAlimentoCategoria);
        imgInfoAlimentoCategoria = findViewById(R.id.imgInfoAlimentoCategoria);

        imgbuttonInfoAlimentoLeft = findViewById(R.id.imgbuttonInfoAlimentoLeft);
        imgbuttonInfoAlimentoRight = findViewById(R.id.imgbuttonInfoAlimentoRight);

        tableInfoAlimentoTableNutrientes = findViewById(R.id.tableInfoAlimentoTableNutrientes);
        txtInfoAlimentoMsgPremium = findViewById(R.id.txtInfoAlimentoMsgPremium);


        // Elementos da tableView
        txtInfoAlimentoHumiditySodium = findViewById(R.id.txtInfoAlimentoHumiditySodium);
        txtInfoAlimentoEnergyPotassium = findViewById(R.id.txtInfoAlimentoEnergyPotassium);
        txtInfoAlimentoProteinCopper = findViewById(R.id.txtInfoAlimentoProteinCopper);
        txtInfoAlimentoLipidsZinc = findViewById(R.id.txtInfoAlimentoLipidsZinc);
        txtInfoAlimentoCarbohydrateRe = findViewById(R.id.txtInfoAlimentoCarbohydrateRe);

        txtInfoAlimentoCholesterolRetinol = findViewById(R.id.txtInfoAlimentoCholesterolRetinol);
        txtInfoAlimentoFiberRae = findViewById(R.id.txtInfoAlimentoFiberRae);
        txtInfoAlimentoAshesThiamine = findViewById(R.id.txtInfoAlimentoAshesThiamine);
        txtInfoAlimentoCalciumRiboflavin = findViewById(R.id.txtInfoAlimentoCalciumRiboflavin);
        txtInfoAlimentoMagnesiumPyridoxine = findViewById(R.id.txtInfoAlimentoMagnesiumPyridoxine);
        txtInfoAlimentoManganaseNiacin = findViewById(R.id.txtInfoAlimentoManganaseNiacin);
        txtInfoAlimentoPhosphorusVitaminC = findViewById(R.id.txtInfoAlimentoPhosphorusVitaminC);
        txtInfoAlimentoIron = findViewById(R.id.txtInfoAlimentoIron);

        txtInfoAlimentoHumiditySodiumV = findViewById(R.id.txtInfoAlimentoHumiditySodiumV);
        txtInfoAlimentoEnergyPotassiumV = findViewById(R.id.txtInfoAlimentoEnergyPotassiumV);
        txtInfoAlimentoProteinCopperV = findViewById(R.id.txtInfoAlimentoProteinCopperV);
        txtInfoAlimentoLipidsZincV = findViewById(R.id.txtInfoAlimentoLipidsZincV);
        txtInfoAlimentoCholesterolRetinolV = findViewById(R.id.txtInfoAlimentoCholesterolRetinolV);
        txtInfoAlimentoCarbohydrateReV = findViewById(R.id.txtInfoAlimentoCarbohydrateReV);
        txtInfoAlimentoFiberRaeV = findViewById(R.id.txtInfoAlimentoFiberRaeV);
        txtInfoAlimentoAshesThiamineV = findViewById(R.id.txtInfoAlimentoAshesThiamineV);
        txtInfoAlimentoCalciumRiboflavinV = findViewById(R.id.txtInfoAlimentoCalciumRiboflavinV);
        txtInfoAlimentoMagnesiumPyridoxineV = findViewById(R.id.txtInfoAlimentoMagnesiumPyridoxineV);
        txtInfoAlimentoManganaseNiacinV = findViewById(R.id.txtInfoAlimentoManganaseNiacinV);
        txtInfoAlimentoPhosphorusVitaminCV = findViewById(R.id.txtInfoAlimentoPhosphorusVitaminCV);
        txtInfoAlimentoIronV = findViewById(R.id.txtInfoAlimentoIronV);

        progressBarInfoAlimento = findViewById(R.id.progressBarInfoAlimento);
    }

    public SharedPreferences getPreferences() {
        SharedPreferences preferences = getSharedPreferences(Consts.ARQUIVO_PREFERENCIAS, 0);
        return preferences;
    }
}
