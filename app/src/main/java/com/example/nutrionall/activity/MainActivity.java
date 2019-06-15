package com.example.nutrionall.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nutrionall.R;
import com.example.nutrionall.utils.Consts;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private TextView Main_Username;
    private TextView Main_Email;
    private ImageView Main_Img;

    String url = "https://66.media.tumblr.com/7be3673618e956b71994399ab63b4f3a/tumblr_inline_p7yruw0V421rsbh4y_500.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Main_Username = findViewById(R.id.Main_Username);
        Main_Email = findViewById(R.id.Main_Email);

        SharedPreferences preferences = getSharedPreferences(Consts.ARQUIVO_PREFERENCIAS, 0);
        if(preferences.contains("name")){
            String name = preferences.getString("name", "iii");
            Log.d("main", "onCreate: " + name);
            Main_Username.setText(name);
        }else{
            Main_Username.setText("n definido");
        }

        String email = preferences.getString("email", "usuário n definido");
        Log.d("main", "onCreate: " + email);
        Main_Email.setText(email);

        Main_Img = (ImageView) findViewById(R.id.imageViewTeste);
        loadImageFromUrl(url,Main_Img);


    }

    private void loadImageFromUrl(String url, ImageView element) {


//      Picasso.get().load(url).resize(element.getHeight(),element.getWidth()).into(element);
        Picasso.get().load(url).fit().centerCrop().into(element);

    }
}
