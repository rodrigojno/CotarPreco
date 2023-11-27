package com.example.cotarpreco.activity.usuario;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

import com.example.cotarpreco.DAO.ItemPedidoDAO;
import com.example.cotarpreco.DAO.UsuarioDAO;
import com.example.cotarpreco.activity.empresa.EmpresaCategoriasActivity;
import com.example.cotarpreco.databinding.ActivityFormCotacaoBinding;
import com.example.cotarpreco.helper.FirebaseHelper;
import com.example.cotarpreco.model.Categoria;
import com.example.cotarpreco.model.Cotacao;
import com.example.cotarpreco.model.ItemPedido;
import com.example.cotarpreco.model.Usuario;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

public class UsuarioFormCotacaoActivity extends AppCompatActivity {

    private ActivityFormCotacaoBinding binding;

    private final int REQUEST_CATEGORIA = 100;
    private final int REQUEST_PEDIDO = 200;

    private Categoria categoriaSelecionada = null;

    private Cotacao cotacao;
    private Usuario usuario;
    private Boolean novaCotacao = true;

    private UsuarioDAO usuarioDAO;
    private ItemPedidoDAO itemPedidoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFormCotacaoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        usuarioDAO = new UsuarioDAO(getBaseContext());
        itemPedidoDAO = new ItemPedidoDAO(getBaseContext());

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            cotacao = (Cotacao) bundle.getSerializable("cotacaoSelecionada");

            configDados();
        }

        binding.include.textToolbar.setText("Nova cotação");

        configCliques();

    }

    private void addPedido(){
        if(usuarioDAO.getUsuario() != null){
            if(cotacao.getIdUsuario().equals(usuarioDAO.getUsuario().getId())){
                salvarCotacao();
            }else {
                Snackbar.make(binding.btnNovaCotacao, "Usuário diferente", Snackbar.LENGTH_LONG).show();
            }
        }else {
            salvarCotacao();
        }
    }

    private void salvarCotacao(){
        ItemPedido itemPedido = new ItemPedido();
        int quantidade = 1;
        itemPedido.setIdItem(cotacao.getId());
        itemPedido.setItem(cotacao.getNome());
        itemPedido.setQuantidade(quantidade);
        itemPedido.setDescricao(cotacao.getDescricao());

        itemPedidoDAO.salvar(itemPedido);

        if(usuarioDAO.getUsuario() == null) usuarioDAO.salvar(usuario);

        Intent intent = new Intent(this, UsuarioPedidoActivity.class);
        startActivityForResult(intent, REQUEST_PEDIDO);

    }

    private void configDados(){
        binding.edtNomeProduto.setText(cotacao.getNome());
        binding.edtMarca.setText(cotacao.getNome());
        binding.edtQuantidade.setText(String.valueOf(cotacao.getQuantidade()));
        binding.edtDescricao.setText(cotacao.getDescricao());

        recuperaCategoria();

        //novaCotacao = false;
        binding.include.textToolbar.setText("Edição");
    }

    private void recuperaCategoria(){
        DatabaseReference categoriasRef = FirebaseHelper.getDatabaseReference()
                .child("categorias")
                .child(FirebaseHelper.getIdFirebase())
                .child(cotacao.getIdCategoria());
        categoriasRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Categoria categoria = snapshot.getValue(Categoria.class);
                if(categoria != null){
                    binding.btnCategoria.setText(categoria.getNome());
                    categoriaSelecionada = categoria;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void configCliques() {
        binding.include.ibVoltar.setOnClickListener(v -> finish());

        binding.btnCategoria.setOnClickListener(v -> {
            Intent intent = new Intent(this, UsuarioCategoriaCotacaoActivity.class);
            intent.putExtra("acesso", 1);
            startActivityForResult(intent, REQUEST_CATEGORIA);
        });

        binding.edtDescricao.setOnClickListener(v -> {
            mostrarTelclado();
            binding.edtDescricao.requestFocus();
        });

    }

    public void validaDados(View view){
        String nomeProduto = binding.edtNomeProduto.getText().toString().trim();
        String marca = binding.edtMarca.getText().toString().trim();
        String quantidade = binding.edtQuantidade.getText().toString().trim();
        String descricao = binding.edtDescricao.getText().toString().trim();

        //if(categoriaSelecionada != null){
            if(!nomeProduto.isEmpty()){
                if(!marca.isEmpty()){
                    if(!quantidade.isEmpty()){
                        if(!descricao.isEmpty()){

                            ocultarTeclado();

                            if(cotacao == null) cotacao = new Cotacao();
                            cotacao.setIdUsuario(FirebaseHelper.getIdFirebase());
                            cotacao.setIdCategoria(categoriaSelecionada.getId());
                            cotacao.setNome(nomeProduto);
                            cotacao.setMarca(marca);
                            cotacao.setQuantidade(Double.valueOf(quantidade));
                            cotacao.setDescricao(descricao);

                            if(novaCotacao){
                                ocultarTeclado();
                                cotacao.salvar();
                            }

                        }else {
                            binding.edtDescricao.requestFocus();
                            binding.edtDescricao.setError("Informe uma descrição.");
                        }
                    }else {
                        binding.edtQuantidade.requestFocus();
                        binding.edtQuantidade.setError("Informe a quantidade do produto.");
                    }
                }else{
                    binding.edtMarca.requestFocus();
                    binding.edtMarca.setError("Informe a marca e modelo do produto.");
                }
            }else {
                binding.edtNomeProduto.requestFocus();
                binding.edtNomeProduto.setError("Informe o tipo de produto.");
            }
        //}else{
        //    ocultarTeclado();
        //    erroSalvarCotacao("Selecione uma categoria para o produto.");
        }

    //}

    private void erroSalvarCotacao(String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Atenção");
        builder.setMessage(msg);
        builder.setPositiveButton("OK", ((dialog, which) -> {
            dialog.dismiss();
        }));

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void mostrarTelclado(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    private void ocultarTeclado(){
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                binding.edtNomeProduto.getWindowToken(), 0
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_CATEGORIA){
                categoriaSelecionada = (Categoria) data.getSerializableExtra("categoriaSelecionada");
                binding.btnCategoria.setText(categoriaSelecionada.getNome());
            }
            if(requestCode == REQUEST_PEDIDO){
                finish();
            }
        }
    }

}