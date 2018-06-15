package com.example.ft.entity.application;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationRepository extends CrudRepository<Application, Long> {

    Optional<Application> findByUsername(String username);

    Optional<Application> getByNameAndVersion(String name, String version);

}
