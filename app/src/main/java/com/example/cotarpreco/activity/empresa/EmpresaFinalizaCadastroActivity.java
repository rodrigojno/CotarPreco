package com.example.cotarpreco.activity.empresa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.cotarpreco.databinding.ActivityEmpresaFinalizaCadastroBinding;
import com.example.cotarpreco.model.Empresa;
import com.example.cotarpreco.model.Login;

public class EmpresaFinalizaCadastroActivity extends AppCompatActivity {

    private ActivityEmpresaFinalizaCadastroBinding binding;

    private Empresa empresa;
    private Login login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmpresaFinalizaCadastroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            empresa = (Empresa) bundle.getSerializable("empresa");
            login = (Login) bundle.getSerializable("login");
        }

    }

    public void validaDados(View view){

        String nome = binding.edtNome.getText().toString().trim();
        String telefone = binding.edtTelefone.getUnMasked();
        String categoria = binding.edtCategoria.getText().toString().trim();

            if(!nome.isEmpty()){
                if(binding.edtTelefone.isDone()){
                    if(!categoria.isEmpty()){

                        ocultarTeclado();

                        binding.progressBar.setVisibility(View.VISIBLE);

                        finalizaCadastro(nome, telefone, categoria);

                    }else {
                        binding.edtCategoria.requestFocus();
                        binding.edtCategoria.setError("Informe a principal categoria da empresa para seu cadastro.");
                    }
                }else {
                    binding.edtTelefone.requestFocus();
                    binding.edtTelefone.setError("Informe um telefone para contato.");
                }
            }else {
                binding.edtNome.requestFocus();
                binding.edtNome.setError("Informe um nome para seu cadastro.");
            }
    }

    private void finalizaCadastro(String nome, String telefone, String categoria){
        login.setAcesso(true);
        login.salvar();

        empresa.setNome(nome);
        empresa.setTelefone(telefone);
        empresa.setCategoria(categoria);
        empresa.salvar();

        finish();
        startActivity(new Intent(this, EmpresaHomeActivity.class));
    }

    private void ocultarTeclado(){
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                binding.edtNome.getWindowToken(), 0
        );
    }

}