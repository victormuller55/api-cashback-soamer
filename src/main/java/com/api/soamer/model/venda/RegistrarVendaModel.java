package com.api.soamer.model.venda;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "tbl_venda")
public class RegistrarVendaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id_venda")
    private Integer idVenda;
    @JsonProperty("id_usuario")
    private Integer idUsuario;
    @JsonProperty("venda_nfe_code")
    private String NFECode;
    @JsonProperty("venda_aprovado")
    private boolean aprovado = true;

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

    public String getNFECode() {
        return NFECode;
    }

    public void setNFECode(String NFECode) {
        this.NFECode = NFECode;
    }

    public boolean isAprovado() {
        return aprovado;
    }

    public void setAprovado(boolean aprovado) {
        this.aprovado = aprovado;
    }
}
