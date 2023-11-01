package com.api.soamer.controller;

import com.api.soamer.responses.Success;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.api.soamer.responses.Error;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/v1/soamer/usuario/pdf")
public class FileUploadController {


    private final String uploadDirectory = "doc/pdf/venda/";
    @PostMapping(path = "/upload")
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) {
        try {

            if (file != null && !file.isEmpty()) {

                String fileName = file.getOriginalFilename();
                File directory = new File(uploadDirectory);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                assert fileName != null;
                File newFile = new File(directory, fileName);

                try (FileOutputStream fos = new FileOutputStream(newFile)) {
                    fos.write(file.getBytes());
                }

                return Success.success200("ok");
            } else {
                return Error.error400("Arquivo não enviado ou vazio.");
            }
        } catch (IOException e) {
            return Error.error500(e);
        }
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<Object> downloadFile(@PathVariable String fileName) {
        try {
            Path filePath = Paths.get(uploadDirectory, fileName);
            var resource = new ByteArrayResource(Files.readAllBytes(filePath));
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName).body(resource);
        } catch (IOException e) {
            return Error.error500(e);
        }
    }
}
