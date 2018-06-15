package com.example.ft.entity.application;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ApplicationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rules", ignore = true)
    void copy(ApplicationDto dto, @MappingTarget Application application);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rules", ignore = true)
    Application fromDto(ApplicationDto dto);

    ApplicationDto toDto(Application application);

    List<ApplicationDto> toDtoList(List<Application> all);

}
