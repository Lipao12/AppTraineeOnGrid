package com.example.apptreineeongrid;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class TelaPontuacao extends AppCompatActivity {

    private ViewHolder mViewHolder = new ViewHolder();
    Typeface fonte;
    private static final String ARQUIVO_HIGHSCORE = "ArquivoHighscore";
    int highscoreFacil;
    int highscoreMedio;
    int highscoreDificil;
    int larguraTela;
    int alturaTela;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_pontuacao);

        this.mViewHolder.b_apagar = findViewById(R.id.TPbapagar);
        this.mViewHolder.b_voltar = findViewById(R.id.TPbvoltar);
        this.mViewHolder.t_scoreFacil = findViewById(R.id.TPscoreFacil);
        this.mViewHolder.t_scoreMedio = findViewById(R.id.TPscoreMedio);
        this.mViewHolder.t_scoreDificil = findViewById(R.id.TPscoreDificil);
        this.mViewHolder.view = findViewById(R.id.TPview);

        SharedPreferences preferences = getSharedPreferences(ARQUIVO_HIGHSCORE, 0);
        SharedPreferences.Editor editor = preferences.edit();

        mudarFonte();
        mudarPosicao_Tamanho();

        highscoreFacil = preferences.getInt("highscoreFacil",0);
        highscoreMedio = preferences.getInt("highscoreMedio",0);
        highscoreDificil = preferences.getInt("highscoreDificil",0);

        mViewHolder.t_scoreFacil.setText(""+highscoreFacil);
        mViewHolder.t_scoreMedio.setText(""+highscoreMedio);
        mViewHolder.t_scoreDificil.setText(""+highscoreDificil);

        mViewHolder.b_apagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewHolder.t_scoreFacil.setText("0");
                mViewHolder.t_scoreMedio.setText("0");
                mViewHolder.t_scoreDificil.setText("0");

                editor.putInt("highscoreFacil", 0);
                editor.putInt("highscoreMedio", 0);
                editor.putInt("highscoreDificil", 0);
                editor.commit();
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
                //Coloca a largura igual à altura
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
}