package com.example.nutrionall.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.example.nutrionall.R;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

public class RefeicoesActivity extends AppCompatActivity {
    private String[] mImages = new String[]{
            "https://nit.pt/wp-content/uploads/2017/12/840b2ccfec7171179aac43a1f6919c11-754x394.jpg",
            "https://nit.pt/wp-content/uploads/2017/12/c4ca4238a0b923820dcc509a6f75849b-10-754x394.jpg",
            "https://nit.pt/wp-content/uploads/2019/06/3db9f34c117da413eb2de25c06547fe8-754x394.jpg",
            "https://nit.pt/wp-content/uploads/2019/06/a0ea12235f3b00b1ace614e143be0eb2-1-754x394.jpg",
            "https://nit.pt/wp-content/uploads/2019/06/1a051d4e6c04508055d43a8f4f5cb313-754x394.jpg"
    };

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
}
