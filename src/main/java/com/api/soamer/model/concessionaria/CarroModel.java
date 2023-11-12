package com.api.soamer.model.concessionaria;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "tbl_carro")
public class CarroModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id_carro")
    private Integer idCarro;
    @JsonProperty("id_concessionaria")
    private Integer idConcessionaria;
    @JsonProperty("modelo_carro")
    private Integer modeloCarro;
    @JsonProperty("image_path")
    private String imagePath;

    public Integer getIdCarro() {
        return idCarro;
    }

    public void setIdCarro(Integer idCarro) {
        this.idCarro = idCarro;
    }

    public Integer getIdConcessionaria() {
        return idConcessionaria;
    }

    public void setIdConcessionaria(Integer idConcessionaria) {
        this.idConcessionaria = idConcessionaria;
    }

    public Integer getModeloCarro() {
        return modeloCarro;
    }

    public void setModeloCarro(Integer modeloCarro) {
        this.modeloCarro = modeloCarro;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
