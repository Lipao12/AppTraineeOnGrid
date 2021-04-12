package com.example.apptreineeongrid;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewTreeObserver;
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
    private Typeface fonte;
    private int perg_estacionada;
    private char mem_dificuldade;
    int larguraTela;
    int alturaTela;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_jogo);

        this.mViewHolder.r1 = findViewById(R.id.TJOpc1);
        this.mViewHolder.r2 = findViewById(R.id.TJOpc2);
        this.mViewHolder.r3 = findViewById(R.id.TJOpc3);
        this.mViewHolder.b_voltarTinicial = findViewById(R.id.TJvoltar);
        this.mViewHolder.b_continuar = findViewById(R.id.TJcontinuar);
        this.mViewHolder.b_vcSabia = findViewById(R.id.TJvcSabia);
        this.mViewHolder.pergunta_texto = findViewById(R.id.TJpergunta);
        this.mViewHolder.imagem_perguntas = findViewById(R.id.TJimageView);
        this.mViewHolder.score_texto = findViewById(R.id.TJqnt_perguntas);
        this.mViewHolder.rosto_feliz = findViewById(R.id.TJrosto_feliz);
        this.mViewHolder.rosto_triste = findViewById(R.id.TJrosto_triste);
        this.mViewHolder.view = findViewById(R.id.TJview);

        /*Resources res = getResources();
        Drawable drawable = ResourcesCompat.getDrawable(res, android.R.drawable.toast_frame,null);*/

        perguntas = new ArrayList<>();
        resp_errada = new ArrayList<>();
        resp_correta = new ArrayList<>();
        curiosidades = new ArrayList<>();
        pergs_fazer = new ArrayList<>();
        botoes_acertar = new ArrayList<>();

        mudarFonte();
        mudarPosicao_Tamanho();

        char dificuldade = getIntent().getExtras().getChar("dificuldade");

        System.out.println("Dificuldade: "+dificuldade);
        if(dificuldade != '\0' )
        {
            mem_dificuldade = dificuldade;
            mViewHolder.rosto_triste.setVisibility(View.GONE);
            mViewHolder.rosto_feliz.setVisibility(View.GONE);
            mViewHolder.b_continuar.setVisibility(View.GONE);
            mViewHolder.b_vcSabia.setVisibility(View.GONE);

            perg_estacionada=1;
            score=0;

            inicializarPerguntas(dificuldade);
            for (x=0;x<perguntas.size();x++)
            {
                pergs_fazer.add(x);
            }
            for (i=0;i<3;i++)
            {
                botoes_acertar.add(i);
            }
            dificuldade = '\0';
            gerarPerguntasAleatorias();
            imprimirQuantidadeDePergunta();
        }

        mViewHolder.b_continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewHolder.r1.setBackgroundColor(0xFFFFFFFF);
                mViewHolder.r2.setBackgroundColor(0xFFFFFFFF);
                mViewHolder.r3.setBackgroundColor(0xFFFFFFFF);

                mViewHolder.rosto_triste.setVisibility(View.GONE);
                mViewHolder.rosto_feliz.setVisibility(View.GONE);
                mViewHolder.b_continuar.setVisibility(View.GONE);
                mViewHolder.b_vcSabia.setVisibility(View.GONE);
                mViewHolder.pergunta_texto.setVisibility(View.VISIBLE);
                mViewHolder.imagem_perguntas.setVisibility(View.VISIBLE);
                imprimirQuantidadeDePergunta();
               // if(!jogoAcabou[0])
                    gerarPerguntasAleatorias();
               // else
               // {
             //       Intent intent = new Intent(TelaJogo.this,TelaFinal.class);
               //     startActivity(intent);
              //  }
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

        mViewHolder.b_voltarTinicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaJogo.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("finishApplication", true);
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
                    mViewHolder.imagem_perguntas.setVisibility(View.INVISIBLE);

                    if(mViewHolder.r1.getText() == resp_correta.get(num_pergunta))
                    {
                        score++;
                        //mViewHolder.score_texto.setText("/"+score);
                        mViewHolder.rosto_feliz.setVisibility(View.VISIBLE);
                        acharRespCorreta();
                    }
                    else
                    {
                        mViewHolder.rosto_triste.setVisibility(View.VISIBLE);
                        mViewHolder.r1.setBackgroundColor(0xD8CA1010);
                        acharRespCorreta();
                      //  jogoAcabou[0] = true;
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
                    mViewHolder.imagem_perguntas.setVisibility(View.INVISIBLE);

                    if(mViewHolder.r2.getText() == resp_correta.get(num_pergunta))
                    {
                        score++;
                        //mViewHolder.score_texto.setText(""+score);
                        mViewHolder.rosto_feliz.setVisibility(View.VISIBLE);
                        acharRespCorreta();
                    }
                    else
                    {
                        mViewHolder.rosto_triste.setVisibility(View.VISIBLE);
                        mViewHolder.r2.setBackgroundColor(0xD8CA1010);
                        acharRespCorreta();
                       // jogoAcabou[0] = true;
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
                    mViewHolder.imagem_perguntas.setVisibility(View.INVISIBLE);

                    if(mViewHolder.r3.getText() == resp_correta.get(num_pergunta))
                    {
                        score++;
                        //mViewHolder.score_texto.setText(""+score);
                        mViewHolder.rosto_feliz.setVisibility(View.VISIBLE);
                        acharRespCorreta();
                    }
                    else
                    {
                        mViewHolder.rosto_triste.setVisibility(View.VISIBLE);
                        mViewHolder.r3.setBackgroundColor(0xD8CA1010);
                        acharRespCorreta();
                       // jogoAcabou[0] = true;
                    }
                }
            }
        });
    }

    public static class ViewHolder{
        Button r1;
        Button r2;
        Button r3;
        ImageView b_voltarTinicial;
        Button b_continuar;
        Button b_vcSabia;
        TextView pergunta_texto;
        TextView score_texto;
        ImageView imagem_perguntas;
        ImageView rosto_feliz;
        ImageView rosto_triste;
        View view;
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
            Intent intent = new Intent(TelaJogo.this,TelaFinal.class);
            intent.putExtra("dificuldade", mem_dificuldade);
            intent.putExtra("score", score);
            startActivity(intent);
        }

        mViewHolder.pergunta_texto.setText(perguntas.get(num_pergunta));
    }

    public void imprimirQuantidadeDePergunta()
    {
        mViewHolder.score_texto.setText(perg_estacionada+"/"+perguntas.size());
        perg_estacionada++;
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

    private void mudarFonte()
    {
        fonte = Typeface.createFromAsset(getAssets(),"RobotoMono-Light.ttf");
        mViewHolder.pergunta_texto.setTypeface(fonte);
        mViewHolder.r1.setTypeface(fonte);
        mViewHolder.r2.setTypeface(fonte);
        mViewHolder.r3.setTypeface(fonte);
    }

    private void mudarPosicao_Tamanho()
    {
        mViewHolder.view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                int alturaObjeto;
                int tamanho;

                //Remove o listenner para não ser novamente chamado.
                mViewHolder.view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                //Coloca a largura igual à altura
                larguraTela = mViewHolder.view.getWidth();
                alturaTela = mViewHolder.view.getHeight();

                System.out.println("Altura da tela: "+ alturaTela);
                System.out.println("Largura da tela: "+ larguraTela);

                alturaObjeto = mViewHolder.r1.getHeight();
                System.out.println("Altura da botao: "+ alturaObjeto);

                tamanho = mudarTamanho(alturaTela,alturaObjeto);
                System.out.println("Tmanaha recebido : "+tamanho);

                // BOTOES RESPOSTA
                mViewHolder.r1.getLayoutParams().height = tamanho;
                mViewHolder.r1.requestLayout();
                mViewHolder.r2.getLayoutParams().height = tamanho;
                mViewHolder.r2.requestLayout();
                mViewHolder.r3.getLayoutParams().height = tamanho;
                mViewHolder.r3.requestLayout();
                mViewHolder.b_voltarTinicial.getLayoutParams().height = tamanho;
                mViewHolder.b_voltarTinicial.requestLayout();

                // BOTAO CASA
                alturaObjeto = mViewHolder.b_voltarTinicial.getHeight();
                tamanho = mudarTamanho(alturaTela,alturaObjeto);
                mViewHolder.b_voltarTinicial.getLayoutParams().height = tamanho/2;
                mViewHolder.b_voltarTinicial.requestLayout();

                // BOTAO CONTINUAR
               /* alturaObjeto = mViewHolder.b_continuar.getHeight();
                System.out.println("Altura da botao CONTINUE: "+ alturaObjeto);
                tamanho = mudarTamanho(alturaTela,75);
                System.out.println("Tmanaha recebido CONTINUE: "+tamanho);
                mViewHolder.b_continuar.getLayoutParams().height = tamanho*5;
                mViewHolder.b_continuar.requestLayout();

                // BOTAO VC SABIA
                alturaObjeto = mViewHolder.b_vcSabia.getHeight();
                tamanho = mudarTamanho(alturaTela,250);
                mViewHolder.b_vcSabia.getLayoutParams().height = tamanho;
                mViewHolder.b_vcSabia.requestLayout();*/

                // ROTOS
                alturaObjeto = mViewHolder.rosto_feliz.getHeight();
                tamanho = mudarTamanho(alturaTela,alturaObjeto);
                mViewHolder.rosto_feliz.getLayoutParams().height = tamanho;
                mViewHolder.rosto_feliz.requestLayout();
                mViewHolder.rosto_triste.getLayoutParams().height = tamanho;
                mViewHolder.rosto_triste.requestLayout();

                alturaObjeto = mViewHolder.imagem_perguntas.getHeight();
                tamanho = mudarTamanho(alturaTela,alturaObjeto);
                mViewHolder.imagem_perguntas.getLayoutParams().height = tamanho;
                mViewHolder.imagem_perguntas.requestLayout();

                // TEXTO PERGUNTA
                alturaObjeto = (int) mViewHolder.pergunta_texto.getTextSize();
                tamanho = mudarTamanho(alturaTela,alturaObjeto)/3;
                mViewHolder.pergunta_texto.setTextSize(tamanho);

                alturaObjeto = (int) mViewHolder.r1.getTextSize();
                tamanho = mudarTamanho(alturaTela,alturaObjeto)/3;
                mViewHolder.r1.setTextSize(tamanho);
                mViewHolder.r2.setTextSize(tamanho);
                mViewHolder.r3.setTextSize(tamanho);
            }
        });
    }

    private int mudarTamanho(int tamanhoTela, int tamanhoObjeto)
    {
        int tamanho;

        tamanho = tamanhoTela * tamanhoObjeto / 1920;
        System.out.println("Tamanho: "+tamanho);

        return tamanho;
    }

    private void inicializarPerguntas(char dificuldade) // FOI COLOCADA ATE A PERGUNTA 20
    {
        if(dificuldade == 'f')
        {
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

            perguntas.add("Quando foi observado pela primeira vez o Efeito fotovoltaico?");
            curiosidades.add("O efeito fotovoltaico foi observado pela primeira vez em 1839 por Edmond Becquerel que verificou que placas metálicas, de platina ou prata, mergulhadas num eletrólito, produziam uma pequena diferença de potencial quando expostas à luz.");
            resp_correta.add("1839");
            resp_errada.add("1996");
            resp_errada.add("1742");

            perguntas.add("Quando ocorreu a primeira construção da célula solar?");
            curiosidades.add("A história da primeira célula solar começou em Março de 1953 quando Calvin Fuller, um químico dos Bell Laboratories (Bell Labs), em Murray Hill, New Jersey, nos EUA, desenvolveu um processo de difusão para introduzir impurezas em cristais de silício, de modo a controlar as suas propriedades eléctricas.");
            resp_correta.add("1953");
            resp_errada.add("1998");
            resp_errada.add("1945");

            perguntas.add("A primeira demonstração da célula solar foi:");
            curiosidades.add("Nas páginas do New York Times podia ler-se que aquela primeira célula solar “marca o princípio de uma nova era, levando, eventualmente, à realização de um dos mais belos sonhos da humanidade: a colheita de energia solar sem limites, para o bem-estar da civilização”");
            resp_correta.add("Em uma transmissão via rádio");
            resp_errada.add("Para ligar uma televisão");
            resp_errada.add("Para acender uma lâmpada");

            perguntas.add("Qual foi a primeira aplicação das células solares?");
            curiosidades.add("A primeira aplicação das células solares de Chapin, Fuller e Pearson foi realizada em Americus, no estado da Georgia, para alimentar uma rede telefónica local ");
            resp_correta.add("Alimentar um rede telefônica ");
            resp_errada.add("Em uma lanterna");
            resp_errada.add("Em uma máquina de costura");

            perguntas.add("Qual a principal dificuldade reconhecida no passado e presente até os dias atuais em relação a instalação de módulos solares?");
            curiosidades.add("Uma das primeiras dificuldades encontradas e compreendidas foi que o custo das células solares era demasiado elevado, pelo que a sua utilização só podia ser economicamente competitiva em aplicações muito especiais, como, por exemplo, para produzir eletricidade no espaço.");
            resp_correta.add("Custo elevado dos módulos solares");
            resp_errada.add("Necessidade de troca de peças mensalmente");
            resp_errada.add("Falta de profissionais adequados");

            perguntas.add("A primeira oportunidade de uso da energia solar foi:");
            curiosidades.add("Para uso inicial de produção de eletricidade no espaço, os satélites usaram pilhas químicas ou baseadas em isótopos radioativos. As células solares eram consideradas uma curiosidade, e foi com grande relutância que a NASA aceitou incorporá-las");
            resp_correta.add("No espaço");
            resp_errada.add("Nas residências");
            resp_errada.add("Em praças públicas");

            perguntas.add("Quais países mais utilizam energia solar?");
            curiosidades.add("Os países mais desenvolvidos no aproveitamento da energia solar são a Alemanha, a Itália, o Japão, a Espanha e os Estados Unidos. Esses países promoveram programas para incentivar a utilização dos sistemas fotovoltaicos.");
            resp_correta.add("Alemanha, Japão e EUA");
            resp_errada.add("Brasil, Rússia e Japão");
            resp_errada.add("Japão, Espanha e China");

            perguntas.add("Qual o estado brasileiro que possui a maior potência de energia solar instalada?");
            curiosidades.add("");
            resp_correta.add("Minas Gerais");
            resp_errada.add("São Paulo");
            resp_errada.add("Rio de Janeiro");

            perguntas.add("No Brasil o principal uso da energia solar é:");
            curiosidades.add("A energia solar está sendo utilizada no Brasil majoritariamente em residências, como uma auxiliar na redução da conta de luz, seja por meio da energia térmica, aquecendo água, ou com a utilização da energia fotovoltaica, gerando eletricidade.");
            resp_correta.add("Nas residências");
            resp_errada.add("Em postes de luz");
            resp_errada.add("Nas indústrias");

            perguntas.add("O que incentiva as pessoas a instalarem sistemas fotovoltaicos no Brasil?");
            curiosidades.add("Com a redução progressiva dos custos, o aumento do rendimento dos sistemas solares, e a elevação das tarifas das concessionárias de distribuição de energia, a paridade de custo final da energia produzida pelos sistemas fotovoltaicos e das tarifas das concessionárias já é uma realidade, o que incentiva a autoprodução de energia.");
            resp_correta.add("Foco na redução da conta de energia elétrica");
            resp_errada.add("Foco na questão ambiental");
            resp_errada.add("Foco em status social");

            perguntas.add("Qual dessas não é uma vantagem da energia Solar?");
            curiosidades.add("O custo inicial para montar um sistema solar é bastante avultado, devido aos equipamentos.");
            resp_correta.add("Alto custo de instalação");
            resp_errada.add("É renovável e gratuita");
            resp_errada.add("Não ocupam espaço físico");

            perguntas.add("Qual dessas não é uma desvantagem da energia Solar?");
            curiosidades.add("Embora os equipamentos solares exijam um investimento inicial mais avultado, esse investimento é recuperado, graças ao dinheiro economizado nas contas de eletricidade, água e gás.");
            resp_correta.add("Os investimentos de instalação é recuperado com o passar do tempo");
            resp_errada.add("Se não houver sol, não haverá produção de energia");
            resp_errada.add("Necessidade de estar conectado à rede ou possuir armazenamento durante a noite");

        }

        else if(dificuldade == 'm')
        {
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

            perguntas.add("O que acontece no processo de dopagem?");
            curiosidades.add("Calvin Fuller desenvolveu um processo de difusão para introduzir impurezas em cristais de silício, de modo a controlar as suas propriedades elétricas, Chamado de dopagem");
            resp_correta.add("Controle de propriedades químicas do silício");
            resp_errada.add("Mergulha o silício em água");
            resp_errada.add("Construção de uma barra de Silício");

            perguntas.add("A energia solar representa cerca de:");
            curiosidades.add("A energia solar no Brasil representa apenas 1,7% de toda a matriz energética, porém, o número de sistemas fotovoltaicos instalados no território brasileiro tem crescido consideravelmente, principalmente, nas regiões Sul e Sudeste do país.");
            resp_correta.add("1,7% da matriz energética brasileira");
            resp_errada.add("60% da matriz energética");
            resp_errada.add("32% da matriz energética");

            perguntas.add("Qual o principal incentivo de uso da energia solar no Brasil?");
            curiosidades.add("O incentivo chamado de Meetering é possibilidade de se injetar na rede elétrica a energia produzida pelos painéis fotovoltaicos não consumida, convertê-la em créditos para a compensação posterior, quando o consumo supera a produção dos painéis.");
            resp_correta.add("Meetering");
            resp_errada.add("Grid-tie");
            resp_errada.add("Feed-in tariff");

            perguntas.add("Qual sistema tem como principal característica o aquecimento de água?");
            curiosidades.add("São os sistemas mais simples, econômicos e conhecidos de aproveitar o sol, sendo utilizados em casas, hotéis e empresas para o aquecimento de água para chuveiros ou piscinas, aquecimentos de ambientes ou até em processos industriais.");
            resp_correta.add("Sistema Solar Térmico");
            resp_errada.add("Sistema Termo Solar");
            resp_errada.add("Sistema Solar Fotovoltaico");

            perguntas.add("Qual sistema tem como principal característica o uso de células fotovoltaicas?");
            curiosidades.add("Os sistemas fotovoltaicos são capazes de gerar energia elétrica através das chamadas células fotovoltaicas. As células fotovoltaicas são feitas de materiais capazes de converter a radiação solar em energia elétrica, através do chamado “efeito fotovoltaico”. ");
            resp_correta.add("Sistema Solar Fotovoltaico");
            resp_errada.add("Sistema Termo Solar");
            resp_errada.add("Sistema Solar Térmico");

            perguntas.add("Qual sistema tem como principal característica a concentração da radiação solar?");
            curiosidades.add("Os sistemas termo solares produzem inicialmente calor, através de um sistema de espelhos (ou concentradores) que concentram a radiação solar, e só então transformam este calor em energia elétrica.");
            resp_correta.add("Sistema Termo Solar");
            resp_errada.add("Sistema Solar Fotovoltaico");
            resp_errada.add("Sistema Solar Térmico");

            perguntas.add("Quais as duas principais espécies básicas de sistemas fotovoltaícos?");
            curiosidades.add("Sistemas Isolados (Off-grid) e Sistemas Conectados à Rede (On-Grid)");
            resp_correta.add("On-Grid e Off-Grid");
            resp_errada.add("Off-Grid e Oron-Grid");
            resp_errada.add("On-Grid e Oron-Grid");

            perguntas.add("Para qual sistema não é necessário estar conectado a rede elétrica?");
            curiosidades.add("Os Sistemas Isolados (Off-Grid) são utilizados em locais remotos ou onde o custo de se conectar a rede elétrica é elevado, são utilizados em casas de campo, refúgios, iluminação, telecomunicações, bombeio de água, etc.");
            resp_correta.add("Off-Grid");
            resp_errada.add("Oron-Grid");
            resp_errada.add("On-Grid");

            perguntas.add("Para qual sistema é necessário estar conectado a rede elétrica?");
            curiosidades.add("Os Sistemas Conectados à rede (On-Grid), substituem ou complementam a energia elétrica convencional disponível na rede elétrica.");
            resp_correta.add("On-Grid");
            resp_errada.add("Off-Grid");
            resp_errada.add("Oron-Grid");
        }

        else
        {
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


}

