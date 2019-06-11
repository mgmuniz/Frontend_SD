package com.example.nutrionall.activity;

import com.example.nutrionall.utils.Validate;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nutrionall.R;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view) {
        // recupera email e senha dos campos digitados pelo usuário
        email = findViewById(R.id.editLoginEmail);
        password = findViewById(R.id.editLoginPassword);

        // arquivo de preferências do usuário
        final String ARQUIVO_PREFERENCIAS = String.valueOf(R.string.ARQUIVO_PREFERENCIAS);
        SharedPreferences preferences = getSharedPreferences(ARQUIVO_PREFERENCIAS,0);
        SharedPreferences.Editor editor = preferences.edit();

        // valida email e senha
        Context c = getApplicationContext();
        if( Validate.validateNotExistFieldOrError(email, "Preencha seu email!", c) &&
                Validate.validateNotExistFieldOrError(password, "Preencha sua senha!", c)){
            Log.d("login", "login: sucess");
        }
    }
}
