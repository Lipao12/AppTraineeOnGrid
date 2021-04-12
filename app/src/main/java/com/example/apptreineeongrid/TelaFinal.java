package com.example.apptreineeongrid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

public class TelaFinal extends AppCompatActivity {

    private ViewHolder mViewHolder = new ViewHolder();
    private static final String ARQUIVO_HIGHSCORE = "ArquivoHighscore";
    Typeface fonte;
    int larguraTela;
    int alturaTela;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_final);

        this.mViewHolder.b_tInicial = findViewById(R.id.TFvoltar);
        this.mViewHolder.texto_score = findViewById(R.id.TFscoreFinal);
        this.mViewHolder.texto_newRecord = findViewById(R.id.TFtextoRecord);
        this.mViewHolder.view = findViewById(R.id.TFview);

        mudarFonte();
        mudarPosicao_Tamanho();

        int score = getIntent().getExtras().getInt("score");
        char dificuldade = getIntent().getExtras().getChar("dificuldade");

        mViewHolder.texto_score.setText(""+score);

        SharedPreferences preferences = getSharedPreferences(ARQUIVO_HIGHSCORE, 0);
        SharedPreferences.Editor editor = preferences.edit();

        if(dificuldade == 'f'){
            int highscore = preferences.getInt("highscoreFacil",0);
            if(score > highscore)
            {
                mViewHolder.texto_newRecord.setVisibility(View.VISIBLE);
                editor.putInt("highscoreFacil", score);
                editor.commit();
            }
        }
        else if(dificuldade == 'm'){
            System.out.println("Medio");
            int highscore = preferences.getInt("highscoreMedio",0);
            if(score > highscore)
            {
                mViewHolder.texto_newRecord.setVisibility(View.VISIBLE);
                editor.putInt("highscoreMedio", score);
                editor.commit();
            }
        }
        else {
            int highscore = preferences.getInt("highscoreDificil",0);
            if(score > highscore)
            {
                mViewHolder.texto_newRecord.setVisibility(View.VISIBLE);
                editor.putInt("highscoreDificil", score);
                editor.commit();
            }
        }

        mViewHolder.b_tInicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaFinal.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("finishApplication", true);
                startActivity(intent);
            }
        });

    }

    public static class ViewHolder{
        ImageView b_tInicial;
        TextView texto_score;
        TextView texto_newRecord;
        View view;
    }

    private void mudarFonte()
    {
        fonte = Typeface.createFromAsset(getAssets(),"Press Start K.ttf");
        mViewHolder.texto_newRecord.setTypeface(fonte);
        mViewHolder.texto_score.setTypeface(fonte);
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