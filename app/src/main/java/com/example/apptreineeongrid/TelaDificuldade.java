package com.example.apptreineeongrid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TelaDificuldade extends AppCompatActivity {

    private TelaDificuldade.ViewHolder mViewHolder = new ViewHolder();
    Typeface fonte;
    int larguraTela;
    int alturaTela;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_dificuldade);

        mViewHolder.textoDificuldade = findViewById(R.id.TDDIFICULDADE);
        mViewHolder.b_facil = findViewById(R.id.TDfacil);
        mViewHolder.b_medio = findViewById(R.id.TDmedio);
        mViewHolder.b_dificil = findViewById(R.id.TDdificil);
        mViewHolder.b_voltar = findViewById(R.id.TDvoltar);
        mViewHolder.view = findViewById(R.id.TDview);

        mudarFonte();
        mudarPosicao_Tamanho();
        mudarPosicao();

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
        View view;
    }

    private void mudarFonte()
    {
        fonte = Typeface.createFromAsset(getAssets(),"Press Start K.ttf"); //RobotoMono-Light.ttf  //  Joystix.ttf
        mViewHolder.textoDificuldade.setTypeface(fonte);
        mViewHolder.b_facil.setTypeface(fonte);
        mViewHolder.b_medio.setTypeface(fonte);
        mViewHolder.b_dificil.setTypeface(fonte);
    }

    private void mudarPosicao_Tamanho()
    {


        //final int[] larguraTela = new int[1];
        //final int[] alturaTela = new int[1];

        mViewHolder.view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                int alturaObjeto;
                int tamanho;



                float alturaTexto;
                float tamanhoTexto;

                //Remove o listenner para não ser novamente chamado.
                mViewHolder.view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                //Coloca a largura igual à altura
                larguraTela = mViewHolder.view.getWidth();
                alturaTela = mViewHolder.view.getHeight();

                System.out.println("Altura da tela: "+ alturaTela);
                System.out.println("Largura da tela: "+ larguraTela);

                alturaObjeto = mViewHolder.b_facil.getHeight();
                System.out.println("Altura da botao: "+ alturaObjeto);
                System.out.println("Altura da botao2: "+ mViewHolder.b_dificil.getLayoutParams());

                tamanho = mudarTamanho(alturaTela,alturaObjeto);
                System.out.println("Tmanaha recebido : "+tamanho);


                mViewHolder.b_facil.getLayoutParams().height = tamanho;
                mViewHolder.b_facil.requestLayout();
                mViewHolder.b_medio.getLayoutParams().height = tamanho;
                mViewHolder.b_medio.requestLayout();
                mViewHolder.b_dificil.getLayoutParams().height = tamanho;
                mViewHolder.b_dificil.requestLayout();

                alturaObjeto = mViewHolder.b_voltar.getHeight();
                tamanho = mudarTamanho(alturaTela,alturaObjeto);
                mViewHolder.b_voltar.getLayoutParams().height = tamanho;
                mViewHolder.b_voltar.requestLayout();

                System.out.println("Altura da botao setado: "+ mViewHolder.b_facil.getHeight());

                alturaObjeto = (int) mViewHolder.b_dificil.getTextSize();
                System.out.println("Texto size FACIL: "+ mViewHolder.b_facil.getTextSize());
                System.out.println("Texto size DIFICIL: "+ alturaObjeto);
                tamanho = mudarTamanho(alturaTela,alturaObjeto)/3;
                mViewHolder.b_facil.setTextSize(tamanho);
                mViewHolder.b_medio.setTextSize(tamanho);
                mViewHolder.b_dificil.setTextSize(tamanho);
                System.out.println("Texto new size: "+ mViewHolder.b_dificil.getTextSize());

            }
        });
    }

    private void mudarPosicao()
    {
        //mViewHolder.b_facil.constrant
    }


    private int mudarTamanho(int tamanhoTela, int tamanhoObjeto)
    {
        int tamanho;

        tamanho = tamanhoTela * tamanhoObjeto / 1920;
        System.out.println("Tamanho: "+tamanho);

        return tamanho;
    }
}