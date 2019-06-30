package com.example.nutrionall.activity.adapterVisualizaRefeicao;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.nutrionall.activity.fragmentVisualizaRefeicao.VisuRefeicaoDescricao;
import com.example.nutrionall.activity.fragmentVisualizaRefeicao.VisuRefeicaoIngredientes;
import com.example.nutrionall.activity.fragmentVisualizaRefeicao.VisuRefeicaoNutricionais;
import com.example.nutrionall.models.Meal.Meal;

public class MyFragPageAdapterVisuRefeicao extends FragmentPagerAdapter {

    private String[] mTitles;
    private Meal meal;

    public MyFragPageAdapterVisuRefeicao(FragmentManager fragment, String[] mTitles, Meal meal){
        super(fragment);
        this.mTitles = mTitles;
        this.meal = meal;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                Bundle args = new Bundle();
                args.putSerializable("meal", meal);
                Fragment fragment = new VisuRefeicaoDescricao();
                fragment.setArguments(args);
                return fragment;
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
