package com.api.soamer.model.voucher;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "tbl_vaucher")
public class VoucherModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id_vaucher")
    private Integer idVaucher;
    @JsonProperty("titulo_vaucher")
    private String tituloVaucher;
    @JsonProperty("info_vaucher")
    private String infoVaucher;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    @JsonProperty("data_comeco_vaucher")
    private Date dataComecoVaucher;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    @JsonProperty("data_final_vaucher")
    private Date dataFinalVaucher;
    @JsonProperty("pontos_cheio_vaucher")
    private Integer pontosCheioVaucher = 0;
    @JsonProperty("desconto_vaucher")
    private Integer descontoVaucher;
    @JsonProperty("pontos_vaucher")
    private Integer pontosVaucher;
    @JsonIgnore
    @JsonProperty("trocado")
    private Integer trocado;

    @JsonIgnore
    private String imagePath;

    public VoucherModel() {
        descontoVaucher = 0;
        trocado = 0;
    }

    public Integer getIdVaucher() {
        return idVaucher;
    }

    public void setIdVaucher(Integer idVaucher) {
        this.idVaucher = idVaucher;
    }

    public String getTituloVaucher() {
        return tituloVaucher;
    }

    public void setTituloVaucher(String tituloVaucher) {
        this.tituloVaucher = tituloVaucher;
    }

    public String getInfoVaucher() {
        return infoVaucher;
    }

    public void setInfoVaucher(String infoVaucher) {
        this.infoVaucher = infoVaucher;
    }

    public Date getDataComecoVaucher() {
        return dataComecoVaucher;
    }

    public void setDataComecoVaucher(Date dataComecoVaucher) {
        this.dataComecoVaucher = dataComecoVaucher;
    }

    public Date getDataFinalVaucher() {
        return dataFinalVaucher;
    }

    public void setDataFinalVaucher(Date dataFinalVaucher) {
        this.dataFinalVaucher = dataFinalVaucher;
    }

    public Integer getPontosCheioVaucher() {
        return pontosCheioVaucher;
    }

    public void setPontosCheioVaucher(Integer pontosCheioVaucher) {
        this.pontosCheioVaucher = pontosCheioVaucher;
    }

    public Integer getDescontoVaucher() {
        return descontoVaucher;
    }

    public void setDescontoVaucher(Integer descontoVaucher) {
        this.descontoVaucher = descontoVaucher;
    }

    public Integer getPontosVaucher() {
        return pontosVaucher;
    }
    public void setPontosVaucher(Integer pontosVaucher) {
        this.pontosVaucher = pontosVaucher;
    }

    public Integer getTrocado() {
        return trocado;
    }

    public void setTrocado(Integer trocado) {
        this.trocado = trocado;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
