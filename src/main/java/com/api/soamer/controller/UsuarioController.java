package com.api.soamer.controller;

import com.api.soamer.model.UsuarioModel;
import com.api.soamer.repository.UsuarioRepository;
import com.api.soamer.responses.Error;
import com.api.soamer.responses.Success;
import com.api.soamer.util.Validators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/v1/soamer/usuario")
public class UsuarioController {

    @Autowired
    UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping
    public ResponseEntity<Object> createUsuario(@RequestBody UsuarioModel usuarioModel) {
        try {
            if (Validators.emailValido(usuarioModel.getEmailUsuario())) {
                if (!usuarioRepository.existsByEmailUsuario(usuarioModel.getEmailUsuario())) {
                    if (!usuarioRepository.existsByCpfUsuario(usuarioModel.getCpfUsuario())) {
                        usuarioRepository.save(usuarioModel);

                        return Success.success200(usuarioModel);
                    }

                    return Error.error400("CPF usado em outra conta!");
                }

                return Error.error400("E-mail usado em outra conta!");
            }

            return Error.error400("E-mail invalido");
        } catch (Exception e) {
            return Error.error500(e);
        }
    }

    @GetMapping
    public ResponseEntity<Object> entrarUsuario(@RequestParam(name = "email") String emailUsuario, @RequestParam String senha) {
        try {
            if (Validators.emailValido(emailUsuario)) {
                if (usuarioRepository.existsByEmailUsuario(emailUsuario)) {
                    UsuarioModel usuario = usuarioRepository.findByEmailUsuario(emailUsuario);
                    if(usuario.getSenhaUsuario().toLowerCase(Locale.ROOT).equals(senha.toLowerCase(Locale.ROOT))){
                        return Success.success200(usuario);
                    }

                    return Error.error400("Senha incorreta");
                }

                return Error.error400("E-mail nao cadastrado");
            }

            return Error.error400("E-mail invalido");
        } catch (Exception e) {
            return Error.error500(e);
        }
    }
}
