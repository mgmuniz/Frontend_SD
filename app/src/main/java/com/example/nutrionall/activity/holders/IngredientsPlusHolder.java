package com.example.nutrionall.activity.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nutrionall.R;

public class IngredientsPlusHolder extends RecyclerView.ViewHolder {

    public TextView txtVisualizarIngredienteNome;
    public TextView txtVisualizarIngredientePorcao;
    public TextView txtVisualizarIngredienteQtde;
    public ImageView imgVisualizarIngredienteCategoria;

    public IngredientsPlusHolder(View itemView) {
        super(itemView);
        txtVisualizarIngredienteNome = (TextView) itemView.findViewById(R.id.txtVisualizarIngredienteNome);
        txtVisualizarIngredientePorcao = (TextView) itemView.findViewById(R.id.txtVisualizarIngredientePorcao);
        txtVisualizarIngredienteQtde = (TextView) itemView.findViewById(R.id.txtVisualizarIngredienteQtde);
        imgVisualizarIngredienteCategoria = (ImageView) itemView.findViewById(R.id.imgVisualizarIngredienteCategoria);

    }
}
