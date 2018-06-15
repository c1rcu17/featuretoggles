package com.example.ft.entity.rule;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface RuleMapper {

    @Mapping(target = "pk", ignore = true)
    @Mapping(target = "toggle", ignore = true)
    @Mapping(target = "application", ignore = true)
    void copy(RuleDto dto, @MappingTarget Rule rule);

    @Mapping(target = "pk", ignore = true)
    @Mapping(target = "toggle", ignore = true)
    @Mapping(target = "application", ignore = true)
    Rule fromDto(RuleDto dto);

    default ComputedRuleDto toComputedDto(Rule r) {
        return ComputedRuleDto.builder()
                .name(r.getToggle().getName())
                .enabled(r.getToggle().getEnabled())
                .trueValue(r.getTrueValue())
                .falseValue(r.getFalseValue())
                .finalStatus(r.getFinalStatus())
                .build();
    }

}
