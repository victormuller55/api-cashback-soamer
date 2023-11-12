package com.api.soamer.controller;

import com.api.soamer.model.usuario.EditUsuarioModel;
import com.api.soamer.model.usuario.HomeModel;
import com.api.soamer.model.usuario.UsuarioModel;
import com.api.soamer.repository.ConcessionariaRepository;
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
    @Autowired
    ConcessionariaRepository concessionariaRepository;

    public UsuarioController(UsuarioRepository usuarioRepository, ConcessionariaRepository concessionariaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.concessionariaRepository = concessionariaRepository;
    }

    @PostMapping
    public ResponseEntity<Object> createUsuario(@RequestBody UsuarioModel usuarioModel) {
        try {
            if (Validators.emailValido(usuarioModel.getEmailUsuario())) {
                if (!usuarioRepository.existsByEmailUsuario(usuarioModel.getEmailUsuario())) {
                    if (!usuarioRepository.existsByCpfUsuario(usuarioModel.getCpfUsuario())) {
                        usuarioModel.setImagePath("default_image.jpg");
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
    public ResponseEntity<Object> entrarUsuario(@RequestParam(name = "email") String emailUsuario, @RequestParam String senha, @RequestParam(name = "nova_senha") String novaSenha) {
        try {
            if (Validators.emailValido(emailUsuario)) {
                if (usuarioRepository.existsByEmailUsuario(emailUsuario)) {

                    UsuarioModel usuario = usuarioRepository.findByEmailUsuario(emailUsuario);

                    if(senha.isEmpty() && novaSenha != "") {

                        usuario.setSenhaUsuario(novaSenha);
                        usuarioRepository.save(usuario);

                        return Success.success200(usuario);
                    }

                    if (usuario.getSenhaUsuario().toLowerCase(Locale.ROOT).equals(senha.toLowerCase(Locale.ROOT))) {
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

    @GetMapping(path = "/home")
    public ResponseEntity<Object> home(@RequestParam(name = "email") String emailUsuario) {
        try {
            if (Validators.emailValido(emailUsuario)) {
                if (usuarioRepository.existsByEmailUsuario(emailUsuario)) {

                    HomeModel homeModel = new HomeModel();
                    UsuarioModel usuarioModel = usuarioRepository.findByEmailUsuario(emailUsuario);

                    homeModel.setPontosUsuario(usuarioModel.getPontosUsuario());
                    homeModel.setPontosPendentesUsuario(usuarioModel.getPontosPendentesUsuario());
                    homeModel.setValorPix(usuarioModel.getPontosUsuario());

                    return Success.success200(homeModel);
                }

                return Error.error400("E-mail nao cadastrado");
            }

            return Error.error400("E-mail invalido");
        } catch (Exception e) {
            return Error.error500(e);
        }
    }

    @GetMapping(path = "/todos")
    public ResponseEntity<Object> carregarUsuario() {
        try {
            return Success.success200(usuarioRepository.findAll());
        } catch (Exception e) {
            return Error.error500(e);
        }
    }

    @PutMapping(path = "/edit")
    public ResponseEntity<Object> editarUsuario(@RequestBody EditUsuarioModel editUsuarioModel) {
        try {
            if (Validators.emailValido(editUsuarioModel.getEmail())) {
                if (usuarioRepository.existsByEmailUsuario(editUsuarioModel.getEmail())) {
                    UsuarioModel usuario = usuarioRepository.findByEmailUsuario(editUsuarioModel.getEmail());
                    if (usuario.getSenhaUsuario().toLowerCase(Locale.ROOT).equals(editUsuarioModel.getSenha().toLowerCase(Locale.ROOT))) {

                        if (!editUsuarioModel.getNewEmail().isEmpty()) {
                            usuario.setEmailUsuario(editUsuarioModel.getNewEmail());
                        }

                        if (!editUsuarioModel.getNewSenha().isEmpty()) {
                            usuario.setSenhaUsuario(editUsuarioModel.getNewSenha());
                        }

                        if (!editUsuarioModel.getNome().isEmpty()) {
                            usuario.setNomeUsuario(editUsuarioModel.getNome());
                        }

                        if (!editUsuarioModel.getNome().isEmpty()) {
                            usuario.setNomeUsuario(editUsuarioModel.getNome());
                        }

                        usuarioRepository.save(usuario);
                        return Success.success200(usuario);
                    }

                    return Error.error400("Senha incorreta");
                }

                return Error.error400("E-mail nao cadastrado");
            }

            return Error.error400("E-mail inválido");
        } catch (Exception e) {
            return Error.error500(e);
        }
    }
}
