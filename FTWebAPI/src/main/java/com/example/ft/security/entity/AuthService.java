package com.example.ft.security.entity;

import com.example.ft.exception.UnauthorizedException;
import com.example.ft.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider tokenProvider;

    public AuthResponseDto auth(AuthRequestDto dto) {
        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            dto.getUsername(),
                            dto.getPassword()));
        } catch (BadCredentialsException e) {
            throw new UnauthorizedException("Bad credentials");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.generateToken(dto.getUsername());
        return new AuthResponseDto(tokenProvider.embedToken(token));
    }

}
