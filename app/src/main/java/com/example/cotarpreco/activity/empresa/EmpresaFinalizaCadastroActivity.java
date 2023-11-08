package com.example.cotarpreco.activity.empresa;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cotarpreco.R;
import com.example.cotarpreco.activity.usuario.UsuarioHomeActivity;
import com.example.cotarpreco.databinding.ActivityEmpresaFinalizaCadastroBinding;
import com.example.cotarpreco.helper.FirebaseHelper;
import com.example.cotarpreco.model.Categoria;
import com.example.cotarpreco.model.Empresa;
import com.example.cotarpreco.model.Login;
import com.example.cotarpreco.model.Usuario;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.santalu.maskara.widget.MaskEditText;

import java.io.IOException;
import java.util.List;

public class EmpresaFinalizaCadastroActivity extends AppCompatActivity {

    private ActivityEmpresaFinalizaCadastroBinding binding;

    private final int REQUEST_CATEGORIA = 100;

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