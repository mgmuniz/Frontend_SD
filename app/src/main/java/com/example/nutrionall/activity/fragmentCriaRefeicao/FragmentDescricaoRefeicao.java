package com.example.nutrionall.activity.fragmentCriaRefeicao;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nutrionall.R;

public class FragmentDescricaoRefeicao extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstancesState) {
        return (View) inflater.inflate(R.layout.descricao_cria_refeicao, container, false);
    }
}
