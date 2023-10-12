package com.api.soamer.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_foto_usuario")
public class FotoUsuarioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    private byte[] dados;
    private String filePath;
    private Integer idUsuario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getDados() {
        return dados;
    }

    public void setDados(byte[] dados) {
        this.dados = dados;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
}
