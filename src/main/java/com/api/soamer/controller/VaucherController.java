package com.api.soamer.controller;

import com.api.soamer.model.extrato.ExtratoModel;
import com.api.soamer.model.extrato.VouchersSolicitadosModel;
import com.api.soamer.model.usuario.UsuarioModel;
import com.api.soamer.model.voucher.VoucherModel;
import com.api.soamer.repository.ExtratoRepository;
import com.api.soamer.repository.UsuarioRepository;
import com.api.soamer.repository.VaucherRepository;
import com.api.soamer.repository.VouchersSolicitadosRepository;
import com.api.soamer.responses.Error;
import com.api.soamer.responses.Success;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;

import static com.api.soamer.util.Formatters.formatDDMMYYYYHHMMToDate;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/v1/soamer/vaucher")
public class VaucherController {
    @Autowired
    VaucherRepository voucherRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    ExtratoRepository extratoRepository;
    @Autowired
    VouchersSolicitadosRepository vouchersSolicitadosRepository;

    public VaucherController(VaucherRepository voucherRepository, UsuarioRepository usuarioRepository, ExtratoRepository extratoRepository, VouchersSolicitadosRepository vouchersSolicitadosRepository) {
        this.voucherRepository = voucherRepository;
        this.usuarioRepository = usuarioRepository;
        this.extratoRepository = extratoRepository;
        this.vouchersSolicitadosRepository = vouchersSolicitadosRepository;
    }

    @Value("${upload.path.voucher}")
    private String uploadPathVoucher;

    @PostMapping
    public ResponseEntity<Object> createVaucher(@RequestBody VoucherModel voucherModel) {
        try {

            voucherModel.setDataComecoVaucher(formatDDMMYYYYHHMMToDate(voucherModel.getDataComecoVaucher().toString()));
            voucherModel.setDataFinalVaucher(formatDDMMYYYYHHMMToDate(voucherModel.getDataFinalVaucher().toString()));

            if (!voucherModel.getTituloVaucher().isEmpty()) {
                if (!voucherModel.getInfoVaucher().isEmpty()) {
                    if (!voucherModel.getPontosCheioVaucher().equals(0)) {
                        voucherModel.setImagePath("default_image.jpg");
                        voucherModel.setPontosVaucher(voucherModel.getPontosCheioVaucher() - voucherModel.getDescontoVaucher());
                        voucherRepository.save(voucherModel);

                        return Success.success200(voucherModel);

                    }
                    return Error.error400("PontosCheiosVaucher não deve ser 0");

                }
                return Error.error400("InfoVaucher não deve ser vazio");

            }
            return Error.error400("TituloVaucher não deve ser vazio");

        } catch (Exception e) {
            return Error.error500(e);
        }
    }

    @PostMapping(path = "/image")
    public ResponseEntity<Object> postImageVoucher(@RequestParam("file") MultipartFile file, @RequestParam(name = "id_voucher") Integer idVoucher) {
        try {
            if (!file.isEmpty()) {
                Optional<VoucherModel> voucherFinded = voucherRepository.findById(idVoucher);
                if (voucherFinded.isPresent()) {

                    VoucherModel voucherModel = voucherFinded.get();

                    if (voucherModel.getImagePath() != null && !voucherModel.getImagePath().equals("default_image.jpg")) {
                        Path previousImagePath = Paths.get(uploadPathVoucher, voucherModel.getImagePath());
                        Files.delete(previousImagePath);
                    }

                    String fileName = "image" + "_" + idVoucher + "_" + file.getOriginalFilename();
                    Path filePath = Paths.get(uploadPathVoucher, fileName);
                    Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                    voucherModel.setImagePath(fileName);
                    voucherRepository.save(voucherModel);
                    return Success.success200("Ok");

                } else {
                    return Error.error400("Usuário não encontrado");
                }
            } else {
                return Error.error400("O arquivo está vazio");
            }
        } catch (Exception e) {
            System.out.println(e);
            return Error.error500(e);
        }
    }

