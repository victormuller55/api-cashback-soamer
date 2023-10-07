package com.api.soamer.controller;

import com.api.soamer.model.ExtratoModel;
import com.api.soamer.repository.ExtratoRepository;
import com.api.soamer.repository.UsuarioRepository;
import com.api.soamer.responses.Error;
import com.api.soamer.responses.Success;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/v1/soamer/extrato")
public class ExtratoController {

    @Autowired
    ExtratoRepository extratoRepository;
    @Autowired
    UsuarioRepository usuarioRepository;

    public ExtratoController(ExtratoRepository extratoRepository, UsuarioRepository usuarioRepository) {
        this.extratoRepository = extratoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public ResponseEntity<Object> extrato(@RequestParam(name = "id_usuario") Integer idUsuario) {
        try {

            if (usuarioRepository.existsById(idUsuario)) {
                List<ExtratoModel> extratoModels = extratoRepository.findByIdUsuario(idUsuario);
                return Success.success200(extratoModels);
            }

            return Error.error400("Usuario não encontrado");
        } catch (Exception e) {
            return Error.error500(e);
        }
    }
}
