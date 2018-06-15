package com.example.ft.entity.rule;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications/{name}/{version}/toggles")
public class RuleController {

    @Autowired
    private RuleService ruleService;

    @ApiOperation(value = "Creates a new toggle rule")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{toggleName}")
    @ResponseBody
    public void create(
            @PathVariable String name,
            @PathVariable String version,
            @PathVariable String toggleName,
            @RequestBody RuleDto dto) {
        ruleService.insertDto(name, version, toggleName, dto);
    }

    @ApiOperation(value = "Deletes a toggle rule")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{toggleName}")
    @ResponseBody
    public void delete(
            @PathVariable String name,
            @PathVariable String version,
            @PathVariable String toggleName) {
        ruleService.delete(name, version, toggleName);
    }

    @ApiOperation(value = "Returns all toggle rules")
    @PreAuthorize("hasAnyRole('ADMIN', 'APPLICATION')")
    @GetMapping
    @ResponseBody
    public List<ComputedRuleDto> index(@PathVariable String name, @PathVariable String version) {
        return ruleService.getDtoAll(name, version);
    }

    @ApiOperation(value = "Returns one toggle rule")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Not Found")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'APPLICATION')")
    @GetMapping("/{toggleName}")
    @ResponseBody
    public ComputedRuleDto read(
            @PathVariable String name,
            @PathVariable String version,
            @PathVariable String toggleName) {
        return ruleService.getDto(name, version, toggleName);
    }

    @ApiOperation(value = "Updates one toggle rule")
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{toggleName}")
    @ResponseBody
    public void update(@PathVariable String name,
                       @PathVariable String version,
                       @PathVariable String toggleName,
                       @RequestBody RuleDto dto) {
        ruleService.updateDto(name, version, toggleName, dto);
    }

}
