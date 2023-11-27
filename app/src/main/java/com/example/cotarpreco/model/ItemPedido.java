package com.example.cotarpreco.model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class ItemPedido implements Serializable {

    private Long id;
    private String idItem;
    private String categoria;
    private String nome;
    private String marca;
    private String item;
    private int quantidade;
    private boolean produtoSimilar;
    private String descricao;

    public ItemPedido() {
    }

    @Exclude
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdItem() {
        return idItem;
    }

    public void setIdItem(String idItem) {
        this.idItem = idItem;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTipoProduto() {
        return nome;
    }

    public void setTipoProduto(String tipoProduto) {
        this.nome = tipoProduto;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marcaModelo) {
        this.marca = marca;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public boolean isProdutoSimilar() {
        return produtoSimilar;
    }

    public void setProdutoSimilar(boolean produtoSimilar) {
        this.produtoSimilar = produtoSimilar;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
