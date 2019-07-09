package com.example.nutrionall.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.nutrionall.R;
import com.example.nutrionall.activity.holders.IngredientsPlusHolder;
import com.example.nutrionall.models.Meal.IngredientPlus;

import java.util.ArrayList;
import java.util.List;

public class IngredientPlusAdapter extends RecyclerView.Adapter<IngredientsPlusHolder> {

    private final List<IngredientPlus> mIngredientes;
    private final Context context;

    public IngredientPlusAdapter(Context context, ArrayList ingredientes) {
        this.context = context;
        mIngredientes = ingredientes;
    }

    @Override
    public IngredientsPlusHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new IngredientsPlusHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_exibir_ingredientes, parent, false));
    }

    @SuppressWarnings("ResourceType")
    @Override
    public void onBindViewHolder(IngredientsPlusHolder holder, final int position) {
        holder.txtVisualizarIngredienteNome.setText(mIngredientes.get(position).getNameFood());
        holder.txtVisualizarIngredientePorcao.setText("Porção: " + mIngredientes.get(position).getPortion() + "g");
        holder.txtVisualizarIngredienteQtde.setText("Quantidade: " + mIngredientes.get(position).getQtdPortion());

        String txtCategoriaAlimento = mIngredientes.get(position).getCategory();

        Resources res = context.getResources();
        TypedArray categorias = res.obtainTypedArray(R.array.categorias);


        switch (txtCategoriaAlimento) {
            case "carnes e derivados":
                holder.imgVisualizarIngredienteCategoria.setImageDrawable(categorias.getDrawable(0));
                break;
            case "frutas e derivados":
                holder.imgVisualizarIngredienteCategoria.setImageDrawable(categorias.getDrawable(1));
                break;
            case "verduras, hortaliças e derivados":
                holder.imgVisualizarIngredienteCategoria.setImageDrawable(categorias.getDrawable(2));
                break;
            case "cereais e derivados":
                holder.imgVisualizarIngredienteCategoria.setImageDrawable(categorias.getDrawable(3));
                break;
            case "pescados e frutos do mar":
                holder.imgVisualizarIngredienteCategoria.setImageDrawable(categorias.getDrawable(4));
                break;
            case "leguminosas e derivados":
                holder.imgVisualizarIngredienteCategoria.setImageDrawable(categorias.getDrawable(5));
                break;
            case "alimentos preparados":
                holder.imgVisualizarIngredienteCategoria.setImageDrawable(categorias.getDrawable(6));
                break;
            case "leite e derivados":
                holder.imgVisualizarIngredienteCategoria.setImageDrawable(categorias.getDrawable(7));
                break;
            case "produtos açucarados":
                holder.imgVisualizarIngredienteCategoria.setImageDrawable(categorias.getDrawable(8));
                break;
            case "bebidas (alcoólicas e não alcoólicas)":
                holder.imgVisualizarIngredienteCategoria.setImageDrawable(categorias.getDrawable(9));
                break;
            case "ovos e derivados":
                holder.imgVisualizarIngredienteCategoria.setImageDrawable(categorias.getDrawable(10));
                break;
            case "miscelâneas":
                holder.imgVisualizarIngredienteCategoria.setImageDrawable(categorias.getDrawable(11));
                break;
            case "outros alimentos industrializados":
                holder.imgVisualizarIngredienteCategoria.setImageDrawable(categorias.getDrawable(12));
                break;
            default:
                holder.imgVisualizarIngredienteCategoria.setImageDrawable(categorias.getDrawable(2));
        }
    }

    @Override
    public int getItemCount() {
        return mIngredientes != null ? mIngredientes.size() : 0;
    }

}
