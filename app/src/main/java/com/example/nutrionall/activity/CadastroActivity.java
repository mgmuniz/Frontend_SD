package com.example.nutrionall.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.nutrionall.R;
import com.example.nutrionall.api.User.UserService;
import com.example.nutrionall.models.User.UserCadastro;
import com.example.nutrionall.utils.Consts;
import com.example.nutrionall.utils.Methods;
import com.example.nutrionall.utils.Validate;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CadastroActivity extends AppCompatActivity implements Methods {

    private Retrofit retrofit;
    private EditText editCadastroName;
    private EditText editCadastroPassword;
    private EditText editCadastroEmail;
    private EditText editCadastroDataNascimento;
    private ImageView editCadastroImgUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        // estabelece comunicação com a api
        retrofit = Consts.connection();

        getReferencesComponentes();
    }

    public void loadImg(View view){
        // Intent para obter uma foto a partir da galeria
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        // Recuperar imagem que o usuário escolheu | O requestCode indica o ponto de requisição (só temos 1 nesse caso)
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Testar retorno dos dados
        if(requestCode == 1 && resultCode == RESULT_OK && data != null){
            // Recuperar local do recurso:
            Uri localImagem = data.getData();

            // Recuperar imagem
            try {
                Bitmap img = MediaStore.Images.Media.getBitmap(getContentResolver(), localImagem);

                // Comprimir no formato JPEG - Para remover transparência

                //Objeto para receber a imagem
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                img.compress(Bitmap.CompressFormat.JPEG,100,stream);

                editCadastroImgUser.setImageBitmap(img);
            } catch(IOException e){
                e.printStackTrace();
            }

        }
    }

    private byte[] imgToString() {
        editCadastroImgUser.setDrawingCacheEnabled(true);
        editCadastroImgUser.buildDrawingCache();

        // recupera o bitmap da imagemd a ser enviada
        Bitmap bitmap = editCadastroImgUser.getDrawingCache();

        // comprime o bitmap para algum formato de imagem
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        // converte o byteArrayOutputStream em array
        byte[] imgByte = byteArrayOutputStream.toByteArray();

        return imgByte;
    }

    private UserCadastro getUserCadastro() {
        UserCadastro newUser = new UserCadastro();
        newUser.setName(editCadastroName.getText().toString());
        newUser.setEmail(editCadastroEmail.getText().toString());
        newUser.setGender("false");
        newUser.setPassword(editCadastroPassword.getText().toString());
        newUser.setDateOfBirth(editCadastroDataNascimento.getText().toString());

        return newUser;
    }

    private MultipartBody.Part multipart() throws IOException {
        File filesDir = getApplicationContext().getFilesDir();
        File file = new File(filesDir, "imageUser" + ".jpg");

        byte[] img = imgToString();

        FileOutputStream fos = new FileOutputStream(file);
        fos.write(img);
        fos.flush();
        fos.close();

        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("urlImg", file.getName(), reqFile);

        return body;
    }

    public void cadastrar(View view) {

        // valida campos de edição
        Context c = getApplicationContext();
        if (
                Validate.validateNotExistFieldOrError(editCadastroName, "Preencha seu nome!", c) &&
                        Validate.validateNotExistFieldOrError(editCadastroPassword, "Preencha sua senha!", c) &&
                        Validate.validateNotExistFieldOrError(editCadastroDataNascimento, "Preencha sua data de nascimento!", c)
        ) {
            // recupera as informações do usuário dos campos de edição
            UserCadastro newUser = getUserCadastro();

            RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "urlImg");
            MultipartBody.Part urlImg = null;
            try {
                urlImg = multipart();
            } catch (IOException e) {
                e.printStackTrace();
            }

            UserService serviceSignUp = retrofit.create(UserService.class);
            Call<UserCadastro> call = serviceSignUp.cadastrar(urlImg, newUser);
            call.enqueue(new Callback<UserCadastro>() {
                @Override
                public void onResponse(Call<UserCadastro> call, Response<UserCadastro> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_LONG).show();
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            // mensagens do servidor
                            JSONObject x = new JSONObject(response.errorBody().string());
                            Toast.makeText(getApplicationContext(), x.getString("msg"), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<UserCadastro> call, Throwable t) {
                    Log.d("cadastro", "onFailure: " + t.toString());
                }
            });
        }
    }

    @Override
    public void getReferencesComponentes() {
        // inicializa variáveis dos componentes
        editCadastroName = findViewById(R.id.editCadastroName);
        editCadastroPassword = findViewById(R.id.editCadastroPassword);
        editCadastroEmail = findViewById(R.id.editCadastroEmail);
        editCadastroDataNascimento = findViewById(R.id.editCadastroDataNascimento);
        editCadastroImgUser = findViewById(R.id.editCadastroImgUser);
    }
}
