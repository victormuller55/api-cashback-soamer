package com.api.soamer.controller;

import com.api.soamer.model.ExtratoModel;
import com.api.soamer.model.UsuarioModel;
import com.api.soamer.model.VaucherModel;
import com.api.soamer.repository.ExtratoRepository;
import com.api.soamer.repository.UsuarioRepository;
import com.api.soamer.repository.VaucherRepository;
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

    public VaucherController(VaucherRepository vaucherRepository, ExtratoRepository extratoRepository, UsuarioRepository usuarioRepository) {
        this.voucherRepository = vaucherRepository;
        this.extratoRepository = extratoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Value("${upload.path.voucher}")
    private String uploadPathVoucher;

    @PostMapping
    public ResponseEntity<Object> createVaucher(@RequestBody VaucherModel vaucherModel) {
        try {

            vaucherModel.setDataComecoVaucher(formatDDMMYYYYHHMMToDate(vaucherModel.getDataComecoVaucher().toString()));
            vaucherModel.setDataFinalVaucher(formatDDMMYYYYHHMMToDate(vaucherModel.getDataFinalVaucher().toString()));

            if (!vaucherModel.getTituloVaucher().isEmpty()) {
                if (!vaucherModel.getInfoVaucher().isEmpty()) {
                    if (!vaucherModel.getPontosCheioVaucher().equals(0)) {
                        vaucherModel.setPontosVaucher(vaucherModel.getPontosCheioVaucher() - vaucherModel.getDescontoVaucher());
                        voucherRepository.save(vaucherModel);

                        return Success.success200(vaucherModel);

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
                Optional<VaucherModel> voucherFinded = voucherRepository.findById(idVoucher);
                if (voucherFinded.isPresent()) {

                    VaucherModel voucherModel = voucherFinded.get();

                    if (voucherModel.getImagePath() != null) {
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

            Optional<VaucherModel> voucherFinded = voucherRepository.findById(idVoucher);

            if (voucherFinded.isPresent()) {

                VaucherModel voucher = voucherFinded.get();
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

            List<VaucherModel> vaucherEnviados = new ArrayList<>();
            Date dataAtual = new Date();

            for (VaucherModel vaucher : voucherRepository.findAll()) {
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
            Optional<VaucherModel> vaucherFound = voucherRepository.findById(id);

            if (vaucherFound.isPresent()) {

                VaucherModel vaucher = vaucherFound.get();
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

            List<VaucherModel> vaucherEnviados = new ArrayList<>();
            Date dataAtual = new Date();

            for (VaucherModel vaucher : voucherRepository.findAll()) {
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
            List<VaucherModel> vaucherEnviados;
            Date dataAtual = new Date();

            List<VaucherModel> vouchersValidos = voucherRepository.findAll().stream()
                    .filter(vaucher -> dataAtual.toInstant().isAfter(vaucher.getDataComecoVaucher().toInstant()) && dataAtual.toInstant().isBefore(vaucher.getDataFinalVaucher().toInstant()))
                    .sorted(Comparator.comparingInt(VaucherModel::getTrocado).reversed()).toList();

            vaucherEnviados = vouchersValidos.stream().limit(10).collect(Collectors.toList());

            if (!vaucherEnviados.isEmpty()) {
                return Success.success200(vaucherEnviados);
            }

            return Error.error400("Não foi encontrado nenhum vaucher.");

        } catch (Exception e) {
            return Error.error500(e);
        }
    }

    @GetMapping(path = "/trocar")
    public ResponseEntity<Object> trocarVaucher(@RequestParam(name = "id_usuario") Integer idUsuario, @RequestParam(name = "id_vaucher") Integer idVaucher) {
        try {

            if (idUsuario != null && idVaucher != null) {

                Optional<VaucherModel> vaucherModel = voucherRepository.findById(idVaucher);
                Optional<UsuarioModel> usuarioModel = usuarioRepository.findById(idUsuario);

                if (vaucherModel.isPresent()) {
                    if (usuarioModel.isPresent()) {
                        if (usuarioModel.get().getPontosUsuario() >= vaucherModel.get().getPontosVaucher()) {

                            vaucherModel.get().setTrocado(vaucherModel.get().getTrocado() + 1);
                            usuarioModel.get().setPontosUsuario(usuarioModel.get().getPontosUsuario() - vaucherModel.get().getPontosVaucher());
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

    private static ExtratoModel getExtratoModel(Integer idUsuario, Integer idVaucher, Optional<VaucherModel> vaucherModel) {
        ExtratoModel extratoModel = new ExtratoModel();
        extratoModel.setTituloExtrato("Troca por vaucher");
        extratoModel.setDescricaoExtrato("Troca de pontos realizada - " + vaucherModel.get().getTituloVaucher());
        extratoModel.setEntradaExtrato(false);
        extratoModel.setPontosExtrato(vaucherModel.get().getPontosVaucher());
        extratoModel.setDataExtrato(new Date());
        extratoModel.setIdUsuario(idUsuario);
        extratoModel.setIdVaucher(idVaucher);
        return extratoModel;
    }

}
