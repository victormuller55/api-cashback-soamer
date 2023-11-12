package com.api.soamer.repository;

import com.api.soamer.model.voucher.VaucherModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VaucherRepository extends JpaRepository<VaucherModel, Integer> { }
