package com.example.ft.entity.toggle;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/toggles")
public class ToggleController {

    @Autowired
    private ToggleService toggleService;

    @ApiOperation(value = "Creates a new toggle")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public void create(@RequestBody ToggleDto dto) {
        toggleService.insertDto(dto);
    }

    @ApiOperation(value = "Deletes a toggle")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{name}")
    @ResponseBody
    public void delete(@PathVariable String name) {
        toggleService.delete(name);
    }

    @ApiOperation(value = "Returns all toggles")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    @ResponseBody
    public List<ToggleDto> index() {
        return toggleService.getDtoAll();
    }

    @ApiOperation(value = "Returns one toggle")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Not Found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{name}")
    @ResponseBody
    public ToggleDto read(@PathVariable String name) {
        return toggleService.getDto(name);
    }

    @ApiOperation(value = "Updates one toggle")
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{name}")
    @ResponseBody
    public void update(@PathVariable String name, @RequestBody ToggleDto dto) {
        toggleService.updateDto(name, dto);
    }

}
