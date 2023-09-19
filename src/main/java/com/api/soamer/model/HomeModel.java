package com.api.soamer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HomeModel {

    @JsonProperty("pontos_usuario")
    Integer pontosUsuario;

    @JsonProperty("pontos_pedentes_usuario")
    Integer pontosPendentesUsuario;
    @JsonProperty("valor_pix")
    Integer valorPix;

    public Integer getPontosUsuario() {
        return pontosUsuario;
    }

    public void setPontosUsuario(Integer pontosUsuario) {
        this.pontosUsuario = pontosUsuario;
    }

    public Integer getPontosPendentesUsuario() {
        return pontosPendentesUsuario;
    }

    public void setPontosPendentesUsuario(Integer pontosPendentesUsuario) {
        this.pontosPendentesUsuario = pontosPendentesUsuario;
    }

    public Integer getValorPix() {
        return valorPix;
    }

    public void setValorPix(Integer valorPix) {
        this.valorPix = valorPix;
    }
}
