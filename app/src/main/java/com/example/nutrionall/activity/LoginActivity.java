package com.example.nutrionall.activity;

import com.example.nutrionall.api.AuthService;
import com.example.nutrionall.models.AuthUser;
import com.example.nutrionall.models.UserLogin;
import com.example.nutrionall.utils.Validate;
import com.example.nutrionall.utils.Consts;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nutrionall.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private EditText email;
    private EditText password;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // estabelece comunicação com a api
        retrofit = Consts.connection();

        SharedPreferences preferences = getSharedPreferences(Consts.ARQUIVO_PREFERENCIAS, 0);

        if(preferences.contains("token")){
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
        }
    }

    public void login(View view) {
        // recupera email e senha dos campos digitados pelo usuário
        email = findViewById(R.id.editLoginEmail);
        password = findViewById(R.id.editLoginPassword);

        // arquivo de preferências do usuário
        SharedPreferences preferences = getSharedPreferences(Consts.ARQUIVO_PREFERENCIAS, 0);
        editor = preferences.edit();

        // valida email e senha
        Context c = getApplicationContext();
        if (Validate.validateNotExistFieldOrError(email, "Preencha seu email!", c) &&
                Validate.validateNotExistFieldOrError(password, "Preencha sua senha!", c)) {


            UserLogin newUserLogin = new UserLogin();
            newUserLogin.setEmail(email.getText().toString());
            newUserLogin.setPassword(password.getText().toString());

            AuthService serviceLogin = retrofit.create(AuthService.class);
            Call<AuthUser> call = serviceLogin.login(newUserLogin);

            call.enqueue(new Callback<AuthUser>() {
                @Override
                public void onResponse(Call<AuthUser> call, Response<AuthUser> response) {
                    if (response.isSuccessful()) {
                        AuthUser authUser = response.body();

                        Toast.makeText(getApplicationContext(), "Bem-Vindo " + authUser.getName(), Toast.LENGTH_LONG).show();

                        // salva os dados do usuário no arquivo de preferencias
                        editor.putString("name", authUser.getName());
                        editor.putString("email", authUser.getEmail());
                        editor.putString("_id", authUser.get_id());
                        editor.putString("token", authUser.getToken());
                        editor.putBoolean("isPremium", authUser.getPremium());
                        editor.commit();

                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                    } else {
                        try {
                            // informa o usuário da requisição
                            JSONObject x = new JSONObject(response.errorBody().string());
                            Toast.makeText(getApplicationContext(), x.getString("msg"), Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<AuthUser> call, Throwable t) {

                    Log.d("login", "onFailure: " + t.toString());
                }
            });
        }
    }

    public void abrirCadastroUsuario(View view) {
        Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
        startActivity(intent);
    }
}
