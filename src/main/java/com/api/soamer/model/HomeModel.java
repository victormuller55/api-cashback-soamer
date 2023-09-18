package com.api.soamer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HomeModel {

    @JsonProperty("pontos_usuario")
    Integer pontosUsuario;

    @JsonProperty("valor_pix")
    Integer valorPix;

    public Integer getPontosUsuario() {
        return pontosUsuario;
    }

    public void setPontosUsuario(Integer pontosUsuario) {
        this.pontosUsuario = pontosUsuario;
    }

    public Integer getValorPix() {
        return valorPix;
    }

    public void setValorPix(Integer valorPix) {
        this.valorPix = valorPix;
    }
}
