package com.api.soamer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EditUsuarioModel {
    @JsonProperty("nome")
    String nome;
    @JsonProperty("new_email")
    String newEmail;
    @JsonProperty("new_senha")
    String newSenha;
    @JsonProperty("email")
    String email;
    @JsonProperty("senha")
    String senha;
    @JsonProperty("id_concessionaria")
    Integer idConcessionaria;


    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNewSenha() {
        return newSenha;
    }

    public void setNewSenha(String newSenha) {
        this.newSenha = newSenha;
    }

    public String getNome() {
        return nome;
    }

    public Integer getIdConcessionaria() {
        return idConcessionaria;
    }

    public void setIdConcessionaria(Integer idConcessionaria) {
        this.idConcessionaria = idConcessionaria;
    }
}