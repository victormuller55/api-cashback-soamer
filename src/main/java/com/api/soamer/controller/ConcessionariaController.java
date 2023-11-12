package com.api.soamer.controller;

import com.api.soamer.model.concessionaria.ConcessionariaModel;
import com.api.soamer.model.usuario.UsuarioModel;
import com.api.soamer.repository.ConcessionariaRepository;
import com.api.soamer.repository.UsuarioRepository;
import com.api.soamer.responses.Error;
import com.api.soamer.responses.Success;
import com.api.soamer.util.Validators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/v1/soamer/concessionaria")
public class ConcessionariaController {

    @Autowired
    ConcessionariaRepository concessionariaRepository;
    @Autowired
    UsuarioRepository usuarioRepository;

    public ConcessionariaController(ConcessionariaRepository concessionariaRepository, UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping
    public ResponseEntity<Object> cadastrarConcessionaria(@RequestBody ConcessionariaModel concessionariaModel) {
        try {

            if (Validators.cnpjValido(concessionariaModel.getCnpjConcessionaria())) {
                concessionariaRepository.save(concessionariaModel);
                return Success.success200(concessionariaModel);
            }

            return Error.error400("CNPJ invalido");
        } catch (Exception e) {
            return Error.error500(e);
        }
    }

    @GetMapping
    public ResponseEntity<Object> todasConcessionaria() {
        try {
            return Success.success200(concessionariaRepository.findAll());
        } catch (Exception e) {
            return Error.error500(e);
        }
    }

    @PutMapping
    public ResponseEntity<Object> setConcessionariaUsuario(@RequestParam(name = "id_usuario") Integer idUsuario, @RequestParam(name = "id_concessionaria") Integer idConcessionaria) {
        try {

            Optional<UsuarioModel> usuario = usuarioRepository.findById(idUsuario);
            Optional<ConcessionariaModel> concessionaria = concessionariaRepository.findById(idConcessionaria);

            if (usuario.isPresent()) {
                if (concessionaria.isPresent()) {

                    usuario.get().setIdConcessionaria(idConcessionaria);
                    usuario.get().setNomeConcessionaria(concessionaria.get().getNomeConcessionaria());

                    usuarioRepository.save(usuario.get());

                    return Success.success200(concessionariaRepository.findAll());
                }
                return Error.error400("Concessionaria nao encontrada");
            }
            return Error.error400("Usuario nao encontrado");

        } catch (Exception e) {
            return Error.error500(e);
        }
    }
}
