package com.api.soamer.repository;

import com.api.soamer.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, Integer> {
    boolean existsByEmailUsuario(String emailUsuario);
    boolean existsByCpfUsuario(String cpfUsuario);
}
