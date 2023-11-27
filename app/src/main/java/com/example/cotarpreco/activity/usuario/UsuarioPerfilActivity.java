package com.example.cotarpreco.activity.usuario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cotarpreco.R;
import com.example.cotarpreco.databinding.ActivityUsuarioPerfilBinding;
import com.example.cotarpreco.helper.FirebaseHelper;
import com.example.cotarpreco.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class UsuarioPerfilActivity extends AppCompatActivity {

    private ActivityUsuarioPerfilBinding binding;

    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsuarioPerfilBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configCliques();

        recuperaUsuario();

        binding.include.textToolbar.setText("Perfil");

    }

    private void configCliques() {
        binding.include.ibVoltar.setOnClickListener(v -> finish());
        binding.include.ibSalvar.setOnClickListener(v -> atualizarDados());
    }

    private void recuperaUsuario(){
        DatabaseReference usuarioRef = FirebaseHelper.getDatabaseReference()
                .child("usuarios")
                .child(FirebaseHelper.getIdFirebase());
        usuarioRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    usuario = snapshot.getValue(Usuario.class);

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
        binding.edtNome.setText(usuario.getNome());
        binding.edtTelefone.setText(usuario.getTelefone());
        binding.edtEmail.setText(usuario.getEmail());

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

    private void atualizarDados(){
        String nome = binding.edtNome.getText().toString().trim();
        String telefone = binding.edtTelefone.getUnMasked();

        if(!nome.isEmpty()){
            if(binding.edtTelefone.isDone()){

                ocultarTeclado();

                configSalvar(true);

                if(usuario == null) usuario = new Usuario();
                usuario.setNome(nome);
                usuario.setTelefone(telefone);
                usuario.salvar();

                configSalvar(false);
                Toast.makeText(this, "Dados salvos com sucesso!", Toast.LENGTH_SHORT).show();

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