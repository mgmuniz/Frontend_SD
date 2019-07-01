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

import com.example.nutrionall.R;
import com.example.nutrionall.models.Meal.Meal;
import com.example.nutrionall.utils.Methods;
import com.squareup.picasso.Picasso;

import java.io.Serializable;

public class VisuRefeicaoDescricao extends Fragment implements Methods {
    // componentes
    private ImageView imgVisualizaRefeicao;
    private TextView nomeVisualizaRefeicao;
    private TextView descVisualizaRefeicao;
    private RatingBar ratingBarVisualizaRefeicao;
    private ImageView btnFavoriteRefeicao;
    private View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String TAG = "tab tab";

        v = inflater.inflate(R.layout.frag_visu_refeicao_descricao, container, false);

        // deve ser chamado após a instância da view
        getReferencesComponentes();

        // recuperando os dados para mostrar no tabview
        Meal meal = (Meal) getArguments().getSerializable("meal");

        nomeVisualizaRefeicao.setText(meal.getName());
        descVisualizaRefeicao.setText(meal.getDescription());
        Picasso.get().load(meal.getUrlImg()).fit().centerCrop().into(imgVisualizaRefeicao);

        return v;
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
