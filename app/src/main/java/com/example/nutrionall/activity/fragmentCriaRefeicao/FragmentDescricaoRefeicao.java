package com.example.nutrionall.activity.fragmentCriaRefeicao;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nutrionall.R;

public class FragmentDescricaoRefeicao extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstancesState) {
        View view = (View) inflater.inflate(R.layout.descricao_cria_refeicao, container, false);
        return view;
    }
}
