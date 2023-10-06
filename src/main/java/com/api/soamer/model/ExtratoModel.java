package com.api.soamer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tbl_extrato")
public class ExtratoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id_extrato")
    private Integer idExtrato;
    @JsonProperty("titulo_extrato")
    private String tituloExtrato;
    @JsonProperty("descricao_extrato")
    private String descricaoExtrato;
    @JsonProperty("pontos_extrato")
    private Integer pontosExtrato;
    @JsonProperty("data_extrato")
    private Date dataExtrato;
    @JsonProperty("entrada_extrato")
    private Boolean entradaExtrato;
    @JsonProperty("id_usuario_extrato")
    private Integer idUsuario;
    @JsonProperty("id_vaucher_extrato")
    private Integer idVaucher;

    public Integer getIdExtrato() {
        return idExtrato;
    }

    public void setIdExtrato(Integer idExtrato) {
        this.idExtrato = idExtrato;
    }

    public String getTituloExtrato() {
        return tituloExtrato;
    }

    public void setTituloExtrato(String tituloExtrato) {
        this.tituloExtrato = tituloExtrato;
    }

    public String getDescricaoExtrato() {
        return descricaoExtrato;
    }

    public void setDescricaoExtrato(String descricaoExtrato) {
        this.descricaoExtrato = descricaoExtrato;
    }

    public Integer getPontosExtrato() {
        return pontosExtrato;
    }

    public void setPontosExtrato(Integer pontosExtrato) {
        this.pontosExtrato = pontosExtrato;
    }

    public Date getDataExtrato() {
        return dataExtrato;
    }

    public void setDataExtrato(Date dataExtrato) {
        this.dataExtrato = dataExtrato;
    }

    public Boolean getEntradaExtrato() {
        return entradaExtrato;
    }

    public void setEntradaExtrato(Boolean entradaExtrato) {
        this.entradaExtrato = entradaExtrato;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdVaucher() {
        return idVaucher;
    }

    public void setIdVaucher(Integer idVaucher) {
        this.idVaucher = idVaucher;
    }
}
