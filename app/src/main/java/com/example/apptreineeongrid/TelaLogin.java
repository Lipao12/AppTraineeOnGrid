package com.example.apptreineeongrid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

        this.queue = Volley.newRequestQueue(this);

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
                System.out.println(login_data.toString());
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.POST, url, login_data, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                JSONObject success = null;
                                try {
                                    Intent telaInicial = new Intent(TelaLogin.this, TelaIniciar.class);
                                    success = response.getJSONObject("success");
                                    TelaLogin.this.token = success.getString("token");
                                    System.out.println(String.format("Token obtido: %s", TelaLogin.this.token));
                                    telaInicial.putExtra("token", TelaLogin.this.token);
                                    Toast.makeText(getApplicationContext(),"Token de autenticaçao obtido com sucesso.", Toast.LENGTH_SHORT).show();
                                    startActivity(telaInicial);

                                } catch (JSONException e) {
                                    Intent intent = getIntent();
                                    finish();
                                    Toast.makeText(getApplicationContext(),"Falha na autenticaçao.", Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                }
                                Toast.makeText(getApplicationContext(),"Token adiquirido.", Toast.LENGTH_SHORT).show();

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
}