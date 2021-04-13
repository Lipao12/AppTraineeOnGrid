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

import org.json.JSONException;
import org.json.JSONObject;

public class TelaRegistrar extends AppCompatActivity {

    Button b_register;
    EditText t_password;
    EditText t_user;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_registrar);

        this.b_register = findViewById(R.id.TRregister);
        this.t_password = findViewById(R.id.TRpassword);
        this.t_user = findViewById(R.id.TRuser);

        this.queue = Volley.newRequestQueue(this);

        b_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(t_user.getText().toString().equals("") || t_password.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Campo não preenchido", Toast.LENGTH_LONG).show();
                    return;
                }
                String url = "https://appongridtraineer.garcias.dev:8443/user/register";
                JSONObject reg_data = null;
                try {
                    reg_data = new JSONObject(String.format("{\"username\":\"%s\",\"password\":\"%s\"}", TelaRegistrar.this.t_user.getText(), TelaRegistrar.this.t_password.getText()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.POST, url, reg_data, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                JSONObject success = null;
                                System.out.println(response.toString());
                                try {
                                    Intent telaLogin = new Intent(TelaRegistrar.this, TelaLogin.class);
                                    success = response.getJSONObject("success");
                                    Toast.makeText(getApplicationContext(),"Usuario registrado com sucesso.", Toast.LENGTH_SHORT).show();
                                    startActivity(telaLogin);

                                } catch (JSONException e) {
                                    Intent intent = getIntent();
                                    finish();
                                    e.printStackTrace();
                                    Toast.makeText(getApplicationContext(),"Falha ao registrar usuario.", Toast.LENGTH_SHORT).show();
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

                TelaRegistrar.this.queue.add(jsonObjectRequest);
            }
        });
    }
}