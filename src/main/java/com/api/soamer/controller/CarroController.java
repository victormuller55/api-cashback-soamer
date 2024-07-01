package com.api.soamer.controller;

import com.api.soamer.model.concessionaria.CarroModel;
import com.api.soamer.model.usuario.UsuarioModel;
import com.api.soamer.repository.CarroRepository;
import com.api.soamer.repository.ConcessionariaRepository;
import com.api.soamer.responses.Error;
import com.api.soamer.responses.Success;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/v1/soamer/concessionaria/carro")
public class CarroController {

    CarroRepository carroRepository;
    ConcessionariaRepository concessionariaRepository;

    public CarroController(CarroRepository carroRepository, ConcessionariaRepository concessionariaRepository) {
        this.carroRepository = carroRepository;
        this.concessionariaRepository = concessionariaRepository;
    }

    @PostMapping
    public ResponseEntity<Object> cadastrarCarro(@RequestBody CarroModel carroModel) {
        try {

            if (concessionariaRepository.existsById(carroModel.getIdConcessionaria())) {
                carroRepository.save(carroModel);
                return Success.success200(carroModel);
            }

            return Error.error400("Concessionaria não encontrada");

        } catch (Exception e) {
            return Error.error500(e);
        }
    }

    @GetMapping
    public ResponseEntity<Object> todasCarros() {
        try {
            return Success.success200(carroRepository.findAll());
        } catch (Exception e) {
            return Error.error500(e);
        }
    }

    @Value("${upload.path.carro}")
    private String uploadPath;

    @PostMapping(path="/foto")
    public ResponseEntity<Object> uploadFotoCarro(@RequestParam("file") MultipartFile file, @RequestParam(name = "id_carro") Integer idCarro) {
        try {
            if (!file.isEmpty()) {
                Optional<CarroModel> carroModelOptional = carroRepository.findById(idCarro);

                if (carroModelOptional.isPresent()) {

                    CarroModel carroModel = carroModelOptional.get();

                    if (carroModel.getImagePath() != null) {
                        if(!carroModel.getImagePath().equals("default_image.jpg")) {
                            Path previousImagePath = Paths.get(uploadPath, carroModel.getImagePath());
                            Files.delete(previousImagePath);
                        }
                    }

                    String fileName = "image" + "_" + idCarro + "_" + file.getOriginalFilename();
                    Path filePath = Paths.get(uploadPath, fileName);
                    Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                    carroModel.setImagePath(fileName);
                    carroRepository.save(carroModel);

                    return Success.success200("Ok");
                } else {
                    return Error.error400("Usuário não encontrado");
                }
            } else {
                return Error.error400("O arquivo está vazio");
            }
        } catch (Exception e) {
            return Error.error500(e);
        }
    }

}
