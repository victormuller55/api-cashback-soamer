package com.api.soamer.model.venda;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "tbl_venda")
public class RegistrarVendaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id_venda")
    private Integer idVenda;

    @JsonProperty("id_usuario")
    private Integer idUsuario;

    @JsonProperty("nome_usuario")
    private String nomeUsuario;

    @JsonProperty("venda_nfe_code")
    private String NFECode;

    @JsonProperty("id_ponteira")
    private Integer idPonteira;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    @JsonProperty("data_envio")
    private Date dateEnvio = new Date();

    @JsonProperty("venda_aprovado")
    private Integer aprovado = 0;

    public RegistrarVendaModel() {}

    public Integer getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(Integer idVenda) {
        this.idVenda = idVenda;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getNFECode() {
        return NFECode;
    }

    public void setNFECode(String NFECode) {
        this.NFECode = NFECode;
    }

    public Integer getIdPonteira() {
        return idPonteira;
    }

    public void setIdPonteira(Integer idPonteira) {
        this.idPonteira = idPonteira;
    }

    public Date getDateEnvio() {
        return dateEnvio;
    }

    public void setDateEnvio(Date dateEnvio) {
        this.dateEnvio = dateEnvio;
    }

    public Integer isAprovado() {
        return aprovado;
    }


    public void setAprovado(Integer aprovado) {
        this.aprovado = aprovado;
    }
}
