package com.example.nutrionall.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.nutrionall.R;
import com.example.nutrionall.activity.holders.SimilaresHolder;
import com.example.nutrionall.models.Food.Food;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SimilaresAdapter extends RecyclerView.Adapter<SimilaresHolder> {

    private final Context context;
    private final List<Food> mSimilars;

    public SimilaresAdapter(Context context,ArrayList foods) {
        this.context = context;
        mSimilars = foods;
    }

    @Override
    public SimilaresHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SimilaresHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_similares, parent, false));
    }

    @SuppressWarnings("ResourceType")
    @Override
    public void onBindViewHolder(SimilaresHolder holder, int position) {
        holder.txtLayoutBuscaNome.setText(mSimilars.get(position).getName().getValue());
        holder.txtLayoutBuscaCarboidrato.setText(mSimilars.get(position).getCarbohydrate().getValue());

        String txtCategoriaAlimento = mSimilars.get(position).getCategory().getValue();
        holder.txtLayoutBuscaCategoria.setText(txtCategoriaAlimento);

        Resources res = context.getResources();
        TypedArray categorias = res.obtainTypedArray(R.array.categorias);


        switch (txtCategoriaAlimento) {
            case "carnes e derivados":
                holder.imgLayoutBuscaImgCategoria.setImageDrawable(categorias.getDrawable(0));
                break;
            case "frutas e derivados":
                holder.imgLayoutBuscaImgCategoria.setImageDrawable(categorias.getDrawable(1));
                break;
            case "verduras, hortaliças e derivados":
                holder.imgLayoutBuscaImgCategoria.setImageDrawable(categorias.getDrawable(2));
                break;
            case "cereais e derivados":
                holder.imgLayoutBuscaImgCategoria.setImageDrawable(categorias.getDrawable(3));
                break;
            case "pescados e frutos do mar":
                holder.imgLayoutBuscaImgCategoria.setImageDrawable(categorias.getDrawable(4));
                break;
            case "leguminosas e derivados":
                holder.imgLayoutBuscaImgCategoria.setImageDrawable(categorias.getDrawable(5));
                break;
            case "alimentos preparados":
                holder.imgLayoutBuscaImgCategoria.setImageDrawable(categorias.getDrawable(6));
                break;
            case "leite e derivados":
                holder.imgLayoutBuscaImgCategoria.setImageDrawable(categorias.getDrawable(7));
                break;
            case "produtos açucarados":
                holder.imgLayoutBuscaImgCategoria.setImageDrawable(categorias.getDrawable(8));
                break;
            case "bebidas (alcoólicas e não alcoólicas)":
                holder.imgLayoutBuscaImgCategoria.setImageDrawable(categorias.getDrawable(9));
                break;
            case "ovos e derivados":
                holder.imgLayoutBuscaImgCategoria.setImageDrawable(categorias.getDrawable(10));
                break;
            case "miscelâneas":
                holder.imgLayoutBuscaImgCategoria.setImageDrawable(categorias.getDrawable(11));
                break;
            case "outros alimentos industrializados":
                holder.imgLayoutBuscaImgCategoria.setImageDrawable(categorias.getDrawable(12));
                break;
            default:
                holder.imgLayoutBuscaImgCategoria.setImageDrawable(categorias.getDrawable(2));
        }
    }

    @Override
    public int getItemCount() {
        return mSimilars != null ? mSimilars.size() : 0;
    }
}
