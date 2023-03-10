package xyz.metropants.element.service.impl;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.core.async.AsyncResponseTransformer;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import xyz.metropants.element.modal.Upload;
import xyz.metropants.element.payload.UploadNotification;
import xyz.metropants.element.service.FileService;
import xyz.metropants.element.service.UploadService;
import xyz.metropants.element.util.FileNameUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Service
public class FileServiceImpl implements FileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileService.class);

    private final S3AsyncClient client;
    private final UploadService service;
    private final StreamBridge bridge;

    @Value("${aws.bucket}")
    private String bucket;

    public FileServiceImpl(S3AsyncClient client, UploadService service, StreamBridge bridge) {
        this.client = client;
        this.service = service;
        this.bridge = bridge;
    }

    private void sendNotification(Object payload) {
        bridge.send("notificationSupplier-out-0", payload);
    }

    @Override
    public CompletableFuture<Upload> upload(MultipartFile file) {
        if (file.getOriginalFilename() == null) {
            throw new IllegalArgumentException("File name cannot be null.");
        }

        final String name = FileNameUtils.formatFileName(file.getOriginalFilename());
        if (service.existsByName(name)) {
            throw new IllegalArgumentException("File with name %s already exists.".formatted(name));
        }

        final PutObjectRequest request = PutObjectRequest.builder()
                .key(name)
                .contentType(file.getContentType())
                .bucket(bucket)
                .build();

        try {
            return client.putObject(request, AsyncRequestBody.fromBytes(file.getBytes()))
                    .whenComplete((response, error) -> {
                        if (error != null) {
                            LOGGER.error("Error while uploading file.", error);
                        }
                    }).thenApply(response -> {
                        Upload upload = service.save(new Upload(name, file.getContentType()));
                        LOGGER.info("File {} uploaded successfully.", name);

                        try {
                            sendNotification(new UploadNotification(name, file.getBytes()));
                            LOGGER.info("Sent upload notification for file {}.", name);
                        } catch (IOException e) {
                            LOGGER.error("Error while sending upload notification.", e);
                            throw new RuntimeException(e);
                        }
                        return upload;
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CompletableFuture<Void> download(String name, HttpServletResponse response) {
        final Upload file = service.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("File with name %s not found.".formatted(name)));

        return client.getObject(builder -> builder.bucket(bucket).key(file.getName()).build(), AsyncResponseTransformer.toBytes())
                .whenComplete((bytes, error) -> {
                    if (error != null) {
                        LOGGER.error("Error while downloading file.", error);
                        throw new RuntimeException(error);
                    }
                }).thenAcceptAsync(bytes -> {
                    try {
                        response.setContentType(file.getContentType());
                        try (var stream = new ByteArrayInputStream(bytes.asByteArray())) {
                            IOUtils.copy(stream, response.getOutputStream());
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

}
