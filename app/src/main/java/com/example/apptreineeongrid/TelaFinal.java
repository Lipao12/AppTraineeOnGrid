package com.example.apptreineeongrid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TelaFinal extends AppCompatActivity {

    private ViewHolder mViewHolder = new ViewHolder();

    TextView t_final_score = null;
    Typeface fonte;
    int larguraTela;
    int alturaTela;
    RequestQueue queue = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_final);

        this.queue = Volley.newRequestQueue(this);

        char difficulty = getIntent().getExtras().getChar("dificuldade");
        int score = getIntent().getExtras().getInt("final_score");

        this.setScore(difficulty, score);

        this.mViewHolder.b_tInicial = findViewById(R.id.voltarTF);
        this.mViewHolder.view = findViewById(R.id.TFview);

        this.t_final_score = findViewById(R.id.final_score);
        this.t_final_score.setText(String.valueOf(getIntent().getExtras().getInt("final_score")));

        mudarFonte();
        mudarPosicao_Tamanho();

        mViewHolder.b_tInicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(TelaFinal.this, TelaIniciar.class);
                home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                home.putExtra("token", getIntent().getExtras().getString("token"));
                startActivity(home);
            }
        });

    }

    public static class ViewHolder{
        ImageView b_tInicial;
        View view;
    }

    private void mudarFonte()
    {
        fonte = Typeface.createFromAsset(getAssets(),"Press Start K.ttf");
        this.t_final_score.setTypeface(fonte);
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

                System.out.println("Altura da tela: "+ alturaTela);
                System.out.println("Largura da tela: "+ larguraTela);

                alturaObjeto = mViewHolder.b_tInicial.getHeight();
                tamanho = mudarTamanho(alturaTela,alturaObjeto);
                mViewHolder.b_tInicial.getLayoutParams().height = tamanho;
                mViewHolder.b_tInicial.requestLayout();
            }
        });
    }

    private int mudarTamanho(int tamanhoTela, int tamanhoObjeto)
    {
        int tamanho;

        tamanho = tamanhoTela * tamanhoObjeto / 1920;
        System.out.println("Tamanho: "+tamanho);

        return tamanho;
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