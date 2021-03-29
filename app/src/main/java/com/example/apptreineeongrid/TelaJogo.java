package com.example.apptreineeongrid;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.MediaRouteButton;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class TelaJogo extends AppCompatActivity {

    private TelaJogo.ViewHolder mViewHolder = new TelaJogo.ViewHolder();
    private ArrayList<String> perguntas;
    private ArrayList<String> resp_errada;
    private ArrayList<String> resp_correta;
    private ArrayList<String> curiosidades;
    public ArrayList<Integer> pergs_fazer;
    private ArrayList<Integer> botoes_acertar;
    private int x;
    private int i;
    private int num_pergunta;
    public int score;
    private static Boolean rClicado=true;

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

        /*Resources res = getResources();
        Drawable drawable = ResourcesCompat.getDrawable(res, android.R.drawable.toast_frame,null);*/

        perguntas = new ArrayList<>();
        resp_errada = new ArrayList<>();
        resp_correta = new ArrayList<>();
        curiosidades = new ArrayList<>();
        pergs_fazer = new ArrayList<>();
        botoes_acertar = new ArrayList<>();

        boolean start=getIntent().getExtras().getBoolean("iniciar");
        if(start)
        {
            mViewHolder.b_continuar.setVisibility(View.GONE);
            mViewHolder.b_vcSabia.setVisibility(View.GONE);
            score=0;
            inicializarPerguntas();
            for (x=0;x<perguntas.size();x++)
            {
                pergs_fazer.add(x);
            }
            for (i=0;i<3;i++)
            {
                botoes_acertar.add(i);
            }
            start=false;
            gerarPerguntasAleatorias();
        }

        mViewHolder.b_continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewHolder.r1.setBackgroundColor(0xFFFFFFFF);
                mViewHolder.r2.setBackgroundColor(0xFFFFFFFF);
                mViewHolder.r3.setBackgroundColor(0xFFFFFFFF);

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
                intent.putExtra("curiosidade",curiosidades.get(num_pergunta));
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
                    mViewHolder.imagem_perguntas.setVisibility(View.GONE);

                    if(mViewHolder.r1.getText() == resp_correta.get(num_pergunta))
                    {
                        score++;
                        mViewHolder.score_texto.setText("Score: "+score);
                        mViewHolder.r1.setBackgroundColor(0xD806B50D);
                    }
                    else
                    {
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
                    mViewHolder.imagem_perguntas.setVisibility(View.GONE);

                    if(mViewHolder.r2.getText() == resp_correta.get(num_pergunta))
                    {
                        score++;
                        mViewHolder.score_texto.setText("Score: "+score);
                        mViewHolder.r2.setBackgroundColor(0xD806B50D); // verde
                    }
                    else
                    {
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
                    mViewHolder.imagem_perguntas.setVisibility(View.GONE);
                    if(mViewHolder.r3.getText() == resp_correta.get(num_pergunta))
                    {
                        score++;
                        mViewHolder.score_texto.setText("Score: "+score);
                        mViewHolder.r3.setBackgroundColor(0xD806B50D);
                    }
                    else
                    {
                        mViewHolder.r3.setBackgroundColor(0xFFCA1010);
                        acharRespCorreta();
                    }
                }
            }
        });
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
    }

    private void inicializarPerguntas()
    {
        perguntas.add("A luz solar leva aproximadamente quanto tempo a chegar à superfície da Terra?");
        curiosidades.add("A energia hidrelétrica é a maior fonte no Brasil.");
        resp_correta.add("8 minutos");
        resp_errada.add("15 minutos");
        resp_errada.add("30 minutos");

        perguntas.add("2+2");
        curiosidades.add("Matemática:somar");
        resp_correta.add("4");
        resp_errada.add("1");
        resp_errada.add("3");

        perguntas.add("1+1");
        curiosidades.add("Matemática:somar");
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

        /*perguntas.add("Acabaram as Perguntas");
        curiosidades.add("Curiosidades");*/
    }

    public void gerarPerguntasAleatorias()
    {
          rClicado = false;

        //VALIDAR PERGUNTA
        if (pergs_fazer.size() > 0) {
            x = new Random().nextInt(pergs_fazer.size());
            num_pergunta = pergs_fazer.get(x);

            //GERAR LUGARES DE RESPOSTA ALEATORIO
            i = new Random().nextInt(botoes_acertar.size());
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
            finish();
        }

        mViewHolder.pergunta_texto.setText(perguntas.get(num_pergunta));
    }

    public static void set_rClicado(boolean clicado)
    {
        rClicado=clicado;
    }

    public void acharRespCorreta()
    {
        if(resp_correta.get(num_pergunta) == mViewHolder.r1.getText())
        {
            mViewHolder.r1.setBackgroundColor(0xD806B50D);
        }
        else if(resp_correta.get(num_pergunta) == mViewHolder.r2.getText())
        {
            mViewHolder.r2.setBackgroundColor(0xD806B50D);
        }
        else
        {
            mViewHolder.r3.setBackgroundColor(0xD806B50D);
        }
    }

}

