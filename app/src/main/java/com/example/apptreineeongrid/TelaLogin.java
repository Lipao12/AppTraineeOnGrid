package com.example.apptreineeongrid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TelaLogin extends AppCompatActivity {

    Button b_login;
    Button b_register;
    EditText t_user;
    EditText t_password;
    TextView texto_login;
    View view;
    int larguraTela;
    int alturaTela;
    Typeface fonte;

    RequestQueue queue;

    String token = null;

    // https://appongridtraineer.garcias.dev:8443/user/login OBS.: POST

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_login);

        this.b_login = findViewById(R.id.TLlogin);
        this.b_register = findViewById(R.id.TLregister);
        this.t_password = findViewById(R.id.TLpassword);
        this.t_user = findViewById(R.id.TLuser);
        this.texto_login = findViewById(R.id.TLtextoLogin);
        this.view = findViewById(R.id.TLview);

        this.queue = Volley.newRequestQueue(this);

        mudarFonte();
        mudarPosicao_Tamanho();

        b_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://appongridtraineer.garcias.dev:8443/user/login";
                JSONObject login_data = null;
                try {
                    login_data = new JSONObject(String.format("{\"username\":\"%s\",\"password\":\"%s\"}", TelaLogin.this.t_user.getText(), TelaLogin.this.t_password.getText()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.POST, url, login_data, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                JSONObject success = null;
                                try {
                                    Intent telaInicial = new Intent(TelaLogin.this, TelaIniciar.class);
                                    System.out.println(response.toString());
                                    success = response.getJSONObject("success");
                                    TelaLogin.this.token = success.getString("token");
                                    System.out.println(String.format("Token obtido: %s", TelaLogin.this.token));
                                    telaInicial.putExtra("token", TelaLogin.this.token);
                                    Toast.makeText(getApplicationContext(),"Token de autenticaçao obtido com sucesso.", Toast.LENGTH_SHORT).show();
                                    startActivity(telaInicial);

                                } catch (JSONException e) {
                                    Intent intent = getIntent();
                                    e.printStackTrace();
                                    finish();
                                    Toast.makeText(getApplicationContext(),"Falha na autenticaçao.", Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError e) {
                                // TODO: Handle error
                                Intent intent = getIntent();
                                finish();
                                startActivity(intent);
                            }
                        });

                queue.add(jsonObjectRequest);
            }
        });

        b_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaLogin.this, TelaRegistrar.class);
                startActivity(intent);
            }
        });
    }

    private void mudarFonte()
    {
        fonte = Typeface.createFromAsset(getAssets(),"Press Start K.ttf");
        b_register.setTypeface(fonte);
        b_login.setTypeface(fonte);
        texto_login.setTypeface(fonte);
    }

    private void mudarPosicao_Tamanho()
    {
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                int alturaObjeto;
                int tamanho;

                //Remove o listenner para não ser novamente chamado.
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                larguraTela = view.getWidth();
                alturaTela = view.getHeight();

                alturaObjeto = b_login.getHeight();
                tamanho = mudarTamanho(alturaTela,alturaObjeto);
                b_login.getLayoutParams().height = tamanho;
                b_login.requestLayout();
                b_register.getLayoutParams().height = tamanho;
                b_register.requestLayout();

                alturaObjeto = (int) b_register.getTextSize();
                tamanho = mudarTamanho(alturaTela,alturaObjeto)/3;
                b_register.setTextSize(tamanho);
                b_login.setTextSize(tamanho);
            }
        });
    }

    private int mudarTamanho(int tamanhoTela, int tamanhoObjeto)
    {
        int tamanho;
        tamanho = tamanhoTela * tamanhoObjeto / 1920;
        return tamanho;
    }
}