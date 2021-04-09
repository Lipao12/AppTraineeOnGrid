package com.example.apptreineeongrid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TelaFinal extends AppCompatActivity {

    private ViewHolder mViewHolder = new ViewHolder();
    private static final String ARQUIVO_HIGHSCORE = "ArquivoHighscore";
    Typeface fonte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_final);

        this.mViewHolder.b_tInicial = findViewById(R.id.TFvoltar);
        this.mViewHolder.texto_score = findViewById(R.id.TFscoreFinal);
        this.mViewHolder.texto_newRecord = findViewById(R.id.TFtextoRecord);

        mudarFonte();

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
    }

    private void mudarFonte()
    {
        fonte = Typeface.createFromAsset(getAssets(),"Press Start K.ttf");
        mViewHolder.texto_newRecord.setTypeface(fonte);
        mViewHolder.texto_score.setTypeface(fonte);
    }
}