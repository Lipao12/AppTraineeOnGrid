package com.example.apptreineeongrid;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
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

        SharedPreferences preferences = getSharedPreferences(ARQUIVO_HIGHSCORE, 0);
        SharedPreferences.Editor editor = preferences.edit();

        mudarFonte();

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
    }

    private void mudarFonte()
    {
        fonte = Typeface.createFromAsset(getAssets(),"Press Start K.ttf");
        mViewHolder.t_scoreFacil.setTypeface(fonte);
        mViewHolder.t_scoreMedio.setTypeface(fonte);
        mViewHolder.t_scoreDificil.setTypeface(fonte);
        mViewHolder.b_apagar.setTypeface(fonte);
    }
}