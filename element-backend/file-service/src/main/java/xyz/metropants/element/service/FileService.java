package xyz.metropants.element.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
import xyz.metropants.element.modal.Upload;

import java.util.concurrent.CompletableFuture;

public interface FileService {

    CompletableFuture<Upload> upload(MultipartFile file);

    CompletableFuture<Void> download(String name, HttpServletResponse response);

}
