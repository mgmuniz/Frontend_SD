package com.example.nutrionall.activity.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.nutrionall.R;

public class IngredientesHolder extends RecyclerView.ViewHolder {
    public TextView txtLayoutIngredientesNome;
    public TextView txtLayoutIngredientesPorcao;
    public TextView txtLayoutIngredientesQtde;
    public ImageButton btnLayoutIngredientesDelete;

    public IngredientesHolder(View itemView) {
        super(itemView);
        txtLayoutIngredientesNome = (TextView) itemView.findViewById(R.id.txtLayoutIngredientesNome);
        txtLayoutIngredientesPorcao = (TextView) itemView.findViewById(R.id.txtLayoutIngredientesPorcao);
        txtLayoutIngredientesQtde = (TextView) itemView.findViewById(R.id.txtLayoutIngredientesQtde);
        btnLayoutIngredientesDelete = (ImageButton) itemView.findViewById(R.id.btnLayoutIngredientesDelete);
    }
}
