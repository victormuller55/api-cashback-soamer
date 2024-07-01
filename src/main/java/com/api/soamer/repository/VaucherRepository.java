package com.api.soamer.repository;

import com.api.soamer.model.voucher.VoucherModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VaucherRepository extends JpaRepository<VoucherModel, Integer> { }
