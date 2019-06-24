package com.example.nutrionall.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nutrionall.R;
import com.example.nutrionall.activity.holders.IngredientesHolder;
import com.example.nutrionall.activity.holders.SimilaresHolder;
import com.example.nutrionall.models.Meal.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class IngredientesAdapter extends RecyclerView.Adapter<IngredientesHolder> {

    private final List<Ingredient> mIngredientes;

    public IngredientesAdapter(ArrayList ingredientes) {

        mIngredientes = ingredientes;
    }

    @Override
    public IngredientesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new IngredientesHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_ingredientes, parent, false));
    }

    @SuppressWarnings("ResourceType")
    @Override
    public void onBindViewHolder(IngredientesHolder holder, final int position) {
        holder.txtLayoutIngredientesNome.setText(mIngredientes.get(position).getNameFood());
        holder.txtLayoutIngredientesPorcao.setText(mIngredientes.get(position).getPortion());
        holder.txtLayoutIngredientesQtde.setText(mIngredientes.get(position).getQtdPortion());

        holder.btnLayoutIngredientesDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IngredientesAdapter.this.removeItem(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mIngredientes != null ? mIngredientes.size() : 0;
    }

    public void insertItem(Ingredient ingredient) {
        mIngredientes.add(ingredient);
        notifyItemInserted(getItemCount());
    }

    public void removeItem(int position) {
        mIngredientes.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mIngredientes.size());
    }

    public ArrayList<Ingredient> getArrayIngredients(){
        ArrayList<Ingredient> arrayIngredients = new ArrayList<>();
        arrayIngredients.addAll(mIngredientes);
        return arrayIngredients;
    }
}
