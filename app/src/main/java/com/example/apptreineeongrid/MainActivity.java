// DESCOBRIR UM JEITO DE COMECAR O JOGO DIRETO

package com.example.apptreineeongrid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private ViewHolder mViewHolder = new ViewHolder();
    TelaJogo t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        this.mViewHolder.b_jogo = findViewById(R.id.botao_iniciar_game);

        mViewHolder.b_jogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,TelaJogo.class);
                intent.putExtra("iniciar", true);
                startActivity(intent);
            }
        });

    }

    public static class ViewHolder{
        Button b_jogo;
    }

}