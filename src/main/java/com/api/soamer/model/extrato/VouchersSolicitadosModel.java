package com.api.soamer.model.extrato;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "tbl_vouchers_solicitados")
public class VouchersSolicitadosModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id_historico")
    Integer idHistorico;

    @JsonProperty("id_voucher")
    Integer idVoucher;

    @JsonProperty("id_usuario")
    Integer idUsuario;

    @JsonProperty("nome_usuario")
    String nomeUsuario;

    @JsonProperty("titulo")
    String tituloVoucher;

    @JsonProperty("valor")
    int valor;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    @JsonProperty("data_pedido")
    Date dataPedido;

    @JsonProperty("enviado")
    boolean enviado;

    public Integer getIdHistorico() {
        return idHistorico;
    }

    public void setIdHistorico(Integer idHistorico) {
        this.idHistorico = idHistorico;
    }

    public Integer getIdVoucher() {
        return idVoucher;
    }

    public void setIdVoucher(Integer idVoucher) {
        this.idVoucher = idVoucher;
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

    public String getTituloVoucher() {
        return tituloVoucher;
    }

    public void setTituloVoucher(String tituloVoucher) {
        this.tituloVoucher = tituloVoucher;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public Date getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(Date dataPedido) {
        this.dataPedido = dataPedido;
    }

    public boolean isEnviado() {
        return enviado;
    }

    public void setEnviado(boolean enviado) {
        this.enviado = enviado;
    }
}
