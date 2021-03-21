package com.example.apptreineeongrid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.BoringLayout;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ViewHolder mViewHolder = new ViewHolder();
    private ArrayList<String> perguntas;
    private ArrayList<String> resp_errada;
    private ArrayList<String> resp_correta;
    private ArrayList<String> curiosidades;
    private ArrayList<Integer> pergs_fazer;
    private ArrayList<Integer> botoes_acertar;
    private int x;
    private int i;
    private int num_pergunta;
    private int score=0;
    private Boolean rClicado=true;

    Animation animFadein;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        this.mViewHolder.r1 = findViewById(R.id.Opc1);
        this.mViewHolder.r2 = findViewById(R.id.Opc2);
        this.mViewHolder.r3 = findViewById(R.id.Opc3);
        this.mViewHolder.sair_vcSabia = findViewById(R.id.sair_vcSabia);
        this.mViewHolder.b_continuar = findViewById(R.id.continuar);
        this.mViewHolder.pergunta_texto = findViewById(R.id.pergunta);
        this.mViewHolder.b_vcSabia = findViewById(R.id.VcSabia);
        this.mViewHolder.score_texto = findViewById(R.id.score);
        this.mViewHolder.imagem_vcSabia = findViewById(R.id.imageView);
        this.mViewHolder.texto_vcSabia = findViewById(R.id.texto_vcSabia);

        perguntas = new ArrayList<>();
        resp_errada = new ArrayList<>();
        resp_correta = new ArrayList<>();
        curiosidades = new ArrayList<>();
        pergs_fazer = new ArrayList<>();
        botoes_acertar = new ArrayList<>();

        mViewHolder.imagem_vcSabia.setVisibility(View.GONE);
        mViewHolder.sair_vcSabia.setVisibility(View.GONE);
        mViewHolder.texto_vcSabia.setVisibility(View.GONE);

        //PERGUNTAS E RESPOSTAS
        inicializarPerguntas();

        for (x=0;x<perguntas.size()-1;x++)
        {
            pergs_fazer.add(x);
        }
        for (i=0;i<3;i++)
        {
            botoes_acertar.add(i);
        }

        // GERAR PERGUNTAS ALEATÓRIAS
        mViewHolder.b_continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rClicado = false;

                pintarBotoes();

                mViewHolder.b_vcSabia.setVisibility(View.GONE);
                mViewHolder.b_continuar.setVisibility((View.GONE));

                //VALIDAR PERGUNTA
                if (pergs_fazer.size() > 0) {
                    x = new Random().nextInt(pergs_fazer.size());
                    num_pergunta = pergs_fazer.get(x);

                    //GERAR LUGARES DE RESPOSTA ALEATORIO
                    i = new Random().nextInt(botoes_acertar.size());
                    System.out.println("Tamanho do veror que comtem os botões: " + botoes_acertar.size());
                    System.out.println("Número dos botões aleatório: " + i);
                    if (i == 1) {
                        mViewHolder.r1.setText(resp_correta.get(num_pergunta));
                        mViewHolder.r2.setText(resp_errada.get(2 * num_pergunta));
                        mViewHolder.r3.setText(resp_errada.get(2 * num_pergunta + 1));
                    } else if (i == 2) {
                        mViewHolder.r2.setText(resp_correta.get(num_pergunta));
                        mViewHolder.r1.setText(resp_errada.get(2 * num_pergunta));
                        mViewHolder.r3.setText(resp_errada.get(2 * num_pergunta + 1));
                    } else {
                        mViewHolder.r3.setText(resp_correta.get(num_pergunta));
                        mViewHolder.r1.setText(resp_errada.get(2 * num_pergunta));
                        mViewHolder.r2.setText(resp_errada.get(2 * num_pergunta + 1));
                    }

                    pergs_fazer.remove(x);
                } else if (pergs_fazer.size() == 0) {
                    num_pergunta = perguntas.size() - 1;
                    mViewHolder.r1.setText("A");
                    mViewHolder.r2.setText("B");
                    mViewHolder.r3.setText("C");
                    rClicado = true;
                }

                mViewHolder.pergunta_texto.setText(perguntas.get(num_pergunta));
                mViewHolder.texto_vcSabia.setText(curiosidades.get(num_pergunta));
            }
        });

        // VALIDAR RESPOSTA
            mViewHolder.r1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!rClicado)
                    {
                        if(mViewHolder.r1.getText() == resp_correta.get(num_pergunta))
                        {
                            respostaCorreta();
                            mViewHolder.r1.setBackgroundColor(0xBA4CAF50);
                        }
                        else
                            mViewHolder.pergunta_texto.setText("Resposta Errada");

                        mViewHolder.b_vcSabia.setVisibility(View.VISIBLE);
                        mViewHolder.b_continuar.setVisibility((View.VISIBLE));
                        rClicado=true;
                    }
                }
            });
            mViewHolder.r2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!rClicado)
                    {
                        if(mViewHolder.r2.getText() == resp_correta.get(num_pergunta))
                        {
                            respostaCorreta();
                            mViewHolder.r2.setBackgroundColor(0xBA4CAF50);
                        }
                        else
                            mViewHolder.pergunta_texto.setText("Resposta Errada");

                        mViewHolder.b_vcSabia.setVisibility(View.VISIBLE);
                        mViewHolder.b_continuar.setVisibility((View.VISIBLE));
                        rClicado=true;
                    }
                }
            });
            mViewHolder.r3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!rClicado)
                    {
                        if(mViewHolder.r3.getText() == resp_correta.get(num_pergunta))
                        {
                            respostaCorreta();
                            mViewHolder.r3.setBackgroundColor(0xBA4CAF50);
                        }
                        else
                            mViewHolder.pergunta_texto.setText("Resposta Errada");

                        mViewHolder.b_vcSabia.setVisibility(View.VISIBLE);
                        mViewHolder.b_continuar.setVisibility((View.VISIBLE));
                        rClicado=true;
                    }
                }
            });

        mViewHolder.b_vcSabia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewHolder.imagem_vcSabia.setVisibility(View.VISIBLE);
                mViewHolder.sair_vcSabia.setVisibility(View.VISIBLE);
                mViewHolder.texto_vcSabia.setVisibility(View.VISIBLE);
                mViewHolder.b_continuar.setVisibility(View.GONE);
                mViewHolder.b_vcSabia.setVisibility(View.GONE);
            }
        });

        mViewHolder.sair_vcSabia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewHolder.imagem_vcSabia.setVisibility(View.GONE);
                mViewHolder.sair_vcSabia.setVisibility(View.GONE);
                mViewHolder.texto_vcSabia.setVisibility(View.GONE);
                mViewHolder.b_continuar.setVisibility(View.VISIBLE);
                mViewHolder.b_vcSabia.setVisibility(View.VISIBLE);
            }
        });
    }

    private static class ViewHolder{
        Button b_continuar;
        Button r1;
        Button r2;
        Button r3;
        ImageButton sair_vcSabia;
        TextView pergunta_texto;
        TextView b_vcSabia;
        TextView score_texto;
        ImageView imagem_vcSabia;
        TextView texto_vcSabia;
    }

    private void inicializarPerguntas()
    {
        perguntas.add("A luz solar leva aproximadamente quanto tempo a chegar à superfície da Terra?");
        curiosidades.add("A energia hidrelétrica é a maior fonte no Brasil.");
        resp_correta.add("8 minutos\n");
        resp_errada.add("15 minutos\n");
        resp_errada.add("30 minutos\n");

        perguntas.add("Quantos da 2+2");
        curiosidades.add("Matemática:somar");
        resp_correta.add("4");
        resp_errada.add("1");
        resp_errada.add("3");

        perguntas.add("Matemática:somar");
        curiosidades.add("2");
        resp_correta.add("2");
        resp_errada.add("5");
        resp_errada.add("8");

        perguntas.add("Calcule: d/dt (sint)");
        curiosidades.add("Matemática->Cáculo: trigonometria");
        resp_correta.add("cost");
        resp_errada.add("sint");
        resp_errada.add("tgt");

        perguntas.add("Calcule: d/dt (tg^(-1)x)");
        curiosidades.add("Matemática->Cáculo: trigonometria inversa");
        resp_correta.add("1/(1+x^2)");
        resp_errada.add("-1/(1+x^2)");
        resp_errada.add("1/(1-x^2)");

        perguntas.add("F");
        curiosidades.add("Português->Alfabeto->Letra: F");
        resp_correta.add("F");
        resp_errada.add("-1/(1+x^2)");
        resp_errada.add("1/(1-x^2)");

        perguntas.add("E");
        curiosidades.add("Português->Alfabeto->Letra: E");
        resp_correta.add("E");
        resp_errada.add("-1/(1+x^2)");
        resp_errada.add("1/(1-x^2)");

        perguntas.add("D");
        curiosidades.add("Português->Alfabeto->Letra: D");
        resp_correta.add("D");
        resp_errada.add("-1/(1+x^2)");
        resp_errada.add("1/(1-x^2)");

        perguntas.add("C");
        curiosidades.add("Português->Alfabeto->Letra: C");
        resp_correta.add("C");
        resp_errada.add("-1/(1+x^2)");
        resp_errada.add("1/(1-x^2)");

        perguntas.add("B");
        curiosidades.add("Português->Alfabeto->Letra: B");
        resp_correta.add("B");
        resp_errada.add("-1/(1+x^2)");
        resp_errada.add("1/(1-x^2)");

        perguntas.add("A");
        curiosidades.add("Português->Alfabeto->Letra: A");
        resp_correta.add("A");
        resp_errada.add("-1/(1+x^2)");
        resp_errada.add("1/(1-x^2)");

        perguntas.add("Acabaram as Perguntas");
        curiosidades.add("Curiosidades");
    }

    private void pintarBotoes()
    {
        mViewHolder.r1.setBackgroundColor(0xB9673AB7);
        mViewHolder.r2.setBackgroundColor(0xB9673AB7);
        mViewHolder.r3.setBackgroundColor(0xB9673AB7);
    }

    private void respostaCorreta()
    {
        mViewHolder.pergunta_texto.setText("Resposta Correta");
        score++;
        mViewHolder.score_texto.setText("Score: "+score);
    }

}