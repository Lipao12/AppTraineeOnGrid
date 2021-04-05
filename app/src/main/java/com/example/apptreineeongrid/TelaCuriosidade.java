package com.example.apptreineeongrid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class TelaCuriosidade extends AppCompatActivity {

    TelaCuriosidade.ViewHolder mViewHolder = new TelaCuriosidade.ViewHolder();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_curiosidade);

        this.mViewHolder.voltar = findViewById(R.id.voltar);
        this.mViewHolder.curiosidade = findViewById(R.id.curiosidade_texto);

        mViewHolder.curiosidade.setText(getIntent().getExtras().getString("curiosidade"));

        mViewHolder.voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private static class ViewHolder{
        ImageView voltar;
        TextView curiosidade;
    }
}