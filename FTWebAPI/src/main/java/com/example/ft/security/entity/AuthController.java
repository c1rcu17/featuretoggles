package com.example.ft.security.entity;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @ApiOperation(value = "Request a new authentication token")
    @PostMapping
    @ResponseBody
    public AuthResponseDto auth(@Valid @RequestBody AuthRequestDto dto) {
        return authService.auth(dto);
    }

}
