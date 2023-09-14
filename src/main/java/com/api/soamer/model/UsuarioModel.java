package com.api.soamer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "tbl_usuario")
public class UsuarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id_usuario")
    private Integer idUsuario;
    @JsonProperty("nome_usuario")
    private String nomeUsuario;
    @JsonProperty("email_usuario")
    private String emailUsuario;
    @JsonProperty("cpf_usuario")
    private String cpfUsuario;
    @JsonProperty("pontos_usuario")
    private int pontosUsuario;
    @JsonProperty("pontos_pendentes_usuario")
    private int pontosPendentesUsuario;
    @JsonProperty("senha_usuario")
    private String senhaUsuario;

    @JsonProperty("data_usuario")
    private Date dataUsuario;

    public UsuarioModel() {
        dataUsuario = new Date();
        pontosUsuario = 0;
        pontosPendentesUsuario = 0;
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

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public String getCpfUsuario() {
        return cpfUsuario;
    }

    public void setCpfUsuario(String cpfUsuario) {
        this.cpfUsuario = cpfUsuario;
    }

    public int getPontosUsuario() {
        return pontosUsuario;
    }

    public void setPontosUsuario(int pontosUsuario) {
        this.pontosUsuario = pontosUsuario;
    }

    public int getPontosPendentesUsuario() {
        return pontosPendentesUsuario;
    }

    public void setPontosPendentesUsuario(int pontosPendentesUsuario) {
        this.pontosPendentesUsuario = pontosPendentesUsuario;
    }

    public String getSenhaUsuario() {
        return senhaUsuario;
    }

    public void setSenhaUsuario(String senhaUsuario) {
        this.senhaUsuario = senhaUsuario;
    }

    public Date getDataUsuario() {
        return dataUsuario;
    }

    public void setDataUsuario(Date dataUsuario) {
        this.dataUsuario = dataUsuario;
    }
}
