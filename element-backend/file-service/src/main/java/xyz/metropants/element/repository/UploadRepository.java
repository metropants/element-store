package xyz.metropants.element.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import xyz.metropants.element.modal.Upload;

import java.util.Optional;

@Repository
public interface UploadRepository extends MongoRepository<Upload, String> {

    boolean existsByName(String name);

    Optional<Upload> findByName(String name);

}
