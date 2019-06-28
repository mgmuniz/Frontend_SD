package com.example.nutrionall.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nutrionall.R;
import com.example.nutrionall.api.User.AuthService;
import com.example.nutrionall.models.User.AuthUser;
import com.example.nutrionall.models.User.UserLogin;
import com.example.nutrionall.utils.Consts;
import com.example.nutrionall.utils.Methods;
import com.example.nutrionall.utils.Validate;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity implements Methods {

    private Retrofit retrofit;
    private EditText email;
    private EditText password;
    private ProgressBar progressLoginValidateToken;
    private TextView textLoginValidateToken;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // estabelece comunicação com a api
        retrofit = Consts.connection();

        getReferencesComponentes();

        Log.d("validateToken", "onCreate: " + getPreferences().contains("token"));

        if (getPreferences().contains("token")) {
            verifyToken(getPreferences().getString("token", ""));
        }
    }

    private void verifyToken(String token) {
        // função que verifica se o token do usuário ainda é válido

        progressLoginValidateToken.setVisibility(View.VISIBLE);
        textLoginValidateToken.setVisibility(View.VISIBLE);
        textLoginValidateToken.setTextColor(Color.WHITE);
        textLoginValidateToken.setError(null);
        textLoginValidateToken.setText("Validando informações anteriores, aguarde enquanto fazemos tudo por você =D ...");

        final String TAG = "validateToken";
        Log.d(TAG, "validateToken: iniciada token: " + token);
        AuthUser userAuthenticated = new AuthUser();
        userAuthenticated.setToken(token);

        AuthService serviceAuth = retrofit.create(AuthService.class);
        Call<AuthUser> call = serviceAuth.validateToken(userAuthenticated);

        call.enqueue(new Callback<AuthUser>() {
            @Override
            public void onResponse(Call<AuthUser> call, Response<AuthUser> response) {
                Log.d(TAG, "onResponse: " + response.toString());
                if (response.isSuccessful()) {
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    Log.d(TAG, "Faça login novamente por favor =D");
                        textLoginValidateToken.setText("Faça login novamente por favor =D");
                        textLoginValidateToken.setError("Faça login novamente por favor =D");
                        textLoginValidateToken.setTextColor(Color.RED);
                        progressLoginValidateToken.setVisibility(View.INVISIBLE);
                    }
                }


            @Override
            public void onFailure(Call<AuthUser> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
            }
        });
    }

    public void login(View view) {
        // recupera email e senha dos campos digitados pelo usuário

        // arquivo de preferências do usuário
        editor = getPreferences().edit();

        // valida email e senha
        Context c = getApplicationContext();
        if (Validate.validateNotExistFieldOrError(email, "Preencha seu email!", c) &&
                Validate.validateNotExistFieldOrError(password, "Preencha sua senha!", c)) {

            progressLoginValidateToken.setVisibility(View.VISIBLE);
            textLoginValidateToken.setVisibility(View.VISIBLE);
            textLoginValidateToken.setTextColor(Color.WHITE);
            textLoginValidateToken.setError(null);
            textLoginValidateToken.setText("Aguarde enquanto resolvemos tudo ...");

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
                        editor.putString("urlImg", authUser.getUrlImg());
                        editor.commit();

                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                    } else {
                        try {
                            // informa o usuário da requisição
                            JSONObject x = new JSONObject(response.errorBody().string());
                            textLoginValidateToken.setText(x.getString("msg"));
                            textLoginValidateToken.setError(x.getString("msg"));
                            textLoginValidateToken.setTextColor(Color.RED);
                            progressLoginValidateToken.setVisibility(View.INVISIBLE);
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

    @Override
    public void getReferencesComponentes() {
        textLoginValidateToken = findViewById(R.id.textLoginValidateToken);
        progressLoginValidateToken = findViewById(R.id.progressLoginValidateToken);
        email = findViewById(R.id.editLoginEmail);
        password = findViewById(R.id.editLoginPassword);
    }

    @Override
    public SharedPreferences getPreferences() {
        SharedPreferences preferences = getSharedPreferences(Consts.ARQUIVO_PREFERENCIAS, 0);
        return preferences;
    }
}
