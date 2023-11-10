package com.example.cotarpreco.activity.empresa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.cotarpreco.databinding.ActivityEmpresaConfigBinding;
import com.example.cotarpreco.helper.FirebaseHelper;
import com.example.cotarpreco.model.Empresa;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class EmpresaConfigActivity extends AppCompatActivity {

    private ActivityEmpresaConfigBinding binding;

    private Empresa empresa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmpresaConfigBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recuperaEmpresa();

        configCliques();

        binding.include.textToolbar.setText("Dados da empresa");

    }

    private void configCliques(){
        binding.include.ibVoltar.setOnClickListener(v -> finish());
        binding.include.ibSalvar.setOnClickListener(v -> validaDados());
    }

    private void recuperaEmpresa(){
        DatabaseReference empresaRef = FirebaseHelper.getDatabaseReference()
                .child("empresas")
                .child(FirebaseHelper.getIdFirebase());
        empresaRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    empresa = snapshot.getValue(Empresa.class);
                    configDados();
                }else {
                    configSalvar(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void configDados(){
        binding.edtNome.setText(empresa.getNome());
        binding.edtTelefone.setText(empresa.getTelefone());
        binding.edtCategoria.setText(empresa.getCategoria());

        configSalvar(false);
    }

    private void configSalvar(boolean progress){
        if(progress){
            binding.include.progressBar.setVisibility(View.VISIBLE);
            binding.include.ibSalvar.setVisibility(View.GONE);
        }else {
            binding.include.progressBar.setVisibility(View.GONE);
            binding.include.ibSalvar.setVisibility(View.VISIBLE);
        }
    }

    private void validaDados(){

        String nome = binding.edtNome.getText().toString().trim();
        String telefone = binding.edtTelefone.getUnMasked();
        String categoria = binding.edtCategoria.getText().toString().trim();

        if(!nome.isEmpty()){
            if(binding.edtTelefone.isDone()){
                if(!categoria.isEmpty()){

                    ocultarTeclado();

                    configSalvar(true);

                    empresa.setNome(nome);
                    empresa.setTelefone(telefone);
                    empresa.setCategoria(categoria);
                    empresa.salvar();

                    configSalvar(false);
                    Toast.makeText(this, "Dados salvos com sucesso!", Toast.LENGTH_SHORT).show();

                }else {
                    binding.edtCategoria.requestFocus();
                    binding.edtCategoria.setError("Informe uma categoria para seu cadastro.");
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

    private void ocultarTeclado(){
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                binding.edtNome.getWindowToken(), 0
        );
    }

}