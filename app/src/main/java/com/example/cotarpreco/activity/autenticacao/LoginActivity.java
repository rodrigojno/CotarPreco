package com.example.cotarpreco.activity.autenticacao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cotarpreco.R;
import com.example.cotarpreco.activity.MainActivity;
import com.example.cotarpreco.helper.FirebaseHelper;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        configCliques();

        iniciaComponentes();

    }

    private void configCliques(){
        findViewById(R.id.text_criar_conta).setOnClickListener(v ->
                startActivity(new Intent(this, CriarContaActivity.class)));
    }

    private void iniciaComponentes() {
        TextView text_titulo = findViewById(R.id.text_titulo);
        text_titulo.setText("Login");
    }

}