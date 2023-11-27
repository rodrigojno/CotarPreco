package com.example.cotarpreco.model;

import com.example.cotarpreco.helper.FirebaseHelper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;

public class Cotacao implements Serializable {

    private String id;
    private String idUsuario;
    private String idCategoria;
    private String nome;
    private String marca;
    private Double quantidade;
    private Boolean produtoSimilar = false;
    private String descricao;

    public Cotacao() {
        DatabaseReference cotacaoRef = FirebaseHelper.getDatabaseReference();
        setId(cotacaoRef.push().getKey());
    }

    public void salvar(){
        DatabaseReference cotacaoRef = FirebaseHelper.getDatabaseReference()
                .child("cotacoes")
                .child(FirebaseHelper.getIdFirebase())
                .child(getId());
        cotacaoRef.setValue(this);
    }

    public void remover(){
        DatabaseReference cotacaoRef = FirebaseHelper.getDatabaseReference()
                .child("cotacoes")
                .child(FirebaseHelper.getIdFirebase())
                .child(getId());
        cotacaoRef.removeValue();

        StorageReference storageReference = FirebaseHelper.getStorageReference()
                .child("cotacoes")
                .child(FirebaseHelper.getIdFirebase())
                .child(getId());
        storageReference.delete();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(String idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    @Exclude
    public Boolean getProdutoSimilar() {
        return produtoSimilar;
    }

    public void setProdutoSimilar(Boolean produtoSimilar) {
        this.produtoSimilar = produtoSimilar;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
