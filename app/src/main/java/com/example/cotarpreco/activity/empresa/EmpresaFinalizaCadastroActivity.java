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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cotarpreco.R;
import com.example.cotarpreco.activity.usuario.UsuarioHomeActivity;
import com.example.cotarpreco.helper.FirebaseHelper;
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




    private EditText edt_nome;
    private MaskEditText edt_telefone;
    private EditText edt_categoria;
    private ProgressBar progressBar;



    private Empresa empresa;
    private Login login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresa_finaliza_cadastro);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            empresa = (Empresa) bundle.getSerializable("empresa");
            login = (Login) bundle.getSerializable("login");
        }

        iniciaComponentes();

    }



    public void validaDados(View view){

        String nome = edt_nome.getText().toString().trim();
        String telefone = edt_telefone.getUnMasked();
        String categoria = edt_categoria.getText().toString().trim();


            if(!nome.isEmpty()){
                if(edt_telefone.isDone()){
                    if(!categoria.isEmpty()){

                        ocultarTeclado();

                        progressBar.setVisibility(View.VISIBLE);

                        finalizaCadastro(nome, telefone, categoria);

                    }else {
                        edt_categoria.requestFocus();
                        edt_categoria.setError("Informe uma categoria para finalizar seu cadastro.");
                    }
                }else {
                    edt_telefone.requestFocus();
                    edt_telefone.setError("Informe um telefone para contato.");
                }
            }else {
                edt_nome.requestFocus();
                edt_nome.setError("Informe um nome para seu cadastro.");
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

    private void iniciaComponentes(){

        edt_nome = findViewById(R.id.edt_nome);
        edt_telefone = findViewById(R.id.edt_telefone);
        edt_categoria = findViewById(R.id.edt_categoria);
        progressBar = findViewById(R.id.progressBar);
    }


    private void ocultarTeclado(){
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                edt_nome.getWindowToken(), 0
        );
    }

}