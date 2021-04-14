package com.example.apptreineeongrid;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TelaPontucao extends AppCompatActivity {

    private ViewHolder mViewHolder = new ViewHolder();
    Typeface fonte;
    int highscoreFacil;
    int highscoreMedio;
    int highscoreDificil;
    int larguraTela;
    int alturaTela;
    String token;
    RequestQueue queue = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_pontucao);

        this.token = getIntent().getExtras().getString("token");
        this.queue = Volley.newRequestQueue(this);

        this.mViewHolder.b_apagar = findViewById(R.id.TPbapagar);
        this.mViewHolder.b_voltar = findViewById(R.id.TPbvoltar);
        this.mViewHolder.t_scoreFacil = findViewById(R.id.TPscoreFacil);
        this.mViewHolder.t_scoreMedio = findViewById(R.id.TPscoreMedio);
        this.mViewHolder.t_scoreDificil = findViewById(R.id.TPscoreDificil);
        this.mViewHolder.view = findViewById(R.id.TPview);

        mudarFonte();
        mudarPosicao_Tamanho();

        // pegar o highscore de cada dificuldade do usuario
        /*highscoreFacil = 0;
        highscoreMedio = 0;
        highscoreDificil = 0;*/
        this.requestScores();

        mViewHolder.t_scoreFacil.setText(String.valueOf(highscoreFacil));
        mViewHolder.t_scoreMedio.setText(String.valueOf(highscoreMedio));
        mViewHolder.t_scoreDificil.setText(String.valueOf(highscoreDificil));

        mViewHolder.b_apagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*mViewHolder.t_scoreFacil.setText("0");
                mViewHolder.t_scoreMedio.setText("0");
                mViewHolder.t_scoreDificil.setText("0");*/

                // setar o highscore de cada dificuldade do usuario como 0
                TelaPontucao.this.setScore('f', -2);
                TelaPontucao.this.setScore('m', -2);
                TelaPontucao.this.setScore('d', -2);
            }
        });

        mViewHolder.b_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public static class ViewHolder{
        Button b_apagar;
        ImageButton b_voltar;
        TextView t_scoreFacil;
        TextView t_scoreMedio;
        TextView t_scoreDificil;
        View view;
    }

    private void mudarFonte()
    {
        fonte = Typeface.createFromAsset(getAssets(),"Press Start K.ttf");
        mViewHolder.t_scoreFacil.setTypeface(fonte);
        mViewHolder.t_scoreMedio.setTypeface(fonte);
        mViewHolder.t_scoreDificil.setTypeface(fonte);
        mViewHolder.b_apagar.setTypeface(fonte);
    }

    private void mudarPosicao_Tamanho()
    {
        mViewHolder.view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                int alturaObjeto;
                int tamanho;

                //Remove o listenner para não ser novamente chamado.
                mViewHolder.view.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                larguraTela = mViewHolder.view.getWidth();
                alturaTela = mViewHolder.view.getHeight();

                alturaObjeto = mViewHolder.b_voltar.getHeight();
                tamanho = mudarTamanho(alturaTela,alturaObjeto);
                mViewHolder.b_voltar.getLayoutParams().height = tamanho;
                mViewHolder.b_voltar.requestLayout();

                alturaObjeto = mViewHolder.b_apagar.getHeight();
                tamanho = mudarTamanho(alturaTela,alturaObjeto);
                mViewHolder.b_apagar.getLayoutParams().height = tamanho;
                mViewHolder.b_apagar.requestLayout();

                alturaObjeto = (int) mViewHolder.t_scoreDificil.getTextSize();
                tamanho = mudarTamanho(alturaTela,alturaObjeto)/3;
                mViewHolder.t_scoreFacil.setTextSize(tamanho);
                mViewHolder.t_scoreMedio.setTextSize(tamanho);
                mViewHolder.t_scoreDificil.setTextSize(tamanho);

            }
        });
    }

    private int mudarTamanho(int tamanhoTela, int tamanhoObjeto)
    {
        int tamanho;
        tamanho = tamanhoTela * tamanhoObjeto / 1920;
        return tamanho;
    }

    public void requestScores() {
        String url = "https://appongridtraineer.garcias.dev:8443/user/get/score";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObject scores = null;
                        try {
                            scores = response.getJSONObject("success");
                            System.out.println(scores.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                            finish();
                        }
                        try {
                            TelaPontucao.this.highscoreDificil = scores.getInt("max_score_d");
                            TelaPontucao.this.highscoreMedio = scores.getInt("max_score_m");
                            TelaPontucao.this.highscoreFacil = scores.getInt("max_score_f");
                            TelaPontucao.this.mViewHolder.t_scoreDificil.setText(String.valueOf(TelaPontucao.this.highscoreDificil));
                            TelaPontucao.this.mViewHolder.t_scoreMedio.setText(String.valueOf(TelaPontucao.this.highscoreMedio));
                            TelaPontucao.this.mViewHolder.t_scoreFacil.setText(String.valueOf(TelaPontucao.this.highscoreFacil));
                            Toast.makeText(getApplicationContext(),"Score carregado.", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError e) {
                        // TODO: Handle error
                        e.printStackTrace();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();

                headers.put("Authorization", "Bearer " + getIntent().getExtras().getString("token"));
                return headers;
            }
        };

        queue.add(jsonObjectRequest);
    }

    public void setScore(char difficulty, int _score) {
        String url = "https://appongridtraineer.garcias.dev:8443/user/set/score";
        JSONObject score = null;
        try {
            score = new JSONObject(String.format("{\"max_score_%c\": %d}", difficulty, _score));
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(),"Falha ao enviar scores para o banco de dados.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return;
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, score, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String success = null;
                        try {
                            success = response.getString("success");
                            Toast.makeText(getApplicationContext(), success, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(),"Falha ao enviar scores para o banco de dados.", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                        Toast.makeText(getApplicationContext(),"Scores enviados para o banco de dados", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError e) {
                        // TODO: Handle error
                        Toast.makeText(getApplicationContext(),"Falha ao enviar scores para o banco de dados.", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();

                headers.put("Authorization", "Bearer " + getIntent().getExtras().getString("token"));
                return headers;
            }
        };

        this.queue.add(jsonObjectRequest);
    }
}