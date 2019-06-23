package com.example.nutrionall.adapters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nutrionall.R;
import com.example.nutrionall.activity.BuscaActivity;

import java.util.ArrayList;
import java.util.Map;

import static android.support.v4.content.ContextCompat.startActivity;

public class AdapterSearchBarHome extends RecyclerView.Adapter<AdapterSearchBarHome.ViewSearchNutrient> {

    private Map<String, String> mapNutrients;
    private ArrayList<String> keys;

    public AdapterSearchBarHome(Map<String, String> mapNutrients) {
        this.mapNutrients = mapNutrients;
        this.keys = new ArrayList<>(mapNutrients.keySet());
    }

    @NonNull
    @Override
    public ViewSearchNutrient onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemLista = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.search_bar_nutrient_home, viewGroup, false);
        return new ViewSearchNutrient(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewSearchNutrient viewSearchNutrient, int i) {
        viewSearchNutrient.searchNutrient.setText(keys.get(i));
    }

    @Override
    public int getItemCount() {
        // o método size começa de 1 e não 0, logo para 5 itens, seria 1,2,3,4,5
        return mapNutrients.size()-1;
    }

    public class ViewSearchNutrient extends RecyclerView.ViewHolder{

        TextView searchNutrient;

        public ViewSearchNutrient(@NonNull View itemView) {
            super(itemView);

            searchNutrient = itemView.findViewById(R.id.searchNutrient);
        }
    }
}
