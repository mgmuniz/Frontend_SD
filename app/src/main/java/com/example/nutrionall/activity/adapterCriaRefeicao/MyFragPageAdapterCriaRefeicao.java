package com.example.nutrionall.activity.adapterCriaRefeicao;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.nutrionall.activity.fragmentCriaRefeicao.FragmentDescricaoRefeicao;
import com.example.nutrionall.activity.fragmentCriaRefeicao.FragmentInformacaoRefeicao;

public class MyFragPageAdapterCriaRefeicao extends FragmentPagerAdapter {

    private String[] mTabTitles;

    public MyFragPageAdapterCriaRefeicao(FragmentManager fn, String[] mTabTitles){
        super(fn);
        this.mTabTitles = mTabTitles;
    }

    @Override
    public int getCount() {
        return this.mTabTitles.length;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new FragmentInformacaoRefeicao();
            case 1:
                return new FragmentDescricaoRefeicao();
            default:
                return null;
        }
    }

    public CharSequence getPageTitle(int position){
        return this.mTabTitles[position];
    }
}
