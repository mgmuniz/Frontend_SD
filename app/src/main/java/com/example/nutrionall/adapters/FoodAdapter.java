package com.example.nutrionall.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nutrionall.R;
import com.example.nutrionall.models.Food.Food;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FoodAdapter extends ArrayAdapter<Food> {

    private final Context context;
    private final ArrayList<Food> elementos;

    public FoodAdapter(Context context, ArrayList<Food> elementos){
        super(context, R.layout.layout_busca,elementos);
        this.context = context;
        this.elementos = elementos;
    }


    @SuppressWarnings("ResourceType")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.layout_busca, parent, false);

        TextView nomeAlimento = (TextView) rowView.findViewById(R.id.txtLayoutBuscaNome);
        TextView categoriaAlimento = (TextView) rowView.findViewById(R.id.txtLayoutBuscaCategoria);
        TextView energiaAlimento = (TextView) rowView.findViewById(R.id.txtLayoutBuscaEnergia);
        TextView carboidratoAlimento = (TextView) rowView.findViewById(R.id.txtLayoutBuscaCarboidrato);
        ImageView imagemCategoriaAlimento = (ImageView) rowView.findViewById(R.id.imgLayoutBuscaImgCategoria);

        String txtCategoriaAlimento = elementos.get(position).getCategory().getValue();

        nomeAlimento.setText(elementos.get(position).getName().getValue());
        categoriaAlimento.setText(txtCategoriaAlimento);
        energiaAlimento.setText("Energia (Kj): " + elementos.get(position).getEnergy().getValue());
        carboidratoAlimento.setText("Carboidrato (g): " + elementos.get(position).getCarbohydrate().getValue());

        Resources res = context.getResources();
        TypedArray categorias = res.obtainTypedArray(R.array.categorias);


        switch (txtCategoriaAlimento) {
            case "carnes e derivados":
                imagemCategoriaAlimento.setImageDrawable(categorias.getDrawable(0));
                break;
            case "frutas e derivados":
                imagemCategoriaAlimento.setImageDrawable(categorias.getDrawable(1));
                break;
            case "verduras, hortaliças e derivados":
                imagemCategoriaAlimento.setImageDrawable(categorias.getDrawable(2));
                break;
            case "cereais e derivados":
                imagemCategoriaAlimento.setImageDrawable(categorias.getDrawable(3));
                break;
            case "pescados e frutos do mar":
                imagemCategoriaAlimento.setImageDrawable(categorias.getDrawable(4));
                break;
            case "leguminosas e derivados":
                imagemCategoriaAlimento.setImageDrawable(categorias.getDrawable(5));
                break;
            case "alimentos preparados":
                imagemCategoriaAlimento.setImageDrawable(categorias.getDrawable(6));
                break;
            case "leite e derivados":
                imagemCategoriaAlimento.setImageDrawable(categorias.getDrawable(7));
                break;
            case "produtos açucarados":
                imagemCategoriaAlimento.setImageDrawable(categorias.getDrawable(8));
                break;
            case "bebidas (alcoólicas e não alcoólicas)":
                imagemCategoriaAlimento.setImageDrawable(categorias.getDrawable(9));
                break;
            case "ovos e derivados":
                imagemCategoriaAlimento.setImageDrawable(categorias.getDrawable(10));
                break;
            case "miscelâneas":
                imagemCategoriaAlimento.setImageDrawable(categorias.getDrawable(11));
                break;
            case "outros alimentos industrializados":
                imagemCategoriaAlimento.setImageDrawable(categorias.getDrawable(12));
                break;
            default:
                imagemCategoriaAlimento.setImageDrawable(categorias.getDrawable(2));
        }

        return rowView;
    }
}
