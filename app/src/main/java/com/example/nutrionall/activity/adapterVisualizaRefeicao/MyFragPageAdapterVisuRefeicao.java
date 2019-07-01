package com.example.nutrionall.activity.adapterVisualizaRefeicao;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.nutrionall.activity.fragmentVisualizaRefeicao.VisuRefeicaoDescricao;
import com.example.nutrionall.activity.fragmentVisualizaRefeicao.VisuRefeicaoIngredientes;
import com.example.nutrionall.activity.fragmentVisualizaRefeicao.VisuRefeicaoNutricionais;
import com.example.nutrionall.models.Meal.Meal;
import com.example.nutrionall.models.User.AuthUser;

public class MyFragPageAdapterVisuRefeicao extends FragmentPagerAdapter {

    private String[] mTitles;
    private Meal meal;
    private AuthUser user;

    public MyFragPageAdapterVisuRefeicao(FragmentManager fragment, String[] mTitles, Meal meal, AuthUser user){
        super(fragment);
        this.mTitles = mTitles;
        this.meal = meal;
        this.user = user;
    }
    @Override
    public Fragment getItem(int position) {
        Bundle args = new Bundle();
        args.putSerializable("meal", meal);
        Fragment fragment;
        switch (position){
            case 0:
                fragment = new VisuRefeicaoDescricao();
                fragment.setArguments(args);
                return fragment;
            case 1:
                fragment = new VisuRefeicaoIngredientes();
                fragment.setArguments(args);
                return fragment;
            case 2:
                fragment = new VisuRefeicaoNutricionais();
                fragment.setArguments(args);
                return fragment;
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
