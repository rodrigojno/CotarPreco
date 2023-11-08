package com.example.cotarpreco.activity.autenticacao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cotarpreco.R;
import com.example.cotarpreco.databinding.ActivityLoginBinding;
import com.example.cotarpreco.databinding.ActivityRecuperarContaBinding;
import com.example.cotarpreco.helper.FirebaseHelper;

public class RecuperarContaActivity extends AppCompatActivity {

    private ActivityRecuperarContaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecuperarContaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configCliques();

        binding.include.textToolbar.setText("Recuperar senha");
    }

    private void configCliques() {
        binding.include.ibVoltar.setOnClickListener(v -> finish());
    }

    public void recuperarSenha(View view) {
        String email = binding.edtEmail.getText().toString().trim();

        if (!email.isEmpty()) {

            binding.progressBar.setVisibility(View.VISIBLE);

            enviaEmail(email);

        } else {
            binding.edtEmail.requestFocus();
            binding.edtEmail.setError("Informe seu email.");
        }
    }

    private void enviaEmail(String email) {
        FirebaseHelper.getAuth().sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Email enviado com sucesso!", Toast.LENGTH_LONG).show();
            } else {
                String error = task.getException().getMessage();
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
            binding.progressBar.setVisibility(View.GONE);
        });

    }

}