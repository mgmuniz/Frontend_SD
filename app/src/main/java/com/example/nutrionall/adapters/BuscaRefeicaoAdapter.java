package com.example.nutrionall.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nutrionall.R;
import com.example.nutrionall.models.Meal.Meal;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BuscaRefeicaoAdapter extends ArrayAdapter<Meal> {

    private final Context context;
    private final ArrayList<Meal> elementos;

    public BuscaRefeicaoAdapter(Context context, ArrayList<Meal> elementos){
        super(context, R.layout.layout_busca_refeicao,elementos);
        this.context = context;
        this.elementos = elementos;
    }

    @SuppressWarnings("ResourceType")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.layout_busca_refeicao, parent, false);

        Log.d("Setar valores", "Comecei a setar");

        TextView txtLayoutBuscaRefeicaoNome = (TextView) rowView.findViewById(R.id.txtLayoutBuscaRefeicaoNome);
        TextView txtLayoutBuscaRefeicaoCategoria = (TextView) rowView.findViewById(R.id.txtLayoutBuscaRefeicaoCategoria);
        TextView txtLayoutBuscaRefeicaoCriador = (TextView) rowView.findViewById(R.id.txtLayoutBuscaRefeicaoCriador);
        ImageView imgLayoutBuscaRefeicaoImgRefeicao = (ImageView) rowView.findViewById(R.id.imgLayoutBuscaRefeicaoImgRefeicao);


        txtLayoutBuscaRefeicaoNome.setText(String.valueOf(elementos.get(position).getName()));
        int value_category = elementos.get(position).getClassification();
        if(value_category == 1){txtLayoutBuscaRefeicaoCategoria.setText("Jantar");}
        if(value_category == 2){txtLayoutBuscaRefeicaoCategoria.setText("Almoço");}
        if(value_category == 3){txtLayoutBuscaRefeicaoCategoria.setText("Lanche");}
        if(value_category == 4){txtLayoutBuscaRefeicaoCategoria.setText("Desjejum");}

//        txtLayoutBuscaRefeicaoCriador.setText(("Criador: " + elementos.get(position).getIdUser()));

        Picasso.get().load(elementos.get(position).getUrlImg()).fit().centerCrop().into(imgLayoutBuscaRefeicaoImgRefeicao);

        Log.d("Setar valores", "Terminei de setar");

        return rowView;
    }
}
