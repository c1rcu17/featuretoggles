package com.example.ft.entity.toggle;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ToggleMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rules", ignore = true)
    void copy(ToggleDto dto, @MappingTarget Toggle toggle);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rules", ignore = true)
    Toggle fromDto(ToggleDto dto);

    ToggleDto toDto(Toggle toggle);

    List<ToggleDto> toDtoList(List<Toggle> all);

}
