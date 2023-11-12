package com.api.soamer.repository;

import com.api.soamer.model.extrato.ExtratoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExtratoRepository extends JpaRepository<ExtratoModel, Integer> {
    List<ExtratoModel> findByIdUsuario(Integer idUsuario);
}
