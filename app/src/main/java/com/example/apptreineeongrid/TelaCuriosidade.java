package com.example.apptreineeongrid;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

public class TelaCuriosidade extends AppCompatActivity {

    TelaCuriosidade.ViewHolder mViewHolder = new TelaCuriosidade.ViewHolder();
    private Typeface fonte;
    int larguraTela;
    int alturaTela;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_curiosidade);

        this.mViewHolder.voltar = findViewById(R.id.TDvoltar);
        this.mViewHolder.curiosidade = findViewById(R.id.TCcuriosidade_texto);
        this.mViewHolder.view = findViewById(R.id.TCview);

        mudarPosicao_Tamanho();
        mudarFonte();

        mViewHolder.curiosidade.setText(getIntent().getExtras().getString("curiosidade"));

        mViewHolder.voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private static class ViewHolder{
        ImageView voltar;
        TextView curiosidade;
        View view;
    }

    private void mudarFonte()
    {
        fonte = Typeface.createFromAsset(getAssets(),"RobotoMono-Light.ttf");
        mViewHolder.curiosidade.setTypeface(fonte);
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

                alturaObjeto = mViewHolder.voltar.getHeight();
                tamanho = mudarTamanho(alturaTela,alturaObjeto);
                mViewHolder.voltar.getLayoutParams().height = tamanho;
                mViewHolder.voltar.requestLayout();

                alturaObjeto = (int) mViewHolder.curiosidade.getTextSize();
                tamanho = mudarTamanho(alturaTela,alturaObjeto)/3;
                mViewHolder.curiosidade.setTextSize(tamanho);

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