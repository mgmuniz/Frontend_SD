package com.example.nutrionall.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.nutrionall.R;
import com.example.nutrionall.api.AuthService;
import com.example.nutrionall.api.UserService;
import com.example.nutrionall.models.AuthUser;
import com.example.nutrionall.models.UserCadastro;
import com.example.nutrionall.models.UserLogin;
import com.example.nutrionall.utils.Consts;
import com.example.nutrionall.utils.Validate;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CadastroActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private EditText editCadastroName;
    private EditText editCadastroPassword;
    private EditText editCadastroEmail;
    private EditText editCadastroDataNascimento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        retrofit = new Retrofit.Builder()
                .baseUrl(Consts.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void cadastrar(View view) {
        editCadastroName = findViewById(R.id.editCadastroName);
        editCadastroPassword = findViewById(R.id.editCadastroPassword);
        editCadastroEmail = findViewById(R.id.editCadastroEmail);
        editCadastroDataNascimento = findViewById(R.id.editCadastroDataNascimento);

        // valida campos de edição
        Context c = getApplicationContext();
        if (
                Validate.validateNotExistFieldOrError(editCadastroName, "Preencha seu nome!", c) &&
                        Validate.validateNotExistFieldOrError(editCadastroPassword, "Preencha sua senha!", c) &&
                        Validate.validateNotExistFieldOrError(editCadastroDataNascimento, "Preencha sua data de nascimento!", c)
        ) {

            UserCadastro newUser = new UserCadastro();
            newUser.setName(editCadastroName.getText().toString());
            newUser.setEmail(editCadastroEmail.getText().toString());
            newUser.setGender("false");
            newUser.setPassword(editCadastroPassword.getText().toString());
            newUser.setDateOfBirth(editCadastroDataNascimento.getText().toString());

            UserService serviceSignUp = retrofit.create(UserService.class);
            Call<UserCadastro> call = serviceSignUp.cadastrar(newUser);

            call.enqueue(new Callback<UserCadastro>() {
                @Override
                public void onResponse(Call<UserCadastro> call, Response<UserCadastro> response) {
                    if (response.isSuccessful()) {
                        Log.d("cadastro", "onResponse: " + response.message());
                        Log.d("cadastro", "onResponse: " + response.body().toString());
                        Log.d("cadastro", "onResponse: " + response.body().getMsg());
                    }
                }

                @Override
                public void onFailure(Call<UserCadastro> call, Throwable t) {
                    Log.d("cadastro", "onFailure: " + t.toString());
                }
            });
        }
    }
}
