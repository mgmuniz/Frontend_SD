package com.example.nutrionall.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.nutrionall.R;
import com.example.nutrionall.activity.adapterCriaRefeicao.MyFragPageAdapterCriaRefeicao;
import com.example.nutrionall.api.Meal.MealApi;
import com.example.nutrionall.models.Meal.Ingredient;
import com.example.nutrionall.models.Meal.Meal;
import com.example.nutrionall.utils.Consts;
import com.example.nutrionall.utils.Methods;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CriarRefeicaoActivity extends AppCompatActivity implements Methods {

    private Retrofit retrofit;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    // Componentes
    private EditText txtCriarRefeicaoNomeRefeicao;
    private EditText txtCriarRefeicaoDescricao;
    private ToggleButton btnCriarRefeicaoVisivel;
    private RadioButton radioCriarRefeicaoDesjejum;
    private RadioButton radioCriarRefeicaoAlmoco;
    private RadioButton radioCriarRefeicaoLanche;
    private RadioButton radioCriarRefeicaoJanta;

    private RecyclerView listCriarRefeicaoIngredientes;
    private ImageView imgCriarRefeicaoImagemRefeicao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_refeicao);


        // estabelece comunicação com a api
        retrofit = Consts.connection();

        getReferencesComponentes();
        mViewPager.setAdapter(new MyFragPageAdapterCriaRefeicao(getSupportFragmentManager(), getResources().getStringArray(R.array.titles_tab)));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    public void loadImgMeal(View view){
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
                imgCriarRefeicaoImagemRefeicao = findViewById(R.id.imgCriarRefeicaoImagemRefeicao);
                imgCriarRefeicaoImagemRefeicao.setImageBitmap(img);
            } catch(IOException e){
                e.printStackTrace();
            }

        }
    }


    private Meal getMealData(){

        txtCriarRefeicaoNomeRefeicao = findViewById(R.id.txtCriarRefeicaoNomeRefeicao);
        txtCriarRefeicaoDescricao = findViewById(R.id.txtCriarRefeicaoDescricao);
        btnCriarRefeicaoVisivel = findViewById(R.id.btnCriarRefeicaoVisivel);
        radioCriarRefeicaoDesjejum = findViewById(R.id.radioCriarRefeicaoDesjejum);
        radioCriarRefeicaoAlmoco = findViewById(R.id.radioCriarRefeicaoAlmoco);
        radioCriarRefeicaoLanche = findViewById(R.id.radioCriarRefeicaoLanche);
        radioCriarRefeicaoJanta = findViewById(R.id.radioCriarRefeicaoJanta);

        Meal newMeal = new Meal();

        ArrayList<Ingredient> ingredients = new ArrayList<>();
        Ingredient x1 = new Ingredient();
        x1.setIdFood("5cf845fe245413a64b40500e");
        x1.setPortion("50");
        x1.setQtdPortion("3");
        Ingredient x2 = new Ingredient();
        x2.setIdFood("5cf845fe245413a64b404fad");
        x2.setPortion("50");
        x2.setQtdPortion("3");

        ingredients.add(x1);
        ingredients.add(x2);

        newMeal.setName(txtCriarRefeicaoNomeRefeicao.getText().toString());
        newMeal.setDescription(txtCriarRefeicaoDescricao.getText().toString());
        newMeal.setVisibility((btnCriarRefeicaoVisivel.isChecked()));

        if(radioCriarRefeicaoDesjejum.isChecked()){newMeal.setClassification(Consts.getClassification("Desjejum"));}
        if(radioCriarRefeicaoAlmoco.isChecked()){newMeal.setClassification(Consts.getClassification("Almoço"));}
        if(radioCriarRefeicaoLanche.isChecked()){newMeal.setClassification(Consts.getClassification("Lanche"));}
        if(radioCriarRefeicaoJanta.isChecked()){newMeal.setClassification(Consts.getClassification("Jantar"));}

        newMeal.setIngredients(ingredients);

        return newMeal;
    }

    public void cadastrar(View view) {
        final String TAG = "cadastrar Refeição";

        Log.d(TAG, "cadastrar: ");
        // Monta os dados para envio
        Meal newMeal = getMealData();

        // Cria o multpart file de envio para imagem
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "urlImg");
        MultipartBody.Part urlImg = null;
        try {
            urlImg = multipart();
        } catch (IOException e) {
            e.printStackTrace();
        }

        MealApi serviceApi = retrofit.create(MealApi.class);
        Call<Meal> call = serviceApi.save(urlImg, newMeal, "bearer " + getPreferences().getString("token", ""));

        call.enqueue(new Callback<Meal>() {
            @Override
            public void onResponse(Call<Meal> call, Response<Meal> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Cadastro efetuado com sucesso!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onResponse: " + response.message());
                }else{
                    Toast.makeText(getApplicationContext(),"Erro ao cadastrar refeição!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onResponse: " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<Meal> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
            }
        });

        finish();
    }

    private byte[] imgToString() {
        imgCriarRefeicaoImagemRefeicao.setDrawingCacheEnabled(true);
        imgCriarRefeicaoImagemRefeicao.buildDrawingCache();

        // recupera o bitmap da imagem a ser enviada
        Bitmap bitmap = imgCriarRefeicaoImagemRefeicao.getDrawingCache();

        // comprime o bitmap para algum formato de imagem
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        // converte o byteArrayOutputStream em array
        byte[] imgByte = byteArrayOutputStream.toByteArray();

        return imgByte;
    }

    private MultipartBody.Part multipart() throws IOException {
        File filesDir = getApplicationContext().getFilesDir();
        File file = new File(filesDir, "imageMeal" + ".png");

        byte[] img = imgToString();

        FileOutputStream fos = new FileOutputStream(file);
        fos.write(img);
        fos.flush();
        fos.close();

        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("urlImg", file.getName(), reqFile);

        return body;
    }

    @Override
    public void getReferencesComponentes() {
        // inicializa variáveis dos componentes
        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager);

    }

    @Override
    public SharedPreferences getPreferences() {
        SharedPreferences preferences = getSharedPreferences(Consts.ARQUIVO_PREFERENCIAS, 0);
        return preferences;
    }

    public void desRadioLessCriaDesjejum(View view) {

        RadioButton radioAlmoco = findViewById(R.id.radioCriarRefeicaoAlmoco);
        RadioButton radioLanche = findViewById(R.id.radioCriarRefeicaoLanche);
        RadioButton radioJantar = findViewById(R.id.radioCriarRefeicaoJanta);

        radioJantar.setChecked(false);
        radioLanche.setChecked(false);
        radioAlmoco.setChecked(false);
    }

    public void desRadioLessCriaAlmoco(View view) {

        RadioButton radioDesjejum = findViewById(R.id.radioCriarRefeicaoDesjejum);
        RadioButton radioLanche = findViewById(R.id.radioCriarRefeicaoLanche);
        RadioButton radioJantar = findViewById(R.id.radioCriarRefeicaoJanta);

        radioJantar.setChecked(false);
        radioLanche.setChecked(false);
        radioDesjejum.setChecked(false);
    }

    public void desRadioLessCriaLanche(View view) {

        RadioButton radioDesjejum = findViewById(R.id.radioCriarRefeicaoDesjejum);
        RadioButton radioAlmoco = findViewById(R.id.radioCriarRefeicaoAlmoco);
        RadioButton radioJantar = findViewById(R.id.radioCriarRefeicaoJanta);

        radioJantar.setChecked(false);
        radioAlmoco.setChecked(false);
        radioDesjejum.setChecked(false);
    }

    public void desRadioLessCriaJantar(View view) {

        RadioButton radioDesjejum = findViewById(R.id.radioCriarRefeicaoDesjejum);
        RadioButton radioAlmoco = findViewById(R.id.radioCriarRefeicaoAlmoco);
        RadioButton radioLanche = findViewById(R.id.radioCriarRefeicaoLanche);

        radioLanche.setChecked(false);
        radioAlmoco.setChecked(false);
        radioDesjejum.setChecked(false);
    }
}
