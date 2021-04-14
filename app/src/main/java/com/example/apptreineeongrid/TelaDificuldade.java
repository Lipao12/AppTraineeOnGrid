package com.example.apptreineeongrid;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class TelaDificuldade extends AppCompatActivity {

    private TelaDificuldade.ViewHolder mViewHolder = new ViewHolder();
    Typeface fonte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_dificuldade);

        mViewHolder.textoDificuldade = findViewById(R.id.DIFICULDADE);
        mViewHolder.b_facil = findViewById(R.id.facil);
        mViewHolder.b_medio = findViewById(R.id.medio);
        mViewHolder.b_dificil = findViewById(R.id.dificil);
        mViewHolder.b_voltar = findViewById(R.id.voltarTD);

        fonte = Typeface.createFromAsset(getAssets(),"Press Start K.ttf"); //RobotoMono-Light.ttf  //  Joystix.ttf
        mViewHolder.textoDificuldade.setTypeface(fonte);
        mViewHolder.b_facil.setTypeface(fonte);
        mViewHolder.b_medio.setTypeface(fonte);
        mViewHolder.b_dificil.setTypeface(fonte);

        Intent intent = new Intent(TelaDificuldade.this, LoadingScreen.class);

        intent.putExtra("token", getIntent().getExtras().getString("token"));

        mViewHolder.b_facil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("dificuldade",'f');
                startActivity(intent);
            }
        });

        mViewHolder.b_medio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("dificuldade",'m');
                startActivity(intent);
            }
        });

        mViewHolder.b_dificil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("dificuldade",'d');
                startActivity(intent);
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
        TextView textoDificuldade;
        Button b_facil;
        Button b_medio;
        Button b_dificil;
        ImageView b_voltar;
    }


}