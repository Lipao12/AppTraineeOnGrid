package com.example.apptreineeongrid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;

public class TelaIniciar extends AppCompatActivity {
    Button b_jogar;
    Button b_pontuacao;
    Button b_trocarUser;
    View view;
    Typeface fonte;
    int larguraTela;
    int alturaTela;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_iniciar);

        this.b_jogar = findViewById(R.id.TIjogar);
        this.b_pontuacao = findViewById(R.id.TIpontuacao);
        this.view = findViewById(R.id.TIview);
        this.b_trocarUser = findViewById(R.id.TItrocarUsuario);

        mudarFonte();
        mudarPosicao_Tamanho();

        this.b_jogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TelaIniciar.this, TelaDificuldade.class);
                intent.putExtra("token", getIntent().getExtras().getString("token"));
                startActivity(intent);
            }
        });

        this.b_pontuacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TelaIniciar.this, TelaPontucao.class);
                intent.putExtra("token", getIntent().getExtras().getString("token"));
                startActivity(intent);
            }
        });

        this.b_trocarUser.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void mudarFonte()
    {
        fonte = Typeface.createFromAsset(getAssets(),"Press Start K.ttf");
        this.b_pontuacao.setTypeface(fonte);
        this.b_jogar.setTypeface(fonte);
        this.b_trocarUser.setTypeface(fonte);
    }

    private void mudarPosicao_Tamanho()
    {
        this.view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                int alturaObjeto;
                int tamanho;

                //Remove o listenner para não ser novamente chamado.
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                larguraTela = view.getWidth();
                alturaTela = view.getHeight();

                System.out.println("Altura da tela: "+ alturaTela);
                System.out.println("Largura da tela: "+ larguraTela);

                alturaObjeto = b_jogar.getHeight();
                tamanho = mudarTamanho(alturaTela,alturaObjeto);
                b_jogar.getLayoutParams().height = tamanho;
                b_jogar.requestLayout();
                b_pontuacao.getLayoutParams().height = tamanho;
                b_pontuacao.requestLayout();
                b_trocarUser.getLayoutParams().height = tamanho/2;
                b_trocarUser.requestLayout();


                alturaObjeto = (int) b_jogar.getTextSize();
                tamanho = mudarTamanho(alturaTela,alturaObjeto)/3;
                b_jogar.setTextSize(tamanho);
                b_pontuacao.setTextSize(tamanho);
                b_trocarUser.setTextSize(tamanho/3);
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
}