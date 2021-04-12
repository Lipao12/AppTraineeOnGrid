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

    TextView t_final_score = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_final);

        this.mViewHolder.b_tInicial = findViewById(R.id.voltarTF);

        this.t_final_score = findViewById(R.id.final_score);
        this.t_final_score.setText(String.valueOf(getIntent().getExtras().getInt("final_score")));

        mViewHolder.b_tInicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(TelaFinal.this, MainActivity.class);
                home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(home);
            }
        });

    }

    public static class ViewHolder{
        ImageView b_tInicial;
    }
}