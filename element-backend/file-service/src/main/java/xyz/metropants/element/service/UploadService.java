package xyz.metropants.element.service;

import xyz.metropants.element.modal.Upload;

import java.util.List;
import java.util.Optional;

public interface UploadService {

    Upload save(Upload upload);

    void delete(String id);

    boolean exists(String id);

    boolean existsByName(String name);

    Optional<Upload> findById(String id);

    Optional<Upload> findByName(String name);

    List<Upload> findAll();

}
