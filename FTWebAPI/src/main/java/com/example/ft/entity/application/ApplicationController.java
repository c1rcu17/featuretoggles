package com.example.ft.entity.application;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @ApiOperation(value = "Creates a new application")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public void create(@RequestBody ApplicationDto dto) {
        applicationService.insertDto(dto);
    }

    @ApiOperation(value = "Deletes an application")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{name}/{version}")
    @ResponseBody
    public void delete(@PathVariable String name, @PathVariable String version) {
        applicationService.delete(name, version);
    }

    @ApiOperation(value = "Returns all applications")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    @ResponseBody
    public List<ApplicationDto> index() {
        return applicationService.getDtoAll();
    }

    @ApiOperation(value = "Returns one application")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Not Found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{name}/{version}")
    @ResponseBody
    public ApplicationDto read(@PathVariable String name, @PathVariable String version) {
        return applicationService.getDto(name, version);
    }

    @ApiOperation(value = "Updates one application")
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{name}/{version}")
    @ResponseBody
    public void update(@PathVariable String name, @PathVariable String version, @RequestBody ApplicationDto dto) {
        applicationService.updateDto(name, version, dto);
    }

}
