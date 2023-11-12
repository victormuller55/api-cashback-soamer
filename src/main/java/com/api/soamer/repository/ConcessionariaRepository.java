package com.api.soamer.repository;

import com.api.soamer.model.concessionaria.ConcessionariaModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcessionariaRepository extends JpaRepository<ConcessionariaModel, Integer> {}