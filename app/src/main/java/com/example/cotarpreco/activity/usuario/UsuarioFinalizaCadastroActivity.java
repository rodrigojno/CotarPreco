package com.example.cotarpreco.activity.usuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.cotarpreco.R;
import com.example.cotarpreco.databinding.ActivityUsuarioFinalizaCadastroBinding;
import com.example.cotarpreco.model.Login;
import com.example.cotarpreco.model.Usuario;
import com.santalu.maskara.widget.MaskEditText;

public class UsuarioFinalizaCadastroActivity extends AppCompatActivity {

    private ActivityUsuarioFinalizaCadastroBinding binding;

    private Usuario usuario;
    private Login login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsuarioFinalizaCadastroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            usuario = (Usuario) bundle.getSerializable("usuario");
            login = (Login) bundle.getSerializable("login");
        }

    }

    public void validaDados(View view) {
        String nome = binding.edtNome.getText().toString().trim();
        String telefone = binding.edtTelefone.getUnMasked();

        if (!nome.isEmpty()) {
            if (!telefone.isEmpty()) {
                if (binding.edtTelefone.isDone()) {

                    ocultarTeclado();

                    binding.progressBar.setVisibility(View.VISIBLE);

                    finalizaCadastro(nome);

                } else {
                    binding.edtTelefone.requestFocus();
                    binding.edtTelefone.setError("Telefone inválido.");
                }
            } else {
                binding.edtTelefone.requestFocus();
                binding.edtTelefone.setError("Informe seu telefone.");
            }
        } else {
            binding.edtNome.requestFocus();
            binding.edtNome.setError("Informe seu nome.");
        }

    }

    private void finalizaCadastro(String nome) {
        login.setAcesso(true);
        login.salvar();

        usuario.setNome(nome);
        usuario.salvar();

        finish();
        startActivity(new Intent(this, UsuarioHomeActivity.class));
    }

    private void ocultarTeclado() {
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                binding.edtNome.getWindowToken(), 0
        );
    }

}