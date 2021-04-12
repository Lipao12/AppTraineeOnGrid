package com.example.apptreineeongrid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TelaRegistrar extends AppCompatActivity {

    Button b_register;
    EditText t_password;
    EditText t_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_registrar);

        this.b_register = findViewById(R.id.TRregister);
        this.t_password = findViewById(R.id.TRpassword);
        this.t_user = findViewById(R.id.TRuser);

        b_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(t_user.getText().toString().equals("") || t_password.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Campo não preenchido", Toast.LENGTH_LONG).show();
                }
                else
                {
                    // mandar senha e usuario

                    Intent home = new Intent(TelaRegistrar.this, TelaLogin.class);
                    home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(home);
                }
            }
        });
    }
}