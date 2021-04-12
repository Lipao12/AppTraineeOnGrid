package com.example.apptreineeongrid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TelaLogin extends AppCompatActivity {

    Button b_login;
    Button b_register;
    EditText t_user;
    EditText t_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_login);

        this.b_login = findViewById(R.id.TLlogin);
        this.b_register = findViewById(R.id.TLregister);
        this.t_password = findViewById(R.id.TLpassword);
        this.t_user = findViewById(R.id.TLuser);

        b_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(t_user.getText().toString().equals("") || t_password.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Campo não preenchido", Toast.LENGTH_LONG).show();
                }
                else
                {
                    String user = "a"; // pegar nome do
                    String password = "as"; // pegar nome do

                    System.out.println("User: "+t_user.getText().toString());

                    if(t_user.getText().toString().equals(user) && t_password.getText().toString().equals(password))
                    {
                        Intent intent = new Intent(TelaLogin.this, TelaDificuldade.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Login e/ou senha incorretos", Toast.LENGTH_LONG).show();
                    }
                }
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