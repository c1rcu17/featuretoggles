package com.example.ft.entity.toggle;

import com.example.ft.exception.ConflictException;
import com.example.ft.exception.ControllerExceptionHandler;
import com.example.ft.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ToggleService {

    @Autowired
    private ToggleRepository repository;

    @Autowired
    private ToggleMapper mapper;

    public void delete(String name) {
        repository.delete(getByName(name));
    }

    public List<Toggle> getAll() {
        List<Toggle> toggles = new ArrayList<>();
        repository.findAll().forEach(toggles::add);
        return toggles;
    }

    public Toggle getByName(String name) {
        Optional<Toggle> toggle = repository.findByName(name);

        if (toggle.isPresent()) {
            return toggle.get();
        }

        throw new NotFoundException("Toggle not found");
    }

    public ToggleDto getDto(String name) {
        return mapper.toDto(getByName(name));
    }

    public List<ToggleDto> getDtoAll() {
        return mapper.toDtoList(getAll());
    }

    public void insert(Toggle toggle) {
        try {
            repository.save(toggle);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Toggle exists");
        } catch (TransactionSystemException e) {
            ControllerExceptionHandler.handle(e);
        }
    }

    public void insertDto(ToggleDto dto) {
        insert(mapper.fromDto(dto));
    }

    public void updateDto(String name, ToggleDto dto) {
        Toggle toggle = getByName(name);
        mapper.copy(dto, toggle);
        insert(toggle);
    }

}
