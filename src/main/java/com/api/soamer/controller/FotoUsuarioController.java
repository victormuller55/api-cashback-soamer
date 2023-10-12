package com.api.soamer.controller;

import com.api.soamer.model.FotoUsuarioModel;
import com.api.soamer.model.UsuarioModel;
import com.api.soamer.repository.FotoUsuarioRepository;
import com.api.soamer.repository.UsuarioRepository;
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

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/v1/soamer/usuario/foto")
public class FotoUsuarioController {
    @Autowired
    private final FotoUsuarioRepository fotoUsuarioRepository;
    @Autowired
    private final UsuarioRepository usuarioRepository;

    public FotoUsuarioController(FotoUsuarioRepository fotoUsuarioRepository, UsuarioRepository usuarioRepository) {
        this.fotoUsuarioRepository = fotoUsuarioRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Value("${upload.path}")
    private String uploadPath;

    @PostMapping
    public ResponseEntity<Object> uploadFoto(@RequestParam("file") MultipartFile file, @RequestParam(name = "id_usuario") Integer idUsuario) throws IOException {
        try {

            if (!file.isEmpty()) {

                Optional<UsuarioModel> usuarioModel = usuarioRepository.findById(idUsuario);

                if(usuarioModel.isPresent()) {

                    String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                    Path filePath = Paths.get(uploadPath, fileName);
                    Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                    FotoUsuarioModel foto = new FotoUsuarioModel();

                    foto.setFilePath(fileName);
                    foto.setIdUsuario(idUsuario);

                    usuarioModel.get().setImagePath(fileName);

                    fotoUsuarioRepository.save(foto);
                    usuarioRepository.save(usuarioModel.get());

                    return Success.success200("Ok");
               }

                return Error.error400("Usuario não encontrado");
            }

            return Error.error400("O arquivo está vazio");

        } catch (Exception e) {
            return Error.error500(e);
        }
    }

    @GetMapping()
    public ResponseEntity<Object> getFoto(@RequestParam(name = "id_usuario") Integer idUsuario) {
        try {

            Optional<UsuarioModel> userOptional = usuarioRepository.findById(idUsuario);

            if (userOptional.isPresent()) {

                UsuarioModel user = userOptional.get();
                String path = user.getImagePath();

                if (path != null) {
                    Path filePath = Paths.get(uploadPath, path);
                    UrlResource resource = new UrlResource(filePath.toUri());
                    if (resource.exists() && resource.isReadable()) {
                        HttpHeaders headers = new HttpHeaders();
                        headers.setContentType(MediaType.IMAGE_PNG);

                        return ResponseEntity.ok().headers(headers).body(resource);
                    } else {
                        return Error.error400("Resorse não encontrada");
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


}
