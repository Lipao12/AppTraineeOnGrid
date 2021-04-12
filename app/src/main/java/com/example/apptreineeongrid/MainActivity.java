package com.example.apptreineeongrid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private ViewHolder mViewHolder = new ViewHolder();
    Typeface fonte;
    int larguraTela;
    int alturaTela;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        this.mViewHolder.b_jogo = findViewById(R.id.TIbotao_iniciar_game);
        this.mViewHolder.b_pontuacao = findViewById(R.id.TIbotao_pontuacao);
        this.mViewHolder.view = findViewById(R.id.TIview);

        mudarFonte();
        mudarPosicao_Tamanho();

        mViewHolder.b_jogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,TelaDificuldade.class);
                startActivity(intent);
            }
        });

        mViewHolder.b_pontuacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,TelaPontuacao.class);
                startActivity(intent);
            }
        });

    }

    public static class ViewHolder{
        Button b_jogo;
        Button b_pontuacao;
        View view;
    }

    private void mudarFonte()
    {
        fonte = Typeface.createFromAsset(getAssets(),"Press Start K.ttf");
        mViewHolder.b_jogo.setTypeface(fonte);
        mViewHolder.b_pontuacao.setTypeface(fonte);
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
                //Coloca a largura igual à altura
                larguraTela = mViewHolder.view.getWidth();
                alturaTela = mViewHolder.view.getHeight();

                System.out.println("Altura da tela: "+ alturaTela);
                System.out.println("Largura da tela: "+ larguraTela);

                alturaObjeto = mViewHolder.b_jogo.getHeight();
                tamanho = mudarTamanho(alturaTela,alturaObjeto);

                mViewHolder.b_jogo.getLayoutParams().height = tamanho;
                mViewHolder.b_jogo.requestLayout();
                mViewHolder.b_pontuacao.getLayoutParams().height = tamanho;
                mViewHolder.b_pontuacao.requestLayout();

                alturaObjeto = (int)mViewHolder.b_pontuacao.getTextSize();
                tamanho = mudarTamanho(alturaTela,alturaObjeto);
                mViewHolder.b_jogo.setTextSize(tamanho/3);
                mViewHolder.b_pontuacao.setTextSize(tamanho/3);

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