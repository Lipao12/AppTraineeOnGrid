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
    int larguraTela;
    int alturaTela;
    Typeface fonte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        this.mViewHolder.b_jogo = findViewById(R.id.botao_iniciar_game);
        this.mViewHolder.view = findViewById(R.id.TMview);

        mudarFonte();
        mudarPosicao_Tamanho();

        mViewHolder.b_jogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TelaLogin.class);
               // intent.putExtra("iniciar", true);
                startActivity(intent);
            }
        });

    }

    public static class ViewHolder{
        Button b_jogo;
        View view;
    }

    private void mudarFonte()
    {
        fonte = Typeface.createFromAsset(getAssets(),"Press Start K.ttf");
        mViewHolder.b_jogo.setTypeface(fonte);
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

                alturaObjeto = mViewHolder.b_jogo.getHeight();
                tamanho = mudarTamanho(alturaTela,alturaObjeto);
                mViewHolder.b_jogo.getLayoutParams().height = tamanho;
                mViewHolder.b_jogo.requestLayout();

                alturaObjeto = (int) mViewHolder.b_jogo.getTextSize();
                tamanho = mudarTamanho(alturaTela,alturaObjeto)/3;
                mViewHolder.b_jogo.setTextSize(tamanho);
            }
        });
    }

    private int mudarTamanho(int tamanhoTela, int tamanhoObjeto)
    {
        int tamanho;
        tamanho = tamanhoTela * tamanhoObjeto / 1920;
        return tamanho;
    }

}