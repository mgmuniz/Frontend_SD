package com.example.nutrionall.activity.fragmentCriaRefeicao;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.example.nutrionall.R;

public class FragmentInformacaoRefeicao extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.informacoes_cria_refeicao, container, false);

    }
    public void desRadioLessCriaDesjejum(View view) {

        RadioButton radioAlmoco = getView().findViewById(R.id.radioCriarRefeicaoAlmoco);
        RadioButton radioLanche = getView().findViewById(R.id.radioCriarRefeicaoLanche);
        RadioButton radioJantar = getView().findViewById(R.id.radioCriarRefeicaoJanta);

        radioJantar.setChecked(false);
        radioLanche.setChecked(false);
        radioAlmoco.setChecked(false);
    }

    public void desRadioLessCriaAlmoco(View view) {

        RadioButton radioDesjejum = getView().findViewById(R.id.radioCriarRefeicaoDesjejum);
        RadioButton radioLanche = getView().findViewById(R.id.radioCriarRefeicaoLanche);
        RadioButton radioJantar = getView().findViewById(R.id.radioCriarRefeicaoJanta);

        radioJantar.setChecked(false);
        radioLanche.setChecked(false);
        radioDesjejum.setChecked(false);
    }

    public void desRadioLessCriaLanche(View view) {

        RadioButton radioDesjejum = getView().findViewById(R.id.radioCriarRefeicaoDesjejum);
        RadioButton radioAlmoco = getView().findViewById(R.id.radioCriarRefeicaoAlmoco);
        RadioButton radioJantar = getView().findViewById(R.id.radioCriarRefeicaoJanta);

        radioJantar.setChecked(false);
        radioAlmoco.setChecked(false);
        radioDesjejum.setChecked(false);
    }

    public void desRadioLessCriaJantar(View view) {

        RadioButton radioDesjejum = getView().findViewById(R.id.radioCriarRefeicaoDesjejum);
        RadioButton radioAlmoco = getView().findViewById(R.id.radioCriarRefeicaoAlmoco);
        RadioButton radioLanche = getView().findViewById(R.id.radioCriarRefeicaoLanche);

        radioLanche.setChecked(false);
        radioAlmoco.setChecked(false);
        radioDesjejum.setChecked(false);
    }


}
