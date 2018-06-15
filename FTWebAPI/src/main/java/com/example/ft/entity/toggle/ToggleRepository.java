package com.example.ft.entity.toggle;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ToggleRepository extends CrudRepository<Toggle, Long> {
    Optional<Toggle> findByName(String name);
}
