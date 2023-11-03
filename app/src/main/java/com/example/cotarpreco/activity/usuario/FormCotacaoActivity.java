package com.example.cotarpreco.activity.usuario;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.example.cotarpreco.R;
import com.example.cotarpreco.activity.empresa.EmpresaCategoriaActivity;
import com.example.cotarpreco.databinding.ActivityFormCotacaoBinding;
import com.example.cotarpreco.model.Categoria;

public class FormCotacaoActivity extends AppCompatActivity {

    private ActivityFormCotacaoBinding binding;

    private final int REQUEST_CATEGORIA = 100;

    private String categoriaSelecinada = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFormCotacaoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.include.textTitulo.setText("Nova cotação");

        configCliques();

    }

    private void configCliques(){
        binding.include.ibVoltar.setOnClickListener(v -> finish());

    }

    public void selecionarCategoria(View view){
        Intent intent = new Intent(this, EmpresaCategoriaActivity.class);
        startActivityForResult(intent, REQUEST_CATEGORIA);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_CATEGORIA){
                Categoria categoria = (Categoria) data.getSerializableExtra("categoriaSelecionada");
                categoriaSelecinada = categoria.getNome();
                binding.btnCategoria.setText(categoriaSelecinada);
            }else if(true){// Camera

            }else { //Galeria

            }

        }
    }
}