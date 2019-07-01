package com.example.nutrionall.activity.fragmentVisualizaRefeicao;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nutrionall.R;
import com.example.nutrionall.api.Meal.MealApi;
import com.example.nutrionall.models.Meal.Evaluate;
import com.example.nutrionall.models.Meal.Meal;
import com.example.nutrionall.models.User.AuthUser;
import com.example.nutrionall.utils.Consts;
import com.example.nutrionall.utils.Methods;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class VisuRefeicaoDescricao extends Fragment implements Methods {
    // componentes
    private Retrofit retrofit;
    private ImageView imgVisualizaRefeicao;
    private TextView nomeVisualizaRefeicao;
    private TextView descVisualizaRefeicao;
    private RatingBar ratingBarVisualizaRefeicao;
    private ImageView btnFavoriteRefeicao;
    private View v;
    private String evaluateID;
    private boolean evaluateOk = false;

    private boolean favoritou = false; // Verificar se o user já favoritou a refeição (mudar ícone)

    private Meal meal;
    private AuthUser user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final String TAG = "tab tab";
        retrofit = Consts.connection();

        v = inflater.inflate(R.layout.frag_visu_refeicao_descricao, container, false);

        // deve ser chamado após a instância da view
        getReferencesComponentes();

        // recuperando os dados para mostrar no tabview
        meal = (Meal) getArguments().getSerializable("meal");
        user = (AuthUser) getArguments().getSerializable("user");

        nomeVisualizaRefeicao.setText(meal.getName());
        descVisualizaRefeicao.setText(meal.getDescription());
        ratingBarVisualizaRefeicao.setRating(meal.getAvgEvaluation());

        Log.d(TAG, "onCreateView: " + meal.get_id());

        ratingBar();

        MealApi serviceApi = retrofit.create(MealApi.class);
        Call<JsonObject> call = serviceApi.getFavorite(meal.get_id(), "bearer " + user.getToken());

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()){
                    Log.d("favoritar", "onResponse: " + response.toString());
                    favoritou = true;
                    btnFavoriteRefeicao.setImageResource(R.drawable.ic_favorite_red_24dp);
                }else{
                    btnFavoriteRefeicao.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    favoritou = false;
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

        Picasso.get().load(meal.getUrlImg()).fit().centerCrop().into(imgVisualizaRefeicao);
        return v;
    }

    public void favoritar(){
        String TAG = "favoritar";
        Log.d(TAG, "favoritar: ");
    }

    private void ratingBar(){
        final String TAG = "addNewEvaluate";

        MealApi serviceApi = retrofit.create(MealApi.class);
        Call<JsonObject> call = serviceApi.getEvaluation(meal.get_id(), "bearer " + user.getToken());

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()){
                    ratingBarVisualizaRefeicao.setRating(response.body().get("evaluation").getAsInt());
                    evaluateOk = true;
                    evaluateID = response.body().get("_id").getAsString();
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });



        ratingBarVisualizaRefeicao.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, final float v, boolean b) {
                Log.d(TAG, "addNewEvaluate: iniciada");

                Log.d(TAG, "onRatingChanged: " + v);
                Log.d(TAG, "onRatingChanged: " + b);

                if(b) {
                    if(evaluateOk == true){
                        Log.d(TAG, "onRatingChanged: entrei");
                        // atualiza
                        JsonObject evaluation = new JsonObject();
                        evaluation.addProperty("evaluation", v);

                        MealApi serviceApi = retrofit.create(MealApi.class);
                        Call<JsonObject> call = serviceApi.changeEvaluation(evaluateID, evaluation,"bearer " + user.getToken());

                        call.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                Log.d(TAG, "onResponse: " + response.toString());
                                Toast.makeText(getContext(), response.body().get("msg").getAsString(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {

                            }
                        });

                    }else {
                        int evaluation = ratingBarVisualizaRefeicao.getNumStars();

                        Evaluate newEvaluate = new Evaluate();
                        newEvaluate.setEvaluation(evaluation);
                        newEvaluate.setMealID(meal.get_id());

                        MealApi serviceApi = retrofit.create(MealApi.class);
                        Call<Evaluate> call = serviceApi.newEvaluate(newEvaluate, "bearer " + user.getToken());


                        call.enqueue(new Callback<Evaluate>() {
                            @Override
                            public void onResponse(Call<Evaluate> call, Response<Evaluate> response) {
                                if (response.isSuccessful()) {
                                    Log.d(TAG, "onResponse: " + response.body().getMsg());
                                } else {
                                    try {
                                        JSONObject msg = new JSONObject(response.errorBody().string());
                                        Toast.makeText(getContext(), msg.getString("msg"), Toast.LENGTH_LONG).show();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<Evaluate> call, Throwable t) {
                                Log.d(TAG, "onFailure: " + t.toString());
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    public void getReferencesComponentes() {
        imgVisualizaRefeicao = v.findViewById(R.id.imgVisualizaRefeicao);
        nomeVisualizaRefeicao = v.findViewById(R.id.nomeVisualizaRefeicao);
        descVisualizaRefeicao = v.findViewById(R.id.descVisualizaRefeicao);
        ratingBarVisualizaRefeicao = v.findViewById(R.id.ratingBarVisualizaRefeicao);
        btnFavoriteRefeicao = v.findViewById(R.id.btnFavoriteRefeicao);
    }

    @Override
    public SharedPreferences getPreferences() {
        return null;
    }
}
