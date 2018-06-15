package com.example.ft.entity.application;

import com.example.ft.exception.ConflictException;
import com.example.ft.exception.ControllerExceptionHandler;
import com.example.ft.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository repository;

    @Autowired
    private ApplicationMapper mapper;

    @Transient
    public void delete(String name, String version) {
        repository.delete(get(name, version));
    }

    public Application get(String name, String version) {
        Optional<Application> application = repository.getByNameAndVersion(name, version);

        if (application.isPresent()) {
            return application.get();
        }

        throw new NotFoundException("Application not found");
    }

    public List<Application> getAll() {
        List<Application> applications = new ArrayList<>();
        repository.findAll().forEach(applications::add);
        return applications;
    }

    public ApplicationDto getDto(String name, String version) {
        return mapper.toDto(get(name, version));
    }

    public List<ApplicationDto> getDtoAll() {
        return mapper.toDtoList(getAll());
    }

    public void insert(Application application) {
        try {
            repository.save(application);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Application/version exists");
        } catch (TransactionSystemException e) {
            ControllerExceptionHandler.handle(e);
        }
    }

    public void insertDto(ApplicationDto dto) {
        insert(mapper.fromDto(dto));
    }

    public void updateDto(String name, String version, ApplicationDto dto) {
        Application application = get(name, version);
        mapper.copy(dto, application);
        insert(application);
    }

}