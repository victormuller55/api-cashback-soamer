package com.api.soamer.model;

public class ErrorModel {
    private String tipo;
    private String mensagem;
    private String erro;

    public ErrorModel(String mensagem, Exception erro, String tipo) {
        this.tipo = tipo;
        this.mensagem = mensagem;
        this.erro = erro.getMessage();
    }


    public ErrorModel(String mensagem, String tipo) {
        this.tipo = tipo;
        this.mensagem = mensagem;
        this.erro = "";
    }

    public ErrorModel(String mensagem) {
        this.tipo = "";
        this.mensagem = mensagem;
        this.erro = "";
    }

    public ErrorModel() {
        this.tipo = "Desconhecido";
        this.mensagem = "Ops ... Ocorreu algum erro desconhecido";
        this.erro = "";
    }

    public ErrorModel(Exception erro) {
        this.tipo = "Desconhecido";
        this.mensagem = "Ops ... Ocorreu algum erro desconhecido";
        this.erro = erro.getMessage();
    }


    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }
}

