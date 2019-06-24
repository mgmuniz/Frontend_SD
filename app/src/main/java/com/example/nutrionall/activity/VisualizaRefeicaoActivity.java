package com.example.nutrionall.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageButton;

import com.example.nutrionall.R;
import com.example.nutrionall.activity.adapterVisualizaRefeicao.MyFragPageAdapterVisuRefeicao;

public class VisualizaRefeicaoActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private ImageButton imgbuttonFavoriteRefeicao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualiza_refeicao);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout_visualiza_refeicao);
        mViewPager = (ViewPager) findViewById(R.id.view_pager_visu_ref);

        mViewPager.setAdapter(new MyFragPageAdapterVisuRefeicao(getSupportFragmentManager(),getResources().getStringArray(R.array.titles_tab_visualiza_refeicao) ));
        mTabLayout.setupWithViewPager(mViewPager);

    }

}
