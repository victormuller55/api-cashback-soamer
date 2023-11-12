package com.api.soamer.repository;

import com.api.soamer.model.concessionaria.CarroModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarroRepository extends JpaRepository<CarroModel, Integer> {}