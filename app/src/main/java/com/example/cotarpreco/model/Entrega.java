package com.example.cotarpreco.model;

public class Entrega {
    private Boolean status = false;
    private String descricao;
    private Double taxa;

    public Entrega(Boolean status) {
    }

    public void salvar(){

    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getTaxa() {
        return taxa;
    }

    public void setTaxa(Double taxa) {
        this.taxa = taxa;
    }
}
