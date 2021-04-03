package com.example.apptreineeongrid;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.MediaRouteButton;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class TelaJogo extends AppCompatActivity {

    private TelaJogo.ViewHolder mViewHolder = new TelaJogo.ViewHolder();
    private ArrayList<String> perguntas;
    private ArrayList<String> resp_errada;
    private ArrayList<String> resp_correta;
    private ArrayList<String> curiosidades;
    public ArrayList<Integer> pergs_fazer;
    private ArrayList<Integer> botoes_acertar;
    private int x;
    private int i;
    private int num_pergunta;
    public int score;
    private static Boolean rClicado=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_jogo);

        this.mViewHolder.r1 = findViewById(R.id.Opc1);
        this.mViewHolder.r2 = findViewById(R.id.Opc2);
        this.mViewHolder.r3 = findViewById(R.id.Opc3);
        this.mViewHolder.b_continuar = findViewById(R.id.continuar);
        this.mViewHolder.b_vcSabia = findViewById(R.id.vcSabia);
        this.mViewHolder.pergunta_texto = findViewById(R.id.pergunta);
        this.mViewHolder.imagem_perguntas = findViewById(R.id.imageView);
        this.mViewHolder.score_texto = findViewById(R.id.score);

        /*Resources res = getResources();
        Drawable drawable = ResourcesCompat.getDrawable(res, android.R.drawable.toast_frame,null);*/

        perguntas = new ArrayList<>();
        resp_errada = new ArrayList<>();
        resp_correta = new ArrayList<>();
        curiosidades = new ArrayList<>();
        pergs_fazer = new ArrayList<>();
        botoes_acertar = new ArrayList<>();

        boolean start=getIntent().getExtras().getBoolean("iniciar");
        if(start)
        {
            mViewHolder.b_continuar.setVisibility(View.GONE);
            mViewHolder.b_vcSabia.setVisibility(View.GONE);
            score=0;
            inicializarPerguntas();
            for (x=0;x<perguntas.size();x++)
            {
                pergs_fazer.add(x);
            }
            for (i=0;i<3;i++)
            {
                botoes_acertar.add(i);
            }
            start=false;
            gerarPerguntasAleatorias();
        }   //

        mViewHolder.b_continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewHolder.r1.setBackgroundColor(0xFFFFFFFF);
                mViewHolder.r2.setBackgroundColor(0xFFFFFFFF);
                mViewHolder.r3.setBackgroundColor(0xFFFFFFFF);

                mViewHolder.b_continuar.setVisibility(View.GONE);
                mViewHolder.b_vcSabia.setVisibility(View.GONE);
                mViewHolder.pergunta_texto.setVisibility(View.VISIBLE);
                mViewHolder.imagem_perguntas.setVisibility(View.VISIBLE);
                gerarPerguntasAleatorias();
            }
        });

        mViewHolder.b_vcSabia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaJogo.this,TelaCuriosidade.class);
                intent.putExtra("curiosidade",curiosidades.get(num_pergunta));
                startActivity(intent);
            }
        });


        // VALIDAR RESPOSTA
        mViewHolder.r1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
               if(!rClicado)
                {
                    rClicado=true;
                    mViewHolder.b_continuar.setVisibility(View.VISIBLE);
                    mViewHolder.b_vcSabia.setVisibility(View.VISIBLE);
                    mViewHolder.pergunta_texto.setVisibility(View.GONE);
                    mViewHolder.imagem_perguntas.setVisibility(View.GONE);

                    if(mViewHolder.r1.getText() == resp_correta.get(num_pergunta))
                    {
                        score++;
                        mViewHolder.score_texto.setText("Score: "+score);
                        mViewHolder.r1.setBackgroundColor(0xD806B50D);
                    }
                    else
                    {
                        mViewHolder.r1.setBackgroundColor(0xFFCA1010);
                        acharRespCorreta();
                    }
                }
            }
        });
        mViewHolder.r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!rClicado)
                {
                    rClicado=true;
                    mViewHolder.b_continuar.setVisibility(View.VISIBLE);
                    mViewHolder.b_vcSabia.setVisibility(View.VISIBLE);
                    mViewHolder.pergunta_texto.setVisibility(View.GONE);
                    mViewHolder.imagem_perguntas.setVisibility(View.GONE);

                    if(mViewHolder.r2.getText() == resp_correta.get(num_pergunta))
                    {
                        score++;
                        mViewHolder.score_texto.setText("Score: "+score);
                        mViewHolder.r2.setBackgroundColor(0xD806B50D); // verde
                    }
                    else
                    {
                        mViewHolder.r2.setBackgroundColor(0xFFCA1010);
                        acharRespCorreta();
                    }
                }
            }
        });
        mViewHolder.r3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!rClicado)
                {
                    rClicado=true;
                    mViewHolder.b_continuar.setVisibility(View.VISIBLE);
                    mViewHolder.b_vcSabia.setVisibility(View.VISIBLE);
                    mViewHolder.pergunta_texto.setVisibility(View.GONE);
                    mViewHolder.imagem_perguntas.setVisibility(View.GONE);
                    if(mViewHolder.r3.getText() == resp_correta.get(num_pergunta))
                    {
                        score++;
                        mViewHolder.score_texto.setText("Score: "+score);
                        mViewHolder.r3.setBackgroundColor(0xD806B50D);
                    }
                    else
                    {
                        mViewHolder.r3.setBackgroundColor(0xFFCA1010);
                        acharRespCorreta();
                    }
                }
            }
        });
    }

    public static class ViewHolder{
        Button r1;
        Button r2;
        Button r3;
        Button b_continuar;
        Button b_vcSabia;
        TextView pergunta_texto;
        TextView score_texto;
        ImageView imagem_perguntas;
    }

    public void gerarPerguntasAleatorias()
    {
          rClicado = false;

        //VALIDAR PERGUNTA
        if (pergs_fazer.size() > 0) {
            x = new Random().nextInt(pergs_fazer.size());
            num_pergunta = pergs_fazer.get(x);

            //GERAR LUGARES DE RESPOSTA ALEATORIO
            i = new Random().nextInt(botoes_acertar.size());
            if (i == 1) {
                mViewHolder.r1.setText(resp_correta.get(num_pergunta));
                mViewHolder.r2.setText(resp_errada.get(2 * num_pergunta));
                mViewHolder.r3.setText(resp_errada.get(2 * num_pergunta + 1));
            } else if (i == 2) {
                mViewHolder.r2.setText(resp_correta.get(num_pergunta));
                mViewHolder.r1.setText(resp_errada.get(2 * num_pergunta));
                mViewHolder.r3.setText(resp_errada.get(2 * num_pergunta + 1));
            } else {
                mViewHolder.r3.setText(resp_correta.get(num_pergunta));
                mViewHolder.r1.setText(resp_errada.get(2 * num_pergunta));
                mViewHolder.r2.setText(resp_errada.get(2 * num_pergunta + 1));
            }

            pergs_fazer.remove(x);
        } else if (pergs_fazer.size() == 0) {
            finish();
        }

        mViewHolder.pergunta_texto.setText(perguntas.get(num_pergunta));
    }

    public static void set_rClicado(boolean clicado)
    {
        rClicado=clicado;
    }

    public void acharRespCorreta()
    {
        if(resp_correta.get(num_pergunta) == mViewHolder.r1.getText())
        {
            mViewHolder.r1.setBackgroundColor(0xD806B50D);
        }
        else if(resp_correta.get(num_pergunta) == mViewHolder.r2.getText())
        {
            mViewHolder.r2.setBackgroundColor(0xD806B50D);
        }
        else
        {
            mViewHolder.r3.setBackgroundColor(0xD806B50D);
        }
    }

    private void inicializarPerguntas() // FOI COLOCADA ATE A PERGUNTA 20
    {
        // FACIL
        perguntas.add("Qual é a camada mais externa do Sol?");
        curiosidades.add("A estrutura do Sol é composta pelas principais regiões: núcleo, zona radiativa, zona convectiva, fotosfera, cromosfera e coroa.");
        resp_correta.add("Coroa");
        resp_errada.add("Fotosfera");
        resp_errada.add("Núcleo");

        perguntas.add("Qual elemento químico mais abundante no Sol?");
        curiosidades.add("O Sol é constituído em sua maioria de Hidrogênio (91,2%), mas também possui Hélio (8,7%), Oxigênio(0,078%) e Carbono(0,043%)");
        resp_correta.add("H");
        resp_errada.add("Fe");
        resp_errada.add("He");

        perguntas.add("Qual região é responsável pela maior parte da radiação visível emitida pelo Sol?");
        curiosidades.add("A fotosfera, primeira região da atmosfera solar, com 330 km de espessura e temperatura próxima de 5.800 K.");
        resp_correta.add("Fotosfera");
        resp_errada.add("Núcleo");
        resp_errada.add("Cromosfera");

        perguntas.add("Qual a potência total disponibilizada pelo Sol à Terra?");
        curiosidades.add("A densidade média anual do fluxo energético proveniente da radiação solar (irradiância solar) recebe o nome de “constante solar” e corresponde ao valor de 1.367 W/m2 . Considerando que o raio médio da Terra é 6.371 km, conclui-se que a potência total disponibilizada pelo Sol à Terra é de aproximadamente 174 mil TW (terawatts)");
        resp_correta.add("174 mil Terawatts");
        resp_errada.add("45 Kilowatts");
        resp_errada.add("152 mil Megawatts");

        perguntas.add("Parte da potência total disponibilizada pelo Sol à Terra é absorvida ou refletida pela atmosfera. Quantos % dessa potência chegam à superfície terrestre?");
        curiosidades.add("Cerca de 54 % da irradiância solar que incide no topo da atmosfera, é refletida (7 %) e absorvida (47 %) pela superfície terrestre (os 46 % restantes são absorvidos ou refletidos diretamente pela atmosfera). Ou seja, da potência total disponibilizada pelo Sol à Terra, cerca de 94 mil TW chegam efetivamente à superfície terrestre.");
        resp_correta.add("54%");
        resp_errada.add("76%");
        resp_errada.add("28%");

        perguntas.add("Qual foi a primeira Usina Fotovoltaica implantada no Brasil?");
        curiosidades.add("A primeira UFV implantada no Brasil foi um empreendimento privado, da empresa MPX, localizado no município de Tauá-CE, a cerca de 360 km de Fortaleza. A UFV Tauá entrou em operação em julho de 2011 e tem potência instalada de 1,0 MWp, em 4.680 módulos de p-Si de 215Wp, conta com 9 inversores de 100kWp e injeta a energia na rede de 13,8 kV da Coelce (Companhia Energética do Ceará).");
        resp_correta.add("Usina solar de Tauá");
        resp_errada.add("Complexo solar Lapa");
        resp_errada.add("Usina solar São Gonçalo");

        // MEDIA
        perguntas.add("Qual o elemento mais utilizado na fabricação de células fotovoltaicas?");
        curiosidades.add("Os átomos de Si são tetravalentes, ou seja, caracterizam-se por possuírem 4 elétrons de valência que formam ligações covalentes com os átomos vizinhos, resultando em 8 elétrons compartilhados por cada átomo, constituindo uma rede cristalina.");
        resp_correta.add("Silício");
        resp_errada.add("Arsênio");
        resp_errada.add("Gálio");

        perguntas.add("Uma associação em série dos módulos tem como objetivo uma soma de qual grandeza elétrica?");
        curiosidades.add("Na conexão em série, o terminal positivo de um dispositivo fotovoltaico é conectado ao terminal negativo do outro dispositivo, e assim por diante. Para dispositivos idênticos e submetidos à mesma irradiância, quando a ligação é em série, as tensões são somadas e a corrente elétrica não é afetada, ou seja:\n" +
                "V=V1+V2+....+Vn \n" +
                "I=I1=I2=....=In ");
        resp_correta.add("Tensão");
        resp_errada.add("Corrente");
        resp_errada.add("Potência");

        perguntas.add("Uma associação em paralelo dos módulos tem como objetivo uma soma de qual grandeza elétrica?");
        curiosidades.add("Na associação em paralelo, os terminais positivos dos dispositivos são interligados entre si, assim como os terminais negativos. As correntes elétricas são somadas, permanecendo inalterada a tensão. Ou seja:\n" +
                "I=I1+I2+...+In\n" +
                "V=V1=V2=...=Vn");
        resp_correta.add("Corrente");
        resp_errada.add("Tensão");
        resp_errada.add("Potência");

        perguntas.add("Em um sistema fotovoltaico há um componente responsável por converter uma corrente contínua (c.c.) em corrente alternada (c.a.). Que componente é esse?");
        curiosidades.add("Um inversor é um dispositivo eletrônico que fornece energia elétrica em corrente alternada (c.a.) a partir de uma fonte de energia elétrica em corrente contínua (c.c.). A tensão c.a. de saída deve ter amplitude, frequência e conteúdo harmônico adequados às cargas a serem alimentadas.");
        resp_correta.add("Inversores");
        resp_errada.add("Baterias");
        resp_errada.add("Controladores de carga");

        perguntas.add("Em qual sistema fotovoltaico é indispensável o armazenamento de energia?");
        curiosidades.add("Sistemas isolados (SFI), em geral, necessitam de algum tipo de armazenamento. O armazenamento pode ser em baterias, quando se deseja utilizar aparelhos elétricos nos períodos em que não há geração fotovoltaica, ou em outras formas de armazenamento de energia.");
        resp_correta.add("Sistemas isolados");
        resp_errada.add("Sistema de bombeamento");
        resp_errada.add("Sistemas Conectados à Rede");

        perguntas.add("Qual a densidade média anual do fluxo energético proveniente da radiação solar?");
        curiosidades.add("As céA densidade média anual do fluxo energético proveniente da radiação solar (irradiância solar), quando medida num plano perpendicular à direção da propagação dos raios solares no topo da atmosfera terrestre recebe o nome de “constante solar” e corresponde ao valor de 1.367 W/m2.");
        resp_correta.add("1.367 W/m2");
        resp_errada.add("529 W/m2");
        resp_errada.add("1145 W/m2");

        perguntas.add("Qual sistema fotovoltaico trabalha com Sistema de compensação de energia elétrica?");
        curiosidades.add("Nos sistemas conectados à rede, a energia elétrica gerada é cedida, por meio de empréstimo gratuito, à distribuidora local e posteriormente compensada com o consumo de energia elétrica ativa dessa mesma unidade consumidora, ou seja, a energia produzida pelo sistema não é diretamente consumida pelo provedor.");
        resp_correta.add("Sistemas Conectados à Rede");
        resp_errada.add("Sistemas isolados");
        resp_errada.add("Sistema de bombeamento");

        // DIFICIL
        perguntas.add("Qual destes fatores é diretamente proporcional à corrente elétrica produzida no módulo fotovoltaico?");
        curiosidades.add("A corrente elétrica gerada por uma célula fotovoltaica aumenta linearmente com o aumento da irradiância solar incidente, enquanto que a tensão de circuito aberto (Voc) aumenta de forma logarítmica.");
        resp_correta.add("Irradiância  Solar");
        resp_errada.add("Resistência do módulo");
        resp_errada.add("Temperatura");

        perguntas.add("Qual destes componentes de um sistema fotovoltaico só é necessário em sistemas off-grid (independentes)?");
        curiosidades.add("Controladores de carga são incluídos na maioria dos Sistemas Fotovoltaicos Independentes SFI com o objetivo de proteger a bateria (ou banco de baterias) contra cargas e descargas excessivas, aumentando a sua vida útil.");
        resp_correta.add("Controladores de carga");
        resp_errada.add("Inversores");
        resp_errada.add("Conversores");

        perguntas.add("Individualmente as células fotovoltaicas de Silício possuem tensão na ordem de:");
        curiosidades.add("As células de Silício possuem, individualmente, uma tensão muito baixa, sendo da ordem de 0,5 a 0,8V. Assim, para se obterem níveis de tensão adequados, as células são conectadas em série, produzindo uma tensão resultante equivalente à soma das tensões individuais de cada célula.");
        resp_correta.add("0,5 a 0,8V");
        resp_errada.add("10 a 15V");
        resp_errada.add("2 a 5V");

        perguntas.add("Seja a tensão de uma célula fotovoltaica 0,6V. Qual a tensão de um módulo com 30 destas células conectadas em paralelo?");
        curiosidades.add("Na associação em paralelo as correntes são somadas e a tensão permanece inalterada, assim, a tensão do módulo é a mesma da célula. ");
        resp_correta.add("0,6V");
        resp_errada.add("15V");
        resp_errada.add("18V");

        perguntas.add("Seja a tensão de uma célula fotovoltaica 0,6V. Qual a tensão de um módulo com 30 destas células conectadas em série?");
        curiosidades.add("Na associação em série as tensões são somadas e a corrente possui valor único, assim, 30 células com 0,6V geram uma tensão de 18V no módulo.");
        resp_correta.add("18V");
        resp_errada.add("15V");
        resp_errada.add("0,6V");

        perguntas.add("2 elementos são mais comumente utilizados no processo de Dopagem Eletrônica do Silício, mudando drasticamente suas propriedades elétricas. Quais são eles?");
        curiosidades.add("Os átomos de Si possuem 4 elétrons de valência, assim a dopagem pode ser feita de duas maneiras. Introduzindo um átomo de Fósforo (Pentavalente) chamado de impureza doadora de elétrons, ou dopante tipo n, ou um átomo de Boro (trivalente) denominado impureza recebedora de elétrons ou dopante tipo p.");
        resp_correta.add("Boro e Fósforo");
        resp_errada.add("Ferro e Manganês");
        resp_errada.add("Arsênio e Alumínio");

        perguntas.add("Qual tipo de bateria é mais utilizado nos sistemas fotovoltaicos isolados?");
        curiosidades.add("As células Chumbo-Ácido são a tecnologia de armazenamento de energia de menor custo por Wh atualmente disponível no mercado para aplicação em sistemas fotovoltaicos.");
        resp_correta.add("Chumbo-Ácido");
        resp_errada.add("Níquel Cádmio");
        resp_errada.add("Lítio-íon");

    }


}

