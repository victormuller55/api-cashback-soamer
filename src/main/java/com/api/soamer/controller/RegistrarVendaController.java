package com.api.soamer.controller;

import com.api.soamer.model.ExtratoModel;
import com.api.soamer.model.RegistrarVendaModel;
import com.api.soamer.model.UsuarioModel;
import com.api.soamer.repository.ExtratoRepository;
import com.api.soamer.repository.RegistrarVendaRepository;
import com.api.soamer.repository.UsuarioRepository;
import com.api.soamer.repository.VaucherRepository;
import com.api.soamer.responses.Error;
import com.api.soamer.responses.Success;
import com.api.soamer.util.Formatters;
import com.api.soamer.util.Validators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/v1/soamer/venda")
public class RegistrarVendaController {

    @Autowired
    VaucherRepository voucherRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    ExtratoRepository extratoRepository;
    @Autowired
    RegistrarVendaRepository vendaRepository;

    public RegistrarVendaController(VaucherRepository voucherRepository, UsuarioRepository usuarioRepository, ExtratoRepository extratoRepository, RegistrarVendaRepository registrarVendaRepository) {
        this.voucherRepository = voucherRepository;
        this.usuarioRepository = usuarioRepository;
        this.extratoRepository = extratoRepository;
        this.vendaRepository = registrarVendaRepository;
    }

    @PostMapping
    public ResponseEntity<Object> createVenda(@RequestBody RegistrarVendaModel registrarVendaModel) {
        try {
            if(!registrarVendaModel.getNFECode().isEmpty()) {

                if(Validators.isValidNfeNumber(registrarVendaModel.getNFECode())) {
                    if(!vendaRepository.existsByNFECode(registrarVendaModel.getNFECode())) {

                        Optional<UsuarioModel> usuarioModel = usuarioRepository.findById(registrarVendaModel.getIdUsuario());

                        if(usuarioModel.isPresent()) {

                            ExtratoModel extratoModel = getExtratoModel(registrarVendaModel.getIdUsuario());
                            usuarioModel.get().setPontosUsuario(usuarioModel.get().getPontosUsuario() + 30);

                            usuarioRepository.save(usuarioModel.get());
                            extratoRepository.save(extratoModel);
                            vendaRepository.save(registrarVendaModel);

                            return Success.success200(registrarVendaModel);
                        }

                        return Error.error400("Usuario nao encontrado");
                    }

                    return Error.error400("NF-E ja registrada");
                }

                return Error.error400("Codigo NF-E invalido");
            }

            return Error.error400("N-FE esta vazio");
        } catch (Exception e) {
            return Error.error500(e);
        }
    }

    private static ExtratoModel getExtratoModel(Integer idUsuario) {
        ExtratoModel extratoModel = new ExtratoModel();
        extratoModel.setTituloExtrato("Registrada venda");
        extratoModel.setDescricaoExtrato("Registro de venda");
        extratoModel.setEntradaExtrato(true);
        extratoModel.setPontosExtrato(30);
        extratoModel.setDataExtrato(new Date());
        extratoModel.setIdUsuario(idUsuario);
        return extratoModel;
    }
}
