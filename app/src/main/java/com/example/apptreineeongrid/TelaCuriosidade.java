package com.example.apptreineeongrid;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TelaCuriosidade extends AppCompatActivity {

    TelaCuriosidade.ViewHolder mViewHolder = new TelaCuriosidade.ViewHolder();
    private Typeface fonte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_curiosidade);

        this.mViewHolder.voltar = findViewById(R.id.TDvoltar);
        this.mViewHolder.curiosidade = findViewById(R.id.TCcuriosidade_texto);

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

    private void mudarFonte()
    {
        fonte = Typeface.createFromAsset(getAssets(),"RobotoMono-Light.ttf");
        mViewHolder.curiosidade.setTypeface(fonte);
    }
}