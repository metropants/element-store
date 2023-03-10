package xyz.metropants.element.service.impl;

import org.springframework.stereotype.Service;
import xyz.metropants.element.modal.Upload;
import xyz.metropants.element.repository.UploadRepository;
import xyz.metropants.element.service.UploadService;

import java.util.List;
import java.util.Optional;

@Service
public class UploadServiceImpl implements UploadService {

    private final UploadRepository repository;

    public UploadServiceImpl(UploadRepository repository) {
        this.repository = repository;
    }

    @Override
    public Upload save(Upload upload) {
        return repository.save(upload);
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
    public boolean exists(String id) {
        return repository.existsById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return repository.existsByName(name);
    }

    @Override
    public Optional<Upload> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Upload> findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public List<Upload> findAll() {
        return repository.findAll();
    }

}