    @GetMapping(path = "/image")
    public ResponseEntity<Object> getVoucherImage(@RequestParam(name = "id_voucher") Integer idVoucher) {
        try {

            Optional<VoucherModel> voucherFinded = voucherRepository.findById(idVoucher);

            if (voucherFinded.isPresent()) {

                VoucherModel voucher = voucherFinded.get();
                String path = voucher.getImagePath();

                if (path != null) {
                    Path filePath = Paths.get(uploadPathVoucher, path);

                    UrlResource resource = new UrlResource(filePath.toUri());

                    if (resource.exists() && resource.isReadable()) {

                        HttpHeaders headers = new HttpHeaders();
                        headers.setContentType(MediaType.IMAGE_PNG);

                        return ResponseEntity.ok().headers(headers).body(resource);
                    } else {
                        return Error.error400("Resourse não encontrada");
                    }
                } else {
                    return Error.error400("Imagem não encontrada");
                }
            } else {
                return Error.error400("Usuario não encontrado");
            }
        } catch (MalformedURLException e) {
            return Error.error500(e);
        }
    }

    @GetMapping
    public ResponseEntity<Object> getVaucherList() {
        try {

            List<VoucherModel> vaucherEnviados = new ArrayList<>();
            Date dataAtual = new Date();

            for (VoucherModel vaucher : voucherRepository.findAll()) {
                if (dataAtual.toInstant().isAfter(vaucher.getDataComecoVaucher().toInstant()) && dataAtual.toInstant().isBefore(vaucher.getDataFinalVaucher().toInstant())) {
                    vaucherEnviados.add(vaucher);
                }
            }

            if (!vaucherEnviados.isEmpty()) {
                return Success.success200(vaucherEnviados);
            }

            return Success.success200(vaucherEnviados);

        } catch (Exception e) {
            return Error.error500(e);
        }
    }

    @PutMapping()
    public ResponseEntity<Object> descountVaucher(@RequestParam(name = "id") Integer id, @RequestParam(name = "desconto") Integer desconto) {

        try {
            Optional<VoucherModel> vaucherFound = voucherRepository.findById(id);

            if (vaucherFound.isPresent()) {

                VoucherModel vaucher = vaucherFound.get();
                vaucher.setDescontoVaucher(desconto);

                if (desconto > 0) {
                    vaucher.setPontosVaucher(vaucher.getPontosCheioVaucher() - desconto);
                    voucherRepository.save(vaucher);

                    return Success.success200(vaucher);
                }

                vaucher.setPontosVaucher(vaucher.getPontosCheioVaucher());
                voucherRepository.save(vaucher);

                return Success.success200(vaucher);
            }

            return Error.error400("Nenhum vaucher com o ID: " + id);

        } catch (Exception e) {
            return Error.error500(e);
        }
    }

    @GetMapping(path = "/promocao")
    public ResponseEntity<Object> getVaucherListPromotion() {
        try {

            List<VoucherModel> vaucherEnviados = new ArrayList<>();
            Date dataAtual = new Date();

            for (VoucherModel vaucher : voucherRepository.findAll()) {
                if (dataAtual.toInstant().isAfter(vaucher.getDataComecoVaucher().toInstant()) && dataAtual.toInstant().isBefore(vaucher.getDataFinalVaucher().toInstant())) {
                    if (vaucher.getDescontoVaucher() > 0) {
                        vaucherEnviados.add(vaucher);
                    }
                }
            }

            if (!vaucherEnviados.isEmpty()) {
                return Success.success200(vaucherEnviados);
            }

            return Success.success200(vaucherEnviados);

        } catch (Exception e) {
            return Error.error500(e);
        }
    }

