package com.example.nutrionall.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nutrionall.R;
import com.example.nutrionall.api.User.UserService;
import com.example.nutrionall.models.User.User;
import com.example.nutrionall.utils.Consts;
import com.example.nutrionall.utils.Methods;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AtualizaUsuarioActivity extends AppCompatActivity implements Methods {

    private Retrofit retrofit;
    private EditText editAtualizaUsuarioName;
    private EditText editAtualizaUsuarioEmail;
    private EditText editAtualizaUsuarioDataNascimento;
    private ImageView editAtualizaUsuarioImgUser;
    private ProgressBar progressBarAtualizaUsuarioUser;
    private RadioButton radioAtualizaUsuarioF;
    private RadioButton radioAtualizaUsuarioM;

    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualiza_usuario);
        retrofit = Consts.connection();

        getData();
    }

    public void getReferencesComponentes() {

        editAtualizaUsuarioName = findViewById(R.id.editAtualizaUsuarioName);
        editAtualizaUsuarioEmail = findViewById(R.id.editAtualizaUsuarioEmail);
        editAtualizaUsuarioDataNascimento = findViewById(R.id.editAtualizaUsuarioDataNascimento);
        editAtualizaUsuarioImgUser = findViewById(R.id.editAtualizaUsuarioImgUser);
        progressBarAtualizaUsuarioUser = findViewById(R.id.progressBarAtualizaUsuarioUser);
        radioAtualizaUsuarioF = findViewById(R.id.radioAtualizaUsuarioF);
        radioAtualizaUsuarioM = findViewById(R.id.radioAtualizaUsuarioM);

    }

    @Override
    public SharedPreferences getPreferences() {
        SharedPreferences preferences = getSharedPreferences(Consts.ARQUIVO_PREFERENCIAS, 0);
        return preferences;
    }

    private void getData(){
        getReferencesComponentes();

        // Recupera informações do perfil do usuario
        Log.d("home", "onCreate: " + getPreferences().contains("email"));

        String defaultImg = "https://cdn4.iconfinder.com/data/icons/avatars-xmas-giveaway/128/anime_spirited_away_no_face_nobody-512.png";

        editAtualizaUsuarioEmail.setText(getPreferences().getString("email", ""));
        editAtualizaUsuarioName.setText(getPreferences().getString("name", ""));
        editAtualizaUsuarioDataNascimento.setText(getPreferences().getString("dateOfBirth", ""));

        boolean sexo_user = getPreferences().getBoolean("gender", false);

        if(sexo_user == true){
            radioAtualizaUsuarioF.setChecked(true);
        }else{
            radioAtualizaUsuarioM.setChecked(true);
        }

        Picasso.get().load(getPreferences().getString("urlImg", defaultImg)).fit().centerCrop().into(editAtualizaUsuarioImgUser);
    }

    public void alterarDados(View view){
        final String TAG = "alterarDados";
        String defaultImg = "https://cdn4.iconfinder.com/data/icons/avatars-xmas-giveaway/128/anime_spirited_away_no_face_nobody-512.png";
        editor = getPreferences().edit();

        final User newUser = new User();
        newUser.setPassword(getPreferences().getString("password", ""));
        newUser.setUrlImg((getPreferences().getString("urlImg", defaultImg)));

        newUser.setName(editAtualizaUsuarioName.getText().toString());
        newUser.setEmail(editAtualizaUsuarioEmail.getText().toString());
        newUser.setDateOfBirth(editAtualizaUsuarioDataNascimento.getText().toString());
        if(radioAtualizaUsuarioF.isChecked()){
            newUser.setGender("true");
        }else{
            newUser.setGender("false");
        }

        newUser.setPremium(true);

        UserService serviceApi = retrofit.create(UserService.class);
        Call<JsonObject> call = serviceApi.alterarDados(getPreferences().getString("_id",""), newUser, "bearer " + getPreferences().getString("token",""));

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d(TAG, "onResponse: " + response.toString());
                if(response.isSuccessful()){
                    Log.d(TAG, "onResponse: " + response.body().get("msg").getAsString());

                    // salva os dados do usuário no arquivo de preferencias
                    editor.putString("name", newUser.getName());
                    editor.putString("email", newUser.getEmail());
                    editor.putBoolean("isPremium", true);
                    editor.putString("urlImg", newUser.getUrlImg());
                    editor.commit();

                    Toast.makeText(getApplicationContext(), "Alteração realizada com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

    }
}
