package com.api.soamer.model.concessionaria;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "tbl_concessionaria")
public class ConcessionariaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id_concessionaria")
    private Integer idConcessionaria;
    @JsonProperty("marca_concessionaria")
    private String marcaConcessionaria;
    @JsonProperty("nome_concessionaria")
    private String nomeConcessionaria;
    @JsonProperty("cnpj_concessionaria")
    private String cnpjConcessionaria;
    @JsonProperty("endereco_concessionaria")
    private String enderecoConcessionaria;

    public Integer getIdConcessionaria() {
        return idConcessionaria;
    }

    public void setIdConcessionaria(Integer idConcessionaria) {
        this.idConcessionaria = idConcessionaria;
    }

    public String getMarcaConcessionaria() {
        return marcaConcessionaria;
    }

    public void setMarcaConcessionaria(String marcaConcessionaria) {
        this.marcaConcessionaria = marcaConcessionaria;
    }

    public String getNomeConcessionaria() {
        return nomeConcessionaria;
    }

    public void setNomeConcessionaria(String nomeConcessionaria) {
        this.nomeConcessionaria = nomeConcessionaria;
    }

    public String getCnpjConcessionaria() {
        return cnpjConcessionaria;
    }

    public void setCnpjConcessionaria(String cnpjConcessionaria) {
        this.cnpjConcessionaria = cnpjConcessionaria;
    }

    public String getEnderecoConcessionaria() {
        return enderecoConcessionaria;
    }

    public void setEnderecoConcessionaria(String enderecoConcessionaria) {
        this.enderecoConcessionaria = enderecoConcessionaria;
    }
}