    @GetMapping(path = "/trocados")
    public ResponseEntity<Object> getTop10VaucherTrocados() {
        try {
            List<VoucherModel> vaucherEnviados;
            Date dataAtual = new Date();

            List<VoucherModel> vouchersValidos = voucherRepository.findAll().stream()
                    .filter(vaucher -> dataAtual.toInstant().isAfter(vaucher.getDataComecoVaucher().toInstant()) && dataAtual.toInstant().isBefore(vaucher.getDataFinalVaucher().toInstant()))
                    .sorted(Comparator.comparingInt(VoucherModel::getTrocado).reversed()).toList();

            vaucherEnviados = vouchersValidos.stream().limit(10).collect(Collectors.toList());

            if (!vaucherEnviados.isEmpty()) {
                return Success.success200(vaucherEnviados);
            }

            return Success.success200(vaucherEnviados);

        } catch (Exception e) {
            return Error.error500(e);
        }
    }

    @GetMapping(path = "/trocar")
    public ResponseEntity<Object> trocarVaucher(@RequestParam(name = "id_usuario") Integer idUsuario, @RequestParam(name = "id_vaucher") Integer idVaucher) {
        try {

            if (idUsuario != null && idVaucher != null) {

                Optional<VoucherModel> vaucherModel = voucherRepository.findById(idVaucher);
                Optional<UsuarioModel> usuarioModel = usuarioRepository.findById(idUsuario);

                if (vaucherModel.isPresent()) {
                    if (usuarioModel.isPresent()) {
                        if (usuarioModel.get().getPontosUsuario() >= vaucherModel.get().getPontosVaucher()) {

                            vaucherModel.get().setTrocado(vaucherModel.get().getTrocado() + 1);

                            usuarioModel.get().setPontosUsuario(usuarioModel.get().getPontosUsuario() - vaucherModel.get().getPontosVaucher());
                            vouchersSolicitadosRepository.save(getSolicitadosModel(vaucherModel, usuarioModel));
                            usuarioRepository.save(usuarioModel.get());
                            extratoRepository.save(getExtratoModel(idUsuario, idVaucher, vaucherModel));

                            return Success.success200("0000-0000-0000-0000-0000");

                        }
                        return Error.error400("Saldo insuficiente");
                    }
                    return Error.error400("Nao foi encontrado nenhum usuario com o ID informado");
                }
                return Error.error400("Nao foi encontrado nenhum vaucher com o ID informado");
            }
            return Error.error400("Os parametros são obrigatOrios");

        } catch (Exception e) {
            return Error.error500(e);
        }
    }

    private static VouchersSolicitadosModel getSolicitadosModel(Optional<VoucherModel> vaucherModel, Optional<UsuarioModel> usuarioModel) {
        VouchersSolicitadosModel vouchersSolicitadosModel = new VouchersSolicitadosModel();

        vouchersSolicitadosModel.setIdVoucher(vaucherModel.get().getIdVaucher());
        vouchersSolicitadosModel.setIdUsuario(usuarioModel.get().getIdUsuario());
        vouchersSolicitadosModel.setNomeUsuario(usuarioModel.get().getNomeUsuario());
        vouchersSolicitadosModel.setTituloVoucher(vaucherModel.get().getTituloVaucher());
        vouchersSolicitadosModel.setValor(vaucherModel.get().getPontosVaucher());
        vouchersSolicitadosModel.setDataPedido(new Date());
        vouchersSolicitadosModel.setEnviado(false);
        return vouchersSolicitadosModel;
    }

    private static ExtratoModel getExtratoModel(Integer idUsuario, Integer idVaucher, Optional<VoucherModel> vaucherModel) {
        ExtratoModel extratoModel = new ExtratoModel();
        extratoModel.setTituloExtrato("Troca por voucher");
        extratoModel.setDescricaoExtrato("Voucher solicitado - " + vaucherModel.get().getTituloVaucher());
        extratoModel.setEntradaExtrato(false);
        extratoModel.setPontosExtrato(vaucherModel.get().getPontosVaucher());
        extratoModel.setDataExtrato(new Date());
        extratoModel.setIdUsuario(idUsuario);
        extratoModel.setIdVoucher(idVaucher);
        return extratoModel;
    }
}
