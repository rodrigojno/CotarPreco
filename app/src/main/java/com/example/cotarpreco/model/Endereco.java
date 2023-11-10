package com.example.cotarpreco.model;

import com.example.cotarpreco.helper.FirebaseHelper;
import com.google.firebase.database.DatabaseReference;

public class Endereco {
    private String logradouro;
    private String bairro;
    private String municipio;

    public Endereco() {
    }

    public void salvar(){
        DatabaseReference enderecoRef = FirebaseHelper.getDatabaseReference()
                .child("endereços")
                .child(FirebaseHelper.getIdFirebase());
        enderecoRef.setValue(this);
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }
}
