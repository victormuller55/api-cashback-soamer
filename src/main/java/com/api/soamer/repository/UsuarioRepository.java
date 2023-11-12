package com.api.soamer.repository;

import com.api.soamer.model.usuario.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, Integer> {
    boolean existsByEmailUsuario(String emailUsuario);
    boolean existsByCpfUsuario(String cpfUsuario);
    UsuarioModel findByEmailUsuario(String emailUsuario);
}
