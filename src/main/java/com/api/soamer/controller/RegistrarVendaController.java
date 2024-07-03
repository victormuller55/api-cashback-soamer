package com.api.soamer.controller;

import com.api.soamer.model.extrato.ExtratoModel;
import com.api.soamer.model.usuario.UsuarioModel;
import com.api.soamer.model.venda.RegistrarVendaModel;
import com.api.soamer.repository.ExtratoRepository;
import com.api.soamer.repository.RegistrarVendaRepository;
import com.api.soamer.repository.UsuarioRepository;
import com.api.soamer.repository.VaucherRepository;
import com.api.soamer.responses.Error;
import com.api.soamer.responses.Success;
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

    @GetMapping
    public ResponseEntity<Object> getVendas() {
        try {
            return Success.success200(vendaRepository.findAll());
        } catch (Exception e) {
            return Error.error500(e);
        }
    }

    @PutMapping(path = "/recusar")
    public ResponseEntity<Object> putVendas(@RequestParam(name = "id_venda") Integer idVenda,
                                            @RequestParam(name = "mensagem") String mensagem) {
        try {

            Optional<RegistrarVendaModel> vendaModel = vendaRepository.findById(idVenda);

            if (vendaModel.isPresent()) {

                Optional<UsuarioModel> usuarioModel = usuarioRepository.findById(vendaModel.get().getIdUsuario());
                vendaModel.get().setAprovado(2);

                if (usuarioModel.isPresent()) {
//                    if(aprovado == 1) {
//
//                        int pontos = switch (vendaModel.get().getIdPonteira()) {
//                            case 0, 2 -> 20;
//                            case 1, 3 -> 10;
//                            default -> 0;
//                        };
//
//                        ExtratoModel extratoModel = getExtratoModelVendaAceitaRecusada(usuarioModel.get().getIdUsuario(), "Venda Aprovada: " + vendaModel.get().getNFECode(), "Foram adicionados os pontos correspondentes a ponteira vendida em sua conta");
//                        usuarioModel.get().setPontosPendentesUsuario(usuarioModel.get().getPontosPendentesUsuario() - pontos);
//                        usuarioModel.get().setPontosUsuario(usuarioModel.get().getPontosUsuario() + pontos);
//
//                        extratoRepository.save(extratoModel);
//                        usuarioRepository.save(usuarioModel.get());
//                        vendaRepository.save(vendaModel.get());
//
//                        return Success.success200(vendaRepository.findAll());
//                    }


                    int pontos = switch (vendaModel.get().getIdPonteira()) {
                        case 0, 2 -> 20;
                        case 1, 3 -> 10;
                        default -> 0;
                    };

                    ExtratoModel extratoModel = getExtratoModelVendaAceitaRecusada(usuarioModel.get().getIdUsuario(), "Venda Recusada: " + vendaModel.get().getNFECode(), "Motivo: " + mensagem,0);
                    usuarioModel.get().setPontosPendentesUsuario(usuarioModel.get().getPontosPendentesUsuario() - pontos);

                    extratoRepository.save(extratoModel);
                    usuarioRepository.save(usuarioModel.get());
                    vendaRepository.save(vendaModel.get());

                    return Success.success200(vendaRepository.findAll());

                }

                return Error.error400("Usuario não encontrado");
            }

            return Error.error400("Nenhuma venda encontrada");

        } catch (Exception e) {
            return Error.error500(e);
        }
    }

    @PutMapping()
    public ResponseEntity<Object> putVendas(@RequestParam(name = "id_venda") Integer idVenda) {
        try {

            Optional<RegistrarVendaModel> vendaModel = vendaRepository.findById(idVenda);

            if (vendaModel.isPresent()) {

                Optional<UsuarioModel> usuarioModel = usuarioRepository.findById(vendaModel.get().getIdUsuario());
                vendaModel.get().setAprovado(1);

                if (usuarioModel.isPresent()) {

                    int pontos = switch (vendaModel.get().getIdPonteira()) {
                        case 0, 2 -> 20;
                        case 1, 3 -> 10;
                        default -> 0;
                    };

                    ExtratoModel extratoModel = getExtratoModelVendaAceitaRecusada(usuarioModel.get().getIdUsuario(), "Venda Aprovada: " + vendaModel.get().getNFECode(), "Foram adicionados os pontos correspondentes a ponteira vendida em sua conta", pontos);
                    usuarioModel.get().setPontosPendentesUsuario(usuarioModel.get().getPontosPendentesUsuario() - pontos);
                    usuarioModel.get().setPontosUsuario(usuarioModel.get().getPontosUsuario() + pontos);

                    extratoRepository.save(extratoModel);
                    usuarioRepository.save(usuarioModel.get());
                    vendaRepository.save(vendaModel.get());

                    return Success.success200(vendaRepository.findAll());

                }

                return Error.error400("Usuario não encontrado");
            }

            return Error.error400("Nenhuma venda encontrada");

        } catch (Exception e) {
            return Error.error500(e);
        }
    }

    @PostMapping
    public ResponseEntity<Object> createVenda(@RequestBody RegistrarVendaModel registrarVendaModel) {
        try {
            if (!registrarVendaModel.getNFECode().isEmpty()) {

                if (Validators.isValidNfeNumber(registrarVendaModel.getNFECode())) {

                    if (!vendaRepository.existsByNFECode(registrarVendaModel.getNFECode())) {

                        Optional<UsuarioModel> usuarioModel = usuarioRepository.findById(registrarVendaModel.getIdUsuario());

                        if (usuarioModel.isPresent()) {

                            int pontos = switch (registrarVendaModel.getIdPonteira()) {
                                case 0, 2 -> 20;
                                case 1, 3 -> 10;
                                default -> 0;
                            };

                            registrarVendaModel.setNomeUsuario(usuarioModel.get().getNomeUsuario());

                            ExtratoModel extratoModel = getExtratoModelVendaEnviada(registrarVendaModel.getIdUsuario(), registrarVendaModel.getNFECode(), pontos);
                            usuarioModel.get().setPontosPendentesUsuario(usuarioModel.get().getPontosPendentesUsuario() + pontos);

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

    private static ExtratoModel getExtratoModelVendaEnviada(Integer idUsuario, String codigoNFE, Integer pontos) {
        ExtratoModel extratoModel = new ExtratoModel();
        extratoModel.setTituloExtrato("Venda enviada para análise");
        extratoModel.setDescricaoExtrato("Aguarde até 3 dias uteis para que sua venda com código NFE: " + codigoNFE + ", seja aprovada.");
        extratoModel.setEntradaExtrato(true);
        extratoModel.setPontosExtrato(0);
        extratoModel.setDataExtrato(new Date());
        extratoModel.setIdUsuario(idUsuario);
        return extratoModel;
    }

    private static ExtratoModel getExtratoModelVendaAceitaRecusada(Integer idUsuario, String titulo, String mensagem, Integer pontos) {
        ExtratoModel extratoModel = new ExtratoModel();
        extratoModel.setTituloExtrato(titulo);
        extratoModel.setDescricaoExtrato(mensagem);
        extratoModel.setEntradaExtrato(true);
        extratoModel.setPontosExtrato(pontos);
        extratoModel.setDataExtrato(new Date());
        extratoModel.setIdUsuario(idUsuario);
        return extratoModel;
    }
}
