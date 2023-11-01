package com.api.soamer.controller;

import com.api.soamer.model.UsuarioModel;
import com.api.soamer.repository.UsuarioRepository;
import com.api.soamer.responses.Error;
import com.api.soamer.responses.Success;
import com.api.soamer.util.Validators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/v1/soamer/senha/recuperar")
public class RecuperarSenhaController {

    @Autowired
    UsuarioRepository usuarioRepository;

    public RecuperarSenhaController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public ResponseEntity<Object> entrarUsuario(@RequestParam(name = "email") String emailUsuario) {
        try {
            if (Validators.emailValido(emailUsuario)) {
                if (usuarioRepository.existsByEmailUsuario(emailUsuario)) {

                    UsuarioModel usuario = usuarioRepository.findByEmailUsuario(emailUsuario);

                    Random random = new Random();

                    int numeroGerado = 0;

                    while(numeroGerado < 100000) {
                        numeroGerado = random.nextInt(999999);
                    }

                    return Success.success200(numeroGerado);
                }

                return Error.error400("Usuario nao encontrado");
            }

            return Error.error400("E-mail invalido");
        } catch (Exception e) {
            return Error.error500(e);
        }
    }
}
