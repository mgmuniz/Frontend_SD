package com.example.nutrionall.activity.adapterVisualizaRefeicao;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.nutrionall.activity.fragmentVisualizaRefeicao.VisuRefeicaoDescricao;
import com.example.nutrionall.activity.fragmentVisualizaRefeicao.VisuRefeicaoIngredientes;
import com.example.nutrionall.activity.fragmentVisualizaRefeicao.VisuRefeicaoNutricionais;

public class MyFragPageAdapterVisuRefeicao extends FragmentPagerAdapter {

    private String[] mTitles;

    public MyFragPageAdapterVisuRefeicao(FragmentManager fragment, String[] mTitles){
        super(fragment);
        this.mTitles = mTitles;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new VisuRefeicaoDescricao();
            case 1:
                return new VisuRefeicaoIngredientes();
            case 2:
                return new VisuRefeicaoNutricionais();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return this.mTitles.length;
    }

    public CharSequence getPageTitle(int position){
        return this.mTitles[position];
    }
}
