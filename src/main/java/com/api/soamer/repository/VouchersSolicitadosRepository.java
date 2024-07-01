package com.api.soamer.repository;

import com.api.soamer.model.extrato.VouchersSolicitadosModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VouchersSolicitadosRepository extends JpaRepository<VouchersSolicitadosModel, Integer> {
}
