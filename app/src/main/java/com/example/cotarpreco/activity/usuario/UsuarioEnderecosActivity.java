package com.example.cotarpreco.activity.usuario;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.cotarpreco.R;

public class UsuarioEnderecosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_enderecos);

        iniciaCompontentes();

        configCliques();

    }

    private void configCliques(){
        findViewById(R.id.ib_voltar).setOnClickListener(v -> finish());
    }

    private void iniciaCompontentes(){
        TextView text_titulo = findViewById(R.id.text_titulo);
        text_titulo.setText("Endereços");
    }

}