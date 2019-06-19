package com.example.nutrionall.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.nutrionall.R;
import com.example.nutrionall.activity.adapterCriaRefeicao.MyFragPageAdapterCriaRefeicao;

public class CriarRefeicaoActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_refeicao);

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        mViewPager.setAdapter(new MyFragPageAdapterCriaRefeicao(getSupportFragmentManager(),getResources().getStringArray(R.array.titles_tab) ));
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
