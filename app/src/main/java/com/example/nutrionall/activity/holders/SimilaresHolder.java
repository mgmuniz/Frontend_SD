package com.example.nutrionall.activity.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nutrionall.R;

public class SimilaresHolder extends RecyclerView.ViewHolder {
    public TextView txtLayoutBuscaNome;
    public TextView txtLayoutBuscaCategoria;
    public TextView txtLayoutBuscaCarboidrato;
    public ImageView imgLayoutBuscaImgCategoria;

    public SimilaresHolder(View itemView) {
        super(itemView);
        txtLayoutBuscaNome = (TextView) itemView.findViewById(R.id.txtLayoutBuscaNome);
        txtLayoutBuscaCategoria = (TextView) itemView.findViewById(R.id.txtLayoutBuscaCategoria);
        txtLayoutBuscaCarboidrato = (TextView) itemView.findViewById(R.id.txtLayoutBuscaCarboidrato);
        imgLayoutBuscaImgCategoria = (ImageView) itemView.findViewById(R.id.imgLayoutBuscaImgCategoria);
    }
}
