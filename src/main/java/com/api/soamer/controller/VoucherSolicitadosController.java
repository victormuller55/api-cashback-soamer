package com.api.soamer.controller;

import com.api.soamer.model.extrato.ExtratoModel;
import com.api.soamer.model.extrato.VouchersSolicitadosModel;
import com.api.soamer.model.voucher.VoucherModel;
import com.api.soamer.repository.ExtratoRepository;
import com.api.soamer.repository.UsuarioRepository;
import com.api.soamer.repository.VouchersSolicitadosRepository;
import com.api.soamer.responses.Error;
import com.api.soamer.responses.Success;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/v1/soamer/voucher/solicitados")
public class VoucherSolicitadosController {

    @Autowired
    ExtratoRepository extratoRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    VouchersSolicitadosRepository vouchersSolicitadosRepository;

    public VoucherSolicitadosController(ExtratoRepository extratoRepository, UsuarioRepository usuarioRepository, VouchersSolicitadosRepository vouchersSolicitadosRepository) {
        this.extratoRepository = extratoRepository;
        this.usuarioRepository = usuarioRepository;
        this.vouchersSolicitadosRepository = vouchersSolicitadosRepository;
    }

    @GetMapping
    public ResponseEntity<Object> solicitados() {
        try {
            return Success.success200(vouchersSolicitadosRepository.findAll());
        } catch (Exception e) {
            return Error.error500(e);
        }
    }

    @PutMapping
    public ResponseEntity<Object> solicitadosEnviado(@RequestParam(name = "id_historico") Integer idVoucherSolicitado) {
        try {

            Optional<VouchersSolicitadosModel> vouchersSolicitadosModel = vouchersSolicitadosRepository.findById(idVoucherSolicitado);

            if (vouchersSolicitadosModel.isPresent()) {

                ExtratoModel extratoModel = getExtratoModel(vouchersSolicitadosModel.get().getIdUsuario(), vouchersSolicitadosModel.get().getIdVoucher(), vouchersSolicitadosModel.get().getTituloVoucher());
                vouchersSolicitadosModel.get().setEnviado(true);

                vouchersSolicitadosRepository.save(vouchersSolicitadosModel.get());
                extratoRepository.save(extratoModel);

                return Success.success200(vouchersSolicitadosRepository.findAll());
            }

            return Error.error400("Histórico não encontrado");

        } catch (Exception e) {
            return Error.error500(e);
        }
    }

    private static ExtratoModel getExtratoModel(Integer idUsuario, Integer idVaucher, String vaucherTitulo) {
        ExtratoModel extratoModel = new ExtratoModel();
        extratoModel.setTituloExtrato("Envio de voucher solicitado");
        extratoModel.setDescricaoExtrato("O voucher " + vaucherTitulo + ", foi enviado ao seu e-mail!");
        extratoModel.setEntradaExtrato(true);
        extratoModel.setPontosExtrato(0);
        extratoModel.setDataExtrato(new Date());
        extratoModel.setIdUsuario(idUsuario);
        extratoModel.setIdVoucher(idVaucher);
        return extratoModel;
    }
}
