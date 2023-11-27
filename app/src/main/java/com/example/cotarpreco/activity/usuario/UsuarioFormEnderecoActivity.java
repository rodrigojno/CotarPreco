package com.example.cotarpreco.activity.usuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.cotarpreco.R;
import com.example.cotarpreco.databinding.ActivityUsuarioFormEnderecoBinding;
import com.example.cotarpreco.model.Endereco;

public class UsuarioFormEnderecoActivity extends AppCompatActivity {

    private ActivityUsuarioFormEnderecoBinding binding;

    private Endereco endereco;
    private Boolean novoEndereco = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsuarioFormEnderecoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configSalvar(false);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            endereco = (Endereco) bundle.getSerializable("enderecoSelecionado");
            atualizarDados();
        }

        configCliques();

        binding.include.textToolbar.setText("Endereços");
    }

    private void configCliques() {
        binding.include.ibVoltar.setOnClickListener(v -> finish());
        binding.include.ibSalvar.setOnClickListener(v -> incluirEnderecoUsuario());
    }

    private void configSalvar(boolean progress) {
        if (progress) {
            binding.include.progressBar.setVisibility(View.VISIBLE);
            binding.include.ibSalvar.setVisibility(View.GONE);
        } else {
            binding.include.progressBar.setVisibility(View.GONE);
            binding.include.ibSalvar.setVisibility(View.VISIBLE);
        }
    }

    private void incluirEnderecoUsuario() {
        String logradouro = binding.edtLogradouro.getText().toString().trim();
        String referencia = binding.edtReferencia.getText().toString().trim();
        String cep = binding.edtCep.getUnMasked();
        String bairro = binding.edtBairro.getText().toString().trim();
        String municipio = binding.edtMunicipio.getText().toString().trim();

        if (!logradouro.isEmpty()) {
            if (!referencia.isEmpty()) {

                if (!bairro.isEmpty()) {
                    if (!municipio.isEmpty()) {
                        if (!cep.isEmpty()) {

                            configSalvar(true);

                            if (endereco == null) endereco = new Endereco();
                            endereco.setLogradouro(logradouro);
                            endereco.setReferencia(referencia);
                            endereco.setCep(cep);
                            endereco.setBairro(bairro);
                            endereco.setMunicipio(municipio);
                            endereco.salvar();

                            if (novoEndereco) {
                                finish();
                            } else {
                                ocultarTeclado();
                                Toast.makeText(this, "Dados salvos com sucesso!", Toast.LENGTH_SHORT).show();
                            }

                            configSalvar(false);

                        } else {
                            binding.edtCep.requestFocus();
                            binding.edtCep.setError("Informe o CEP");
                        }
                    } else {
                        binding.edtMunicipio.requestFocus();
                        binding.edtMunicipio.setError("Informe o município.");
                    }
                } else {
                    binding.edtBairro.requestFocus();
                    binding.edtBairro.setError("Informe o bairro.");
                }
            } else {
                binding.edtReferencia.requestFocus();
                binding.edtReferencia.setError("Informe uma referência.");
            }
        } else {
            binding.edtLogradouro.requestFocus();
            binding.edtLogradouro.setError("Informe o endereço.");
        }

    }

    private void atualizarDados() {
        binding.edtLogradouro.setText(endereco.getLogradouro());
        binding.edtReferencia.setText(endereco.getReferencia());
        binding.edtCep.setText(endereco.getCep());
        binding.edtBairro.setText(endereco.getBairro());
        binding.edtMunicipio.setText(endereco.getMunicipio());

        novoEndereco = false;
        binding.include.textToolbar.setText("Edição");
    }

    private void ocultarTeclado() {
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                binding.include.ibSalvar.getWindowToken(), 0
        );
    }

}