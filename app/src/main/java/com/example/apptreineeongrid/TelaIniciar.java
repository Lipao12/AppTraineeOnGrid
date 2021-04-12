package com.example.apptreineeongrid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TelaIniciar extends AppCompatActivity {


    Button b_jogar;
    Button b_pontuacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_iniciar);

        b_jogar = findViewById(R.id.TIjogar);
        b_pontuacao = findViewById(R.id.TIpontuacao);

        b_jogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TelaIniciar.this, TelaDificuldade.class);
                startActivity(intent);
            }
        });

        b_pontuacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TelaIniciar.this, TelaPontucao.class);
                startActivity(intent);
            }
        });
    }
}