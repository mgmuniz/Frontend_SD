package com.example.nutrionall.activity.fragmentVisualizaRefeicao;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.nutrionall.R;
import com.example.nutrionall.api.Food.FoodApi;
import com.example.nutrionall.models.Food.Food;
import com.example.nutrionall.models.Meal.Ingredient;
import com.example.nutrionall.models.Meal.IngredientPlus;
import com.example.nutrionall.models.Meal.Meal;
import com.example.nutrionall.models.User.AuthUser;
import com.example.nutrionall.utils.Consts;
import com.example.nutrionall.utils.Methods;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

public class VisuRefeicaoNutricionais extends Fragment implements Methods {

    private View v;
    private Context context;
    private Retrofit retrofit;
    private ArrayList<Food> foods = new ArrayList<>();
    private boolean run = true;

    private TableLayout tableVisuRefeicaoTableNutrientes;
    private ImageButton imgbuttonVisuRefeicaoLeft;
    private ImageButton imgbuttonVisuRefeicaoRight;

    // Elementos da tableView
    private TextView txtVisuRefeicaoHumiditySodium;
    private TextView txtVisuRefeicaoEnergyPotassium;
    private TextView txtVisuRefeicaoProteinCopper;
    private TextView txtVisuRefeicaoLipidsZinc;
    private TextView txtVisuRefeicaoCholesterolRetinol;
    private TextView txtVisuRefeicaoCarbohydrateRe;
    private TextView txtVisuRefeicaoFiberRae;
    private TextView txtVisuRefeicaoAshesThiamine;
    private TextView txtVisuRefeicaoCalciumRiboflavin;
    private TextView txtVisuRefeicaoMagnesiumPyridoxine;
    private TextView txtVisuRefeicaoManganaseNiacin;
    private TextView txtVisuRefeicaoPhosphorusVitaminC;
    private TextView txtVisuRefeicaoIron;

    private TextView txtVisuRefeicaoHumiditySodiumV;
    private TextView txtVisuRefeicaoEnergyPotassiumV;
    private TextView txtVisuRefeicaoProteinCopperV;
    private TextView txtVisuRefeicaoLipidsZincV;
    private TextView txtVisuRefeicaoCholesterolRetinolV;
    private TextView txtVisuRefeicaoCarbohydrateReV;
    private TextView txtVisuRefeicaoFiberRaeV;
    private TextView txtVisuRefeicaoAshesThiamineV;
    private TextView txtVisuRefeicaoCalciumRiboflavinV;
    private TextView txtVisuRefeicaoMagnesiumPyridoxineV;
    private TextView txtVisuRefeicaoManganaseNiacinV;
    private TextView txtVisuRefeicaoPhosphorusVitaminCV;
    private TextView txtVisuRefeicaoIronV;

    private float valHumidity = 0;
    private float valSodium = 0;
    private float valEnergy = 0;
    private float valPotassium = 0;
    private float valProtein = 0;
    private float valCopper = 0;
    private float valLipids = 0;
    private float valZinc = 0;
    private float valCarbohydrate = 0;
    private float valRe = 0;
    private float valCholesterol = 0;
    private float valRetinol = 0;
    private float valFiber = 0;
    private float valRae = 0;
    private float valAshes = 0;
    private float valThiamine = 0;
    private float valCalcium = 0;
    private float valRiboflavin = 0;
    private float valMagnesium = 0;
    private float valPyridoxine = 0;
    private float valManganase = 0;
    private float valNiacin = 0;
    private float valPhosphorus = 0;
    private float valVitaminC = 0;
    private float valIron = 0;

    private AuthUser user;
    private List<Ingredient> ingredients;
    private ProgressBar progressBar34;
    private int disableProgressBar = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frag_visu_refeicao_nutricionais, container, false);
        retrofit = Consts.connection();
        this.context = getContext();
        getReferencesComponentes();

        Meal meal = (Meal) getArguments().getSerializable("meal");
        user = (AuthUser) getArguments().getSerializable("user");
        ingredients = meal.getIngredients();
