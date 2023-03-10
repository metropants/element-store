package xyz.metropants.element.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.metropants.element.modal.Upload;
import xyz.metropants.element.service.FileService;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/files")
public class FileController {

    private final FileService service;

    public FileController(FileService service) {
        this.service = service;
    }

    @GetMapping("/{name}")
    public CompletableFuture<Void> download(@PathVariable String name, HttpServletResponse response) {
        return service.download(name, response);
    }

    @PostMapping("/upload")
    public CompletableFuture<Upload> upload(@RequestParam("file") MultipartFile file) {
        return service.upload(file);
    }

}
