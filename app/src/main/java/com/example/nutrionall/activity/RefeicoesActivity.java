package com.example.nutrionall.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.example.nutrionall.R;
import com.example.nutrionall.utils.Consts;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

public class RefeicoesActivity extends AppCompatActivity {
    private String[] mImages = Consts.mImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refeicoes);

        CarouselView carouselView = findViewById(R.id.CarouselRefeicoes);
        carouselView.setPageCount(mImages.length);
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
//                imageView.setImageResource(mImages[position]);
                Picasso.get().load(mImages[position]).fit().centerCrop().into(imageView);
            }
        });


        carouselView.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
//                titulo.setText(mTitles[position]);
            }
        });

        CarouselView carouselFavoritesView = findViewById(R.id.CarouselRefeicoesFavoritos);
        carouselFavoritesView.setPageCount(mImages.length);
        carouselFavoritesView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
//                imageView.setImageResource(mImages[position]);
                Picasso.get().load(mImages[position]).fit().centerCrop().into(imageView);
            }
        });

        carouselFavoritesView.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
//                titulo.setText(mTitles[position]);
            }
        });
    }

    public void desRadioLessDesjejum(View view) {

        RadioButton radioAlmoco = findViewById(R.id.radioButtonAlmoco);
        RadioButton radioLanche = findViewById(R.id.radioButtonLanche);
        RadioButton radioJantar = findViewById(R.id.radioButtonJantar);

        radioJantar.setChecked(false);
        radioLanche.setChecked(false);
        radioAlmoco.setChecked(false);
    }

    public void desRadioLessAlmoco(View view) {

        RadioButton radioDesjejum = findViewById(R.id.radioButtonDesjejum);
        RadioButton radioLanche = findViewById(R.id.radioButtonLanche);
        RadioButton radioJantar = findViewById(R.id.radioButtonJantar);

        radioJantar.setChecked(false);
        radioLanche.setChecked(false);
        radioDesjejum.setChecked(false);
    }

    public void desRadioLessLanche(View view) {

        RadioButton radioDesjejum = findViewById(R.id.radioButtonDesjejum);
        RadioButton radioAlmoco = findViewById(R.id.radioButtonAlmoco);
        RadioButton radioJantar = findViewById(R.id.radioButtonJantar);

        radioJantar.setChecked(false);
        radioAlmoco.setChecked(false);
        radioDesjejum.setChecked(false);
    }

    public void desRadioLessJantar(View view) {

        RadioButton radioDesjejum = findViewById(R.id.radioButtonDesjejum);
        RadioButton radioAlmoco = findViewById(R.id.radioButtonAlmoco);
        RadioButton radioLanche = findViewById(R.id.radioButtonLanche);

        radioLanche.setChecked(false);
        radioAlmoco.setChecked(false);
        radioDesjejum.setChecked(false);
    }

    public void criaRefeicao(View view){
        Intent intent = new Intent(RefeicoesActivity.this, CriarRefeicaoActivity.class);
        startActivity(intent);
    }
}