//        clearValues();

        for (int i = 0; i < ingredients.size(); i++) {

            String id_ingredient = ingredients.get(i).getIdFood();

            FoodApi serviceApi = retrofit.create(FoodApi.class);
            Call<Food> call = serviceApi.getFood(id_ingredient, "bearer " + user.getToken());

            JsonObject x = new JsonObject();
            x.addProperty("token", user.getToken());
            x.addProperty("id_ingredient", id_ingredient);
            x.addProperty("portion", ingredients.get(i).getPortion());
            x.addProperty("qtdPortion", ingredients.get(i).getQtdPortion());

            if(run == false){
                progressBar34.setVisibility(View.INVISIBLE);
            }else{
                VisualizaRefeicaoIngredientesTask task = new VisualizaRefeicaoIngredientesTask();
                task.execute(x);
            }
        }

        progressBar34 = v.findViewById(R.id.progressBar34);
        progressBar34.setVisibility(View.VISIBLE);

        imgbuttonVisuRefeicaoRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTablePart2VisuRefeicao(null);
            }
        });

        setTablePart1VisuRefeicao(null);

        return v;
    }

    private void clearValues(){
        valHumidity = 0;
        valSodium = 0;
        valEnergy = 0;
        valPotassium = 0;
        valProtein = 0;
        valCopper = 0;
        valLipids = 0;
        valZinc = 0;
        valCarbohydrate = 0;
        valRe = 0;
        valCholesterol = 0;
        valRetinol = 0;
        valFiber = 0;
        valRae = 0;
        valAshes = 0;
        valThiamine = 0;
        valCalcium = 0;
        valRiboflavin = 0;
        valMagnesium = 0;
        valPyridoxine = 0;
        valManganase = 0;
        valNiacin = 0;
        valPhosphorus = 0;
        valVitaminC = 0;
        valIron = 0;
    }

    public void getReferencesComponentes() {

        tableVisuRefeicaoTableNutrientes = v.findViewById(R.id.tableVisuRefeicaoTableNutrientes);
        imgbuttonVisuRefeicaoLeft = v.findViewById(R.id.imgbuttonVisuRefeicaoLeft);
        imgbuttonVisuRefeicaoRight = v.findViewById(R.id.imgbuttonVisuRefeicaoRight);;

        // Elementos da tableView
        txtVisuRefeicaoHumiditySodium = v.findViewById(R.id.txtVisuRefeicaoHumiditySodium);
        txtVisuRefeicaoEnergyPotassium = v.findViewById(R.id.txtVisuRefeicaoEnergyPotassium);
        txtVisuRefeicaoProteinCopper = v.findViewById(R.id.txtVisuRefeicaoProteinCopper);
        txtVisuRefeicaoLipidsZinc = v.findViewById(R.id.txtVisuRefeicaoLipidsZinc);
        txtVisuRefeicaoCarbohydrateRe = v.findViewById(R.id.txtVisuRefeicaoCarbohydrateRe);

        txtVisuRefeicaoCholesterolRetinol = v.findViewById(R.id.txtVisuRefeicaoCholesterolRetinol);
        txtVisuRefeicaoFiberRae = v.findViewById(R.id.txtVisuRefeicaoFiberRae);
        txtVisuRefeicaoAshesThiamine = v.findViewById(R.id.txtVisuRefeicaoAshesThiamine);
        txtVisuRefeicaoCalciumRiboflavin = v.findViewById(R.id.txtVisuRefeicaoCalciumRiboflavin);
        txtVisuRefeicaoMagnesiumPyridoxine = v.findViewById(R.id.txtVisuRefeicaoMagnesiumPyridoxine);
        txtVisuRefeicaoManganaseNiacin = v.findViewById(R.id.txtVisuRefeicaoManganaseNiacin);
        txtVisuRefeicaoPhosphorusVitaminC = v.findViewById(R.id.txtVisuRefeicaoPhosphorusVitaminC);
        txtVisuRefeicaoIron = v.findViewById(R.id.txtVisuRefeicaoIron);

        txtVisuRefeicaoHumiditySodiumV = v.findViewById(R.id.txtVisuRefeicaoHumiditySodiumV);
        txtVisuRefeicaoEnergyPotassiumV = v.findViewById(R.id.txtVisuRefeicaoEnergyPotassiumV);
        txtVisuRefeicaoProteinCopperV = v.findViewById(R.id.txtVisuRefeicaoProteinCopperV);
        txtVisuRefeicaoLipidsZincV = v.findViewById(R.id.txtVisuRefeicaoLipidsZincV);
        txtVisuRefeicaoCholesterolRetinolV = v.findViewById(R.id.txtVisuRefeicaoCholesterolRetinolV);
        txtVisuRefeicaoCarbohydrateReV = v.findViewById(R.id.txtVisuRefeicaoCarbohydrateReV);
        txtVisuRefeicaoFiberRaeV = v.findViewById(R.id.txtVisuRefeicaoFiberRaeV);
        txtVisuRefeicaoAshesThiamineV = v.findViewById(R.id.txtVisuRefeicaoAshesThiamineV);
        txtVisuRefeicaoCalciumRiboflavinV = v.findViewById(R.id.txtVisuRefeicaoCalciumRiboflavinV);
        txtVisuRefeicaoMagnesiumPyridoxineV = v.findViewById(R.id.txtVisuRefeicaoMagnesiumPyridoxineV);
        txtVisuRefeicaoManganaseNiacinV = v.findViewById(R.id.txtVisuRefeicaoManganaseNiacinV);
        txtVisuRefeicaoPhosphorusVitaminCV = v.findViewById(R.id.txtVisuRefeicaoPhosphorusVitaminCV);
        txtVisuRefeicaoIronV = v.findViewById(R.id.txtVisuRefeicaoIronV);
    }

    public void setTablePart1VisuRefeicao(View view){

        if (user.getPremium()) {

            imgbuttonVisuRefeicaoLeft.setClickable(false);
            imgbuttonVisuRefeicaoLeft.setImageResource(R.drawable.ic_navigate_before_gray);
            imgbuttonVisuRefeicaoRight.setClickable(true);
            imgbuttonVisuRefeicaoRight.setImageResource(R.drawable.ic_navigate_next_black);

            txtVisuRefeicaoHumiditySodium.setText("Umidade");
            txtVisuRefeicaoHumiditySodiumV.setText(Float.toString(valHumidity) + "%");

            txtVisuRefeicaoEnergyPotassium.setText("Energia");
            txtVisuRefeicaoEnergyPotassiumV.setText(Float.toString(valEnergy) + "Kj");

            txtVisuRefeicaoProteinCopper.setText("Proteína");
            txtVisuRefeicaoProteinCopperV.setText(Float.toString(valProtein) + "g");

            txtVisuRefeicaoLipidsZinc.setText("Lipídeos");
            txtVisuRefeicaoLipidsZincV.setText(Float.toString(valLipids) + "g");


            txtVisuRefeicaoCholesterolRetinol.setText("Colesterol");
            txtVisuRefeicaoCholesterolRetinolV.setText(Float.toString(valCholesterol) + "g");


            txtVisuRefeicaoCarbohydrateRe.setText("Carboidrato");
            txtVisuRefeicaoCarbohydrateReV.setText(Float.toString(valCarbohydrate) + "g");


            txtVisuRefeicaoFiberRae.setText("Fibra Alimentar");
            txtVisuRefeicaoFiberRaeV.setText(Float.toString(valFiber) + "g");


            txtVisuRefeicaoAshesThiamine.setText("Cinzas");
            txtVisuRefeicaoAshesThiamineV.setText(Float.toString(valAshes) + "g");


            txtVisuRefeicaoCalciumRiboflavin.setText("Cálcio");
            txtVisuRefeicaoCalciumRiboflavinV.setText(Float.toString(valRiboflavin) + "g");


            txtVisuRefeicaoMagnesiumPyridoxine.setText("Magnésio");
            txtVisuRefeicaoMagnesiumPyridoxineV.setText(Float.toString(valMagnesium) + "g");


            txtVisuRefeicaoManganaseNiacin.setText("Manganês");
            txtVisuRefeicaoManganaseNiacinV.setText(Float.toString(valManganase) + "g");


            txtVisuRefeicaoPhosphorusVitaminC.setText("Fósforo");
            txtVisuRefeicaoPhosphorusVitaminCV.setText(Float.toString(valPhosphorus) + "g");


            txtVisuRefeicaoIron.setVisibility(TextView.VISIBLE);
            txtVisuRefeicaoIronV.setVisibility(TextView.VISIBLE);

            txtVisuRefeicaoIron.setText("Ferro");
            txtVisuRefeicaoIronV.setText(Float.toString(valIron) + "g");

        }else{

            int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 167, getResources().getDisplayMetrics());
            tableVisuRefeicaoTableNutrientes.getLayoutParams().height = height;

            imgbuttonVisuRefeicaoLeft.setClickable(false);
            imgbuttonVisuRefeicaoLeft.setImageResource(R.drawable.ic_navigate_before_gray);
            imgbuttonVisuRefeicaoRight.setClickable(false);
            imgbuttonVisuRefeicaoRight.setImageResource(R.drawable.ic_navigate_next_gray);


            txtVisuRefeicaoHumiditySodium.setText("Umidade");
            txtVisuRefeicaoHumiditySodiumV.setText(Float.toString(valHumidity) + "%");

            txtVisuRefeicaoEnergyPotassium.setText("Energia");
            txtVisuRefeicaoEnergyPotassiumV.setText(Float.toString(valEnergy) + "Kj");

            txtVisuRefeicaoProteinCopper.setText("Proteína");
            txtVisuRefeicaoProteinCopperV.setText(Float.toString(valProtein) + "g");

            txtVisuRefeicaoLipidsZinc.setText("Lipídeos");
            txtVisuRefeicaoLipidsZincV.setText(Float.toString(valLipids) + "g");

            txtVisuRefeicaoCarbohydrateRe.setText("Carboidrato");
            txtVisuRefeicaoCholesterolRetinolV.setText(Float.toString(valCholesterol) + "g");

            txtVisuRefeicaoCholesterolRetinol.setVisibility(TextView.INVISIBLE);
            txtVisuRefeicaoFiberRae.setVisibility(TextView.INVISIBLE);
            txtVisuRefeicaoAshesThiamine.setVisibility(TextView.INVISIBLE);
            txtVisuRefeicaoCalciumRiboflavin.setVisibility(TextView.INVISIBLE);
            txtVisuRefeicaoMagnesiumPyridoxine.setVisibility(TextView.INVISIBLE);
            txtVisuRefeicaoManganaseNiacin.setVisibility(TextView.INVISIBLE);
            txtVisuRefeicaoPhosphorusVitaminC.setVisibility(TextView.INVISIBLE);
            txtVisuRefeicaoIron.setVisibility(TextView.INVISIBLE);

            txtVisuRefeicaoCholesterolRetinolV.setVisibility(TextView.INVISIBLE);
            txtVisuRefeicaoFiberRaeV.setVisibility(TextView.INVISIBLE);
            txtVisuRefeicaoAshesThiamineV.setVisibility(TextView.INVISIBLE);
            txtVisuRefeicaoCalciumRiboflavinV.setVisibility(TextView.INVISIBLE);
            txtVisuRefeicaoMagnesiumPyridoxineV.setVisibility(TextView.INVISIBLE);
            txtVisuRefeicaoManganaseNiacinV.setVisibility(TextView.INVISIBLE);
            txtVisuRefeicaoPhosphorusVitaminCV.setVisibility(TextView.INVISIBLE);
            txtVisuRefeicaoIronV.setVisibility(TextView.INVISIBLE);
        }
    }

    public void setTablePart2VisuRefeicao(View view){


        imgbuttonVisuRefeicaoLeft.setClickable(true);
        imgbuttonVisuRefeicaoLeft.setImageResource(R.drawable.ic_navigate_before_black);
        imgbuttonVisuRefeicaoRight.setClickable(false);
        imgbuttonVisuRefeicaoRight.setImageResource(R.drawable.ic_navigate_next_gray);

        txtVisuRefeicaoHumiditySodium.setText("Sódio");
        txtVisuRefeicaoLipidsZincV.setText(Float.toString(valSodium) + "g");

        txtVisuRefeicaoEnergyPotassium.setText("Potássio");
        txtVisuRefeicaoLipidsZincV.setText(Float.toString(valPotassium) + "g");

        txtVisuRefeicaoProteinCopper.setText("Cobre");
        txtVisuRefeicaoLipidsZincV.setText(Float.toString(valCopper) + "g");

        txtVisuRefeicaoLipidsZinc.setText("Zinco");
        txtVisuRefeicaoLipidsZincV.setText(Float.toString(valZinc) + "g");

        txtVisuRefeicaoCholesterolRetinol.setText("Retinol");
        txtVisuRefeicaoLipidsZincV.setText(Float.toString(valRetinol) + "g");

        txtVisuRefeicaoCarbohydrateRe.setText("RE");
        txtVisuRefeicaoLipidsZincV.setText(Float.toString(valRe) + "g");

        txtVisuRefeicaoFiberRae.setText("RAE");
        txtVisuRefeicaoLipidsZincV.setText(Float.toString(valRae) + "g");

        txtVisuRefeicaoAshesThiamine.setText("Tiamina");
        txtVisuRefeicaoLipidsZincV.setText(Float.toString(valThiamine) + "g");

        txtVisuRefeicaoCalciumRiboflavin.setText("Riboflavina");
        txtVisuRefeicaoLipidsZincV.setText(Float.toString(valRiboflavin) + "g");

        txtVisuRefeicaoMagnesiumPyridoxine.setText("Piridoxina");
        txtVisuRefeicaoLipidsZincV.setText(Float.toString(valPyridoxine) + "g");

        txtVisuRefeicaoManganaseNiacin.setText("Niacina");
        txtVisuRefeicaoLipidsZincV.setText(Float.toString(valNiacin) + "g");

        txtVisuRefeicaoPhosphorusVitaminC.setText("Vitamina C");
        txtVisuRefeicaoLipidsZincV.setText(Float.toString(valVitaminC) + "g");

        txtVisuRefeicaoIron.setVisibility(TextView.INVISIBLE);
        txtVisuRefeicaoIronV.setVisibility(TextView.INVISIBLE);
    }

    private String checkValue(String value){
        if(value.intern() == "Null"){
            return "0";
        }else{
            return value;
        }
    }

    @Override
    public SharedPreferences getPreferences() {
        return null;
    }

    public void sumValues(){

        for(int i = 0; i < foods.size(); i++ ){
            Food food_ingredient = foods.get(i);
            valHumidity += Float.parseFloat(checkValue(food_ingredient.getHumidity().value));
            valSodium += Float.parseFloat(checkValue(food_ingredient.getSodium().value));
            valEnergy += Float.parseFloat(checkValue(food_ingredient.getEnergy().value));
            valPotassium += Float.parseFloat(checkValue(food_ingredient.getPotassium().value));
            valProtein += Float.parseFloat(checkValue(food_ingredient.getProtein().value));
            valCopper += Float.parseFloat(checkValue(food_ingredient.getCopper().value));
            valLipids += Float.parseFloat(checkValue(food_ingredient.getLipids().value));
            valZinc += Float.parseFloat(checkValue(food_ingredient.getZinc().value));
            valCarbohydrate += Float.parseFloat(checkValue(food_ingredient.getCarbohydrate().value));
            valRe += Float.parseFloat(checkValue(food_ingredient.getRe().value));
            valCholesterol += Float.parseFloat(checkValue(food_ingredient.getCholesterol().value));
            valRetinol += Float.parseFloat(checkValue(food_ingredient.getRetinol().value));
            valFiber += Float.parseFloat(checkValue(food_ingredient.getFoodFiber().value));
            valRae += Float.parseFloat(checkValue(food_ingredient.getRae().value));
            valAshes += Float.parseFloat(checkValue(food_ingredient.getAshes().value));
            valThiamine += Float.parseFloat(checkValue(food_ingredient.getThiamine().value));
            valCalcium += Float.parseFloat(checkValue(food_ingredient.getCalcium().value));
            valRiboflavin += Float.parseFloat(checkValue(food_ingredient.getRiboflavin().value));
            valMagnesium += Float.parseFloat(checkValue(food_ingredient.getMagnesium().value));
            valPyridoxine += Float.parseFloat(checkValue(food_ingredient.getPyridoxine().value));
            valManganase += Float.parseFloat(checkValue(food_ingredient.getManganese().value));
            valNiacin += Float.parseFloat(checkValue(food_ingredient.getNiacin().value));
            valPhosphorus += Float.parseFloat(checkValue(food_ingredient.getPhosphorus().value));
            valVitaminC += Float.parseFloat(checkValue(food_ingredient.getVitaminc().value));
            valIron += Float.parseFloat(checkValue(food_ingredient.getIron().value));

            setTablePart1VisuRefeicao(null);
        }
    }

    class VisualizaRefeicaoIngredientesTask extends AsyncTask<JsonObject, Call, Food> {
        @Override
        protected Food doInBackground(JsonObject... json) {
            JsonObject food = json[0];
            JsonElement id_ingredient = food.get("id_ingredient");
            JsonElement token = food.get("token");

            FoodApi serviceApi = retrofit.create(FoodApi.class);
            Call<Food> call = serviceApi.getFood(id_ingredient.getAsString(), "bearer " + token.getAsString());

            Food resp = null;
            try {
                resp = call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return resp.getFood();
        }

        @Override
        protected void onPostExecute(Food food_ingredient) {
            super.onPostExecute(food_ingredient);
            disableProgressBar++;
            foods.add(food_ingredient);
            // if para desativar o progress bar
            if (run && (disableProgressBar >= ingredients.size())) {
                sumValues();
                run = false;
                progressBar34.setVisibility(View.INVISIBLE);
            }
        }
    }
}
