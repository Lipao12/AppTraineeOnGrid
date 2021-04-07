package com.example.apptreineeongrid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class TelaFinal extends AppCompatActivity {

    private ViewHolder mViewHolder = new ViewHolder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_final);

        this.mViewHolder.b_tInicial = findViewById(R.id.voltarTF);
        this.mViewHolder.texto_score = findViewById(R.id.scoreFinal);

        mViewHolder.texto_score.setText(""+getIntent().getExtras().getInt("score"));

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
    }
}