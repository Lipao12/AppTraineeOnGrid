package com.example.apptreineeongrid;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.example.apptreineeongrid.Question;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TelaJogo extends AppCompatActivity {

    private TelaJogo.ViewHolder mViewHolder = new TelaJogo.ViewHolder();
    private ArrayList<Question> questions;
    private int x;
    private int i;
    private Question pergunta;
    public int score;
    private static Boolean rClicado=true;
    private Typeface fonte;
    boolean counter_continue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_jogo);

        this.mViewHolder.r1 = findViewById(R.id.Opc1);
        this.mViewHolder.r2 = findViewById(R.id.Opc2);
        this.mViewHolder.r3 = findViewById(R.id.Opc3);
        this.mViewHolder.b_continuar = findViewById(R.id.continuar);
        this.mViewHolder.b_vcSabia = findViewById(R.id.vcSabia);
        this.mViewHolder.pergunta_texto = findViewById(R.id.pergunta);
        this.mViewHolder.imagem_perguntas = findViewById(R.id.imageView);
        this.mViewHolder.score_texto = findViewById(R.id.score);
        this.mViewHolder.rosto_feliz = findViewById(R.id.rosto_feliz);
        this.mViewHolder.rosto_triste = findViewById(R.id.rosto_triste);

        /*Resources res = getResources();
        Drawable drawable = ResourcesCompat.getDrawable(res, android.R.drawable.toast_frame,null);*/

        this.questions = new ArrayList<>();

        mudarFonte();
        int dificuldade = getIntent().getExtras().getChar("dificuldade");

        System.out.println("Dificuldade: " + (char)dificuldade);
        if(dificuldade != '\0' )
        {
            mViewHolder.rosto_triste.setVisibility(View.GONE);
            mViewHolder.rosto_feliz.setVisibility(View.GONE);
            mViewHolder.b_continuar.setVisibility(View.GONE);
            mViewHolder.b_vcSabia.setVisibility(View.GONE);
            score=0;
            inicializarPerguntas(dificuldade);
        }

        mViewHolder.b_continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewHolder.r1.setBackgroundColor(0xFFFFFFFF);
                mViewHolder.r2.setBackgroundColor(0xFFFFFFFF);
                mViewHolder.r3.setBackgroundColor(0xFFFFFFFF);

                mViewHolder.rosto_triste.setVisibility(View.GONE);
                mViewHolder.rosto_feliz.setVisibility(View.GONE);
                mViewHolder.b_continuar.setVisibility(View.GONE);
                mViewHolder.b_vcSabia.setVisibility(View.GONE);
                mViewHolder.pergunta_texto.setVisibility(View.VISIBLE);
                mViewHolder.imagem_perguntas.setVisibility(View.VISIBLE);

                gerarPerguntasAleatorias();
            }
        });

        mViewHolder.b_vcSabia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaJogo.this,TelaCuriosidade.class);
                intent.putExtra("curiosidade", pergunta.getCuriosity());
                startActivity(intent);
            }
        });


        // VALIDAR RESPOSTA
        mViewHolder.r1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
               if(!rClicado)
                {
                    rClicado=true;
                    mViewHolder.b_continuar.setVisibility(View.VISIBLE);
                    mViewHolder.b_vcSabia.setVisibility(View.VISIBLE);
                    mViewHolder.pergunta_texto.setVisibility(View.GONE);
                    mViewHolder.imagem_perguntas.setVisibility(View.INVISIBLE);

                    if(mViewHolder.r1.getText() == pergunta.getCorrect())
                    {
                        score++;
                        mViewHolder.score_texto.setText(""+score);
                        mViewHolder.rosto_feliz.setVisibility(View.VISIBLE);
                        acharRespCorreta();
                    }
                    else
                    {
                        mViewHolder.rosto_triste.setVisibility(View.VISIBLE);
                        mViewHolder.r1.setBackgroundColor(0xFFCA1010);
                        acharRespCorreta();
                    }
                }
            }
        });
        mViewHolder.r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!rClicado)
                {
                    rClicado=true;
                    mViewHolder.b_continuar.setVisibility(View.VISIBLE);
                    mViewHolder.b_vcSabia.setVisibility(View.VISIBLE);
                    mViewHolder.pergunta_texto.setVisibility(View.GONE);
                    mViewHolder.imagem_perguntas.setVisibility(View.INVISIBLE);

                    if(mViewHolder.r2.getText() == pergunta.getCorrect())
                    {
                        score++;
                        mViewHolder.score_texto.setText(""+score);
                        mViewHolder.rosto_feliz.setVisibility(View.VISIBLE);
                        acharRespCorreta();
                    }
                    else
                    {
                        mViewHolder.rosto_triste.setVisibility(View.VISIBLE);
                        mViewHolder.r2.setBackgroundColor(0xFFCA1010);
                        acharRespCorreta();
                    }
                }
            }
        });
        mViewHolder.r3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!rClicado)
                {
                    rClicado=true;
                    mViewHolder.b_continuar.setVisibility(View.VISIBLE);
                    mViewHolder.b_vcSabia.setVisibility(View.VISIBLE);
                    mViewHolder.pergunta_texto.setVisibility(View.GONE);
                    mViewHolder.imagem_perguntas.setVisibility(View.INVISIBLE);
                    if(mViewHolder.r3.getText() == pergunta.getCorrect())
                    {
                        score++;
                        mViewHolder.score_texto.setText(""+score);
                        mViewHolder.rosto_feliz.setVisibility(View.VISIBLE);
                        acharRespCorreta();
                    }
                    else
                    {
                        mViewHolder.rosto_triste.setVisibility(View.VISIBLE);
                        mViewHolder.r3.setBackgroundColor(0xFFCA1010);
                        acharRespCorreta();
                    }
                }
            }
        });
    }

    private void _counter(int i) {
        TextView counter = (TextView)findViewById(R.id.counter);
        counter.setText(Integer.toString(i));

        if (i > 0 && this.counter_continue) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    int j = i;
                    j--;
                    _counter(j);
                }
            }, 1000);
        } else {
            rClicado = true;
            acharRespCorreta();
        }

    }

    public static class ViewHolder{
        Button r1;
        Button r2;
        Button r3;
        Button b_continuar;
        Button b_vcSabia;
        TextView pergunta_texto;
        TextView score_texto;
        ImageView imagem_perguntas;
        ImageView rosto_feliz;
        ImageView rosto_triste;
    }

    public void gerarPerguntasAleatorias()
    {
          rClicado = false;

        //VALIDAR PERGUNTA
        if (this.questions.size() > 0) {
            x = new Random().nextInt(this.questions.size());
            Log.v("Volley error", "Size of questions: " + this.questions.size());
            pergunta = this.questions.get(x);
            this.questions.remove(x);

            //GERAR LUGARES DE RESPOSTA ALEATORIO
            i = new Random().nextInt(3);
            if (i == 1) {
                mViewHolder.r1.setText(pergunta.getCorrect());
                mViewHolder.r2.setText(pergunta.getIncorrect1());
                mViewHolder.r3.setText(pergunta.getIncorrect2());
            } else if (i == 2) {
                mViewHolder.r2.setText(pergunta.getCorrect());
                mViewHolder.r1.setText(pergunta.getIncorrect1());
                mViewHolder.r3.setText(pergunta.getIncorrect2());
            } else {
                mViewHolder.r3.setText(pergunta.getCorrect());
                mViewHolder.r1.setText(pergunta.getIncorrect1());
                mViewHolder.r2.setText(pergunta.getIncorrect2());
            }
        } else if (this.questions.size() == 0) {
            finish();
        }

        mViewHolder.pergunta_texto.setText(pergunta.getText());

        this.counter_continue = true;
        _counter(15);
    }

    public static void set_rClicado(boolean clicado)
    {
        rClicado=clicado;
    }

    public void acharRespCorreta()
    {
        this.counter_continue = false;
        if(pergunta.getCorrect() == mViewHolder.r1.getText())
        {
            mViewHolder.r1.setBackgroundColor(0xD806B50D);
        }
        else if(pergunta.getCorrect() == mViewHolder.r2.getText())
        {
            mViewHolder.r2.setBackgroundColor(0xD806B50D);
        }
        else
        {
            mViewHolder.r3.setBackgroundColor(0xD806B50D);
        }
    }

    private void mudarFonte()
    {
        fonte = Typeface.createFromAsset(getAssets(),"RobotoMono-Light.ttf");
        mViewHolder.pergunta_texto.setTypeface(fonte);
        mViewHolder.r1.setTypeface(fonte);
        mViewHolder.r2.setTypeface(fonte);
        mViewHolder.r3.setTypeface(fonte);
    }

    private void inicializarPerguntas(int dificuldade)
    {
        RequestQueue queue = Volley.newRequestQueue(this);

        if(dificuldade == 'f')
        {
            String url = "https://appongridtraineer.garcias.dev:8443/question/list/f";
            ArrayList<Question> q = this.questions;

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            JSONArray questions = null;
                            try {
                                questions = response.getJSONArray("success");
                                for(int i = 0; i < questions.length(); i++) {
                                    JSONObject object = null;
                                    try {
                                        object = questions.getJSONObject(i);
                                        q.add(new Question(object.getString("text"), object.getString("correct"), object.getString("incorrect1"), object.getString("incorrect2"), object.getString("curiosity"), object.getString("difficulty").charAt(0)));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                gerarPerguntasAleatorias();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO: Handle error
                            Log.v("Volley error", error.getMessage());
                        }
                    });

            // Access the RequestQueue through your singleton class.
            queue.add(jsonObjectRequest);

        }

        else if(dificuldade == 'm')
        {
            String url ="https://appongridtraineer.garcias.dev:8443/question/list/m";

            ArrayList<Question> q = this.questions;

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            JSONArray questions = null;
                            try {
                                questions = response.getJSONArray("success");
                                for(int i = 0; i < questions.length(); i++) {
                                    JSONObject object = null;
                                    try {
                                        object = questions.getJSONObject(i);
                                        q.add(new Question(object.getString("text"), object.getString("correct"), object.getString("incorrect1"), object.getString("incorrect2"), object.getString("curiosity"), object.getString("difficulty").charAt(0)));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                gerarPerguntasAleatorias();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO: Handle error
                            Log.v("Volley error", error.getMessage());
                        }
                    });

            // Access the RequestQueue through your singleton class.
            queue.add(jsonObjectRequest);
        }

        else
        {
            String url ="https://appongridtraineer.garcias.dev:8443/question/list/d";

            ArrayList<Question> q = this.questions;

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            JSONArray questions = null;
                            try {
                                questions = response.getJSONArray("success");
                                for(int i = 0; i < questions.length(); i++) {
                                    JSONObject object = null;
                                    try {
                                        object = questions.getJSONObject(i);
                                        q.add(new Question(object.getString("text"), object.getString("correct"), object.getString("incorrect1"), object.getString("incorrect2"), object.getString("curiosity"), object.getString("difficulty").charAt(0)));
                                    } catch (JSONException e) {
                                        Log.v("Volley error", e.getMessage());
                                    }
                                }
                                gerarPerguntasAleatorias();
                            } catch (JSONException e) {
                                Log.v("Volley error", e.getMessage());
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO: Handle error
                            Log.v("Volley error", error.getMessage());
                        }
                    });

            // Access the RequestQueue through your singleton class.
            queue.add(jsonObjectRequest);
        }
        System.out.println("Perguntas inicializadas");
    }


}

