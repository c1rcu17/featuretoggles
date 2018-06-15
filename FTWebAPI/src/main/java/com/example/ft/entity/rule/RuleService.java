package com.example.ft.entity.rule;

import com.example.ft.entity.application.ApplicationService;
import com.example.ft.entity.toggle.ToggleService;
import com.example.ft.exception.ConflictException;
import com.example.ft.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RuleService {

    @Autowired
    private ApplicationService applications;

    @Autowired
    private ToggleService toggles;

    @Autowired
    private RuleRepository repository;

    @Autowired
    private RuleMapper mapper;

    @Transient
    public void delete(String name, String version, String toggleName) {
        repository.delete(get(name, version, toggleName));
    }

    private Rule get(String name, String version, String toggleName) {
        Optional<Rule> rule = repository.getByApplicationNameVersionAndToggleName(name, version, toggleName);

        if (rule.isPresent()) {
            return rule.get();
        }

        throw new NotFoundException("Rule not found");
    }

    public List<Rule> getAll(String name, String version) {
        return repository.getAllByApplicationNameVersion(name, version);
    }

    public ComputedRuleDto getDto(String name, String version, String toggleName) {
        return mapper.toComputedDto(get(name, version, toggleName));
    }

    public List<ComputedRuleDto> getDtoAll(String name, String version) {
        List<ComputedRuleDto> rules = new ArrayList<>();
        getAll(name, version).forEach(r -> rules.add(mapper.toComputedDto(r)));
        return rules;
    }

    public void insert(Rule rule) {
        try {
            repository.save(rule);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Rule exists");
        }
    }

    @Transient
    public void insertDto(String name, String version, String toggleName, RuleDto dto) {
        Rule rule = mapper.fromDto(dto);
        rule.setApplication(applications.get(name, version));
        rule.setToggle(toggles.getByName(toggleName));
        insert(rule);
    }

    @Transient
    public void updateDto(String name, String version, String toggleName, RuleDto dto) {
        Rule rule = get(name, version, toggleName);
        mapper.copy(dto, rule);
        insert(rule);
    }
}
