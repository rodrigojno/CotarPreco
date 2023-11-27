package com.example.cotarpreco.activity.autenticacao;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cotarpreco.R;
import com.example.cotarpreco.activity.MainActivity;
import com.example.cotarpreco.activity.empresa.EmpresaFinalizaCadastroActivity;
import com.example.cotarpreco.activity.empresa.EmpresaHomeActivity;
import com.example.cotarpreco.activity.usuario.UsuarioFinalizaCadastroActivity;
import com.example.cotarpreco.activity.usuario.UsuarioHomeActivity;
import com.example.cotarpreco.databinding.ActivityLoginBinding;
import com.example.cotarpreco.helper.FirebaseHelper;
import com.example.cotarpreco.model.Empresa;
import com.example.cotarpreco.model.Login;
import com.example.cotarpreco.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    private Login login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configCliques();

        binding.include.textToolbar.setText("Login");


    }

    private void configCliques() {
        binding.include.ibVoltar.setOnClickListener(v -> finish());
        binding.textCriarConta.setOnClickListener(v ->
                startActivity(new Intent(this, CriarContaActivity.class)));

        binding.textRecuperarConta.setOnClickListener(v ->
                startActivity(new Intent(this, RecuperarContaActivity.class)));

    }

    public void validaDados(View view) {
        String email = binding.edtEmail.getText().toString();
        String senha = binding.edtSenha.getText().toString();

        if (!email.isEmpty()) {
            if (!senha.isEmpty()) {

                ocultarTeclado();

                binding.progressBar.setVisibility(View.VISIBLE);

                logar(email, senha);

            } else {
                binding.edtSenha.requestFocus();
                binding.edtSenha.setError("Informe sua senha.");
            }
        } else {
            binding.edtEmail.requestFocus();
            binding.edtEmail.setError("Informe seu email.");
        }
    }

    private void logar(String email, String senha) {
        FirebaseHelper.getAuth().signInWithEmailAndPassword(
                email, senha
        ).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                verificaCadastro(task.getResult().getUser().getUid());
            } else {
                binding.progressBar.setVisibility(View.GONE);
                erroAutenticacao(FirebaseHelper.validaErros(task.getException().getMessage()));
            }
        });
    }

    private void verificaCadastro(String idUser) {
        DatabaseReference loginRef = FirebaseHelper.getDatabaseReference()
                .child("login")
                .child(idUser);
        loginRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                login = snapshot.getValue(Login.class);
                verificarAcesso(login);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void verificarAcesso(Login login) {
        if (login != null) {
            if (login.getTipo().equals("U")) {
                if (login.getAcesso()) {
                    finish();
                    startActivity(new Intent(getBaseContext(), UsuarioHomeActivity.class));
                } else {
                    recuperaUsuario();
                }
            } else {
                if (login.getAcesso()) {
                    finish();
                    startActivity(new Intent(getBaseContext(), EmpresaHomeActivity.class));
                } else {
                    recuperaEmpresa();
                }
            }
        }
    }

    private void recuperaUsuario() {
        DatabaseReference usuarioRef = FirebaseHelper.getDatabaseReference()
                .child("usuarios")
                .child(login.getId());
        usuarioRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario usuario = snapshot.getValue(Usuario.class);
                if (usuario != null) {
                    finish();
                    Intent intent = new Intent(getBaseContext(), UsuarioFinalizaCadastroActivity.class);
                    intent.putExtra("login", login);
                    intent.putExtra("usuario", usuario);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void recuperaEmpresa() {
        DatabaseReference empresaRef = FirebaseHelper.getDatabaseReference()
                .child("empresas")
                .child(login.getId());
        empresaRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Empresa empresa = snapshot.getValue(Empresa.class);
                if (empresa != null) {
                    finish();
                    Intent intent = new Intent(getBaseContext(), EmpresaFinalizaCadastroActivity.class);
                    intent.putExtra("login", login);
                    intent.putExtra("empresa", empresa);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void erroAutenticacao(String msg) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Atenção");
        builder.setMessage(msg);
        builder.setPositiveButton("OK", ((dialog, which) -> {
            dialog.dismiss();
        }));

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void ocultarTeclado() {
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                binding.edtEmail.getWindowToken(), 0
        );
    }

}