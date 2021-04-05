package com.example.apptreineeongrid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class TelaDificuldade extends AppCompatActivity {

    private TelaDificuldade.ViewHolder mViewHolder = new ViewHolder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_dificuldade);

        mViewHolder.b_facil = findViewById(R.id.facil);
        mViewHolder.b_medio = findViewById(R.id.medio);
        mViewHolder.b_dificil = findViewById(R.id.dificil);

        Intent intent = new Intent(TelaDificuldade.this,TelaJogo.class);

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

    }
    public static class ViewHolder{
        Button b_facil;
        Button b_medio;
        Button b_dificil;
        Button b_voltar;
    }
}