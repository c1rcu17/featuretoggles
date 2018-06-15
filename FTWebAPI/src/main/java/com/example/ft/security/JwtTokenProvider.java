package com.example.ft.security;

import com.example.ft.exception.UnauthorizedException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtTokenProvider {

    @Value("${ft.security.jwt_secret}")
    private String jwtSecret;

    @Value("${ft.security.jwt_expiration_ms}")
    private long jwtExpirationInMs;

    @Value("${ft.security.jwt_header}")
    private String jwtHeader;

    private String tokenPrefix = "Bearer ";

    public String embedToken(String token) {
        return tokenPrefix + token;
    }

    public String extractFromRequest(HttpServletRequest httpServletRequest) {
        String header = httpServletRequest.getHeader(jwtHeader);

        if (header == null || !header.startsWith(tokenPrefix)) {
            return null;
        }

        return header.replaceFirst(tokenPrefix, "");
    }

    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(username)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public Claims getClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SignatureException e) {
            throw new UnauthorizedException("Invalid JWT signature");
        } catch (MalformedJwtException e) {
            throw new UnauthorizedException("Invalid JWT token");
        } catch (ExpiredJwtException e) {
            throw new UnauthorizedException("Expired JWT token");
        } catch (UnsupportedJwtException e) {
            throw new UnauthorizedException("Unsupported JWT token");
        } catch (IllegalArgumentException e) {
            throw new UnauthorizedException("JWT claims string is empty.");
        } catch (Exception e) {
            throw new UnauthorizedException(e.getMessage());
        }
    }

}
