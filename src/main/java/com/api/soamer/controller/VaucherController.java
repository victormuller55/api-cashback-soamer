package com.api.soamer.controller;

import com.api.soamer.model.VaucherModel;
import com.api.soamer.repository.VaucherRepository;
import com.api.soamer.responses.Error;
import com.api.soamer.responses.Success;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.api.soamer.util.Formatters.formatDDMMYYYYHHMMToDate;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/v1/soamer/vaucher")
public class VaucherController {
    @Autowired
    VaucherRepository vaucherRepository;

    public VaucherController(VaucherRepository vaucherRepository) {
        this.vaucherRepository = vaucherRepository;
    }

    @PostMapping
    public ResponseEntity<Object> createVaucher(@RequestBody VaucherModel vaucherModel) {
        try {

            vaucherModel.setDataComecoVaucher(formatDDMMYYYYHHMMToDate(vaucherModel.getDataComecoVaucher().toString()));
            vaucherModel.setDataFinalVaucher(formatDDMMYYYYHHMMToDate(vaucherModel.getDataFinalVaucher().toString()));

            if (!vaucherModel.getTituloVaucher().isEmpty()) {
                if (!vaucherModel.getInfoVaucher().isEmpty()) {
                    if (!vaucherModel.getPontosCheioVaucher().equals(0)) {

                        vaucherModel.setPontosVaucher(vaucherModel.getPontosCheioVaucher() - vaucherModel.getDescontoVaucher());
                        vaucherRepository.save(vaucherModel);

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

    @GetMapping
    public ResponseEntity<Object> getVaucherList() {
        try {

            List<VaucherModel> vaucherEnviados = new ArrayList<>();
            Date dataAtual = new Date();

            for (VaucherModel vaucher : vaucherRepository.findAll()) {
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
            Optional<VaucherModel> vaucherFound = vaucherRepository.findById(id);

            if (vaucherFound.isPresent()) {

                VaucherModel vaucher = vaucherFound.get();
                vaucher.setDescontoVaucher(desconto);

                if(desconto > 0) {
                    vaucher.setPontosVaucher(vaucher.getPontosCheioVaucher() - desconto);
                    vaucherRepository.save(vaucher);

                    return Success.success200(vaucher);
                }

                vaucher.setPontosVaucher(vaucher.getPontosCheioVaucher());
                vaucherRepository.save(vaucher);

                return Success.success200(vaucher);
            }

            return Error.error400("Nenhum vaucher com o ID: " + id);

        } catch (Exception e) {
            return Error.error500(e);
        }
    }
}
