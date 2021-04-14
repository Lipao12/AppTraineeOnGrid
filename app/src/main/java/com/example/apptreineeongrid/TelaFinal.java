package com.example.apptreineeongrid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class TelaFinal extends AppCompatActivity {

    private ViewHolder mViewHolder = new ViewHolder();

    TextView t_final_score = null;
    Typeface fonte;
    int larguraTela;
    int alturaTela;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_final);

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
}