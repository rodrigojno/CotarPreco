package com.example.cotarpreco.activity.empresa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.cotarpreco.R;
import com.example.cotarpreco.databinding.ActivityEmpresaEnderecoBinding;
import com.example.cotarpreco.helper.FirebaseHelper;
import com.example.cotarpreco.model.Endereco;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EmpresaEnderecoActivity extends AppCompatActivity {

    private ActivityEmpresaEnderecoBinding binding;

    private Endereco endereco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmpresaEnderecoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configCliques();

        recuperaEndereco();

        binding.include.textToolbar.setText("Meu endereço");
    }

    private void recuperaEndereco(){

        DatabaseReference enderecoRef = FirebaseHelper.getDatabaseReference()
                .child("enderecos")
                .child(FirebaseHelper.getIdFirebase());
        enderecoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot ds : snapshot.getChildren()){
                        endereco = ds.getValue(Endereco.class);
                    }
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

    private void configSalvar(boolean progress){
        if(progress){
            binding.include.progressBar.setVisibility(View.VISIBLE);
            binding.include.ibSalvar.setVisibility(View.GONE);
        }else {
            binding.include.progressBar.setVisibility(View.GONE);
            binding.include.ibSalvar.setVisibility(View.VISIBLE);
        }
    }

    private void configDados(){
        binding.edtLogradouro.setText(endereco.getLogradouro());
        binding.edtNumero.setText(endereco.getNumero());
        binding.edtCep.setText(endereco.getCep());
        binding.edtBairro.setText(endereco.getBairro());
        binding.edtMunicipio.setText(endereco.getMunicipio());

        configSalvar(false);
    }

    private void validaDados(){
        String logradouro = binding.edtLogradouro.getText().toString().trim();
        String numero = binding.edtNumero.getText().toString();
        String cep = binding.edtCep.getUnMasked();
        String bairro = binding.edtBairro.getText().toString().trim();
        String municipio = binding.edtMunicipio.getText().toString().trim();

        if(!logradouro.isEmpty()){
            if(!numero.isEmpty()){
                if(!cep.isEmpty()){
                    if(!bairro.isEmpty()){
                        if(!municipio.isEmpty()){

                            configSalvar(true);

                            if(endereco == null) endereco = new Endereco();
                            endereco.setLogradouro(logradouro);
                            endereco.setNumero(numero);
                            endereco.setCep(cep);
                            endereco.setBairro(bairro);
                            endereco.setMunicipio(municipio);
                            endereco.salvar();

                            configSalvar(false);
                            ocultarTeclado();
                            Toast.makeText(this, "Dados salvos com sucesso!", Toast.LENGTH_SHORT).show();

                        }else {
                            binding.edtMunicipio.requestFocus();
                            binding.edtMunicipio.setError("Informe o município.");
                        }
                    }else {
                        binding.edtBairro.requestFocus();
                        binding.edtBairro.setError("Informe o bairro.");
                    }
                }else {
                    binding.edtCep.requestFocus();
                    binding.edtCep.setError("Informe o CEP");
                }
            }else {
                binding.edtNumero.requestFocus();
                binding.edtNumero.setError("Informe o número.");
            }
        }else {
            binding.edtLogradouro.requestFocus();
            binding.edtLogradouro.setError("Informe o endereço.");
        }

    }

    private void configCliques(){
        binding.include.ibVoltar.setOnClickListener(v -> finish());
        binding.include.ibSalvar.setOnClickListener(v -> validaDados());
    }

    private void ocultarTeclado() {
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                binding.include.ibSalvar.getWindowToken(), 0
        );
    }

}