package com.example.performance_management.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.Key;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;

@Service
@Getter
public class JwtTokenProvider {

    private static final String jwtSecret = "c17be0b65f0824272ace47e24bc0503275024e1d7b63763f4c8ab79a53ee2ce3";

    private static SecretKey getSecretKey() {
        byte[] decodedKey = Base64.getDecoder().decode(jwtSecret);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "HmacSHA256");
    }

    public static final long JWT_EXPIRATION = 1000 * 60 * 5;
    public static final long REFRESH_EXPIRATION = 1000 * 60 * 50;
    private static final Key key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtSecret));

    public String generateToken(Authentication authentication) {
        return generateToken(authentication.getName());
    }

    public String generateToken(String name) {
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + JWT_EXPIRATION);

        String token = Jwts.builder()
                .subject(name)
                .issuedAt(new Date())
                .expiration(expireDate)
                .signWith(getSecretKey())
                .compact();

        System.out.println("New token :" + token);
        return token;
    }


    public String getUsernameFromJWTToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    public boolean validateJwtToken(HttpServletResponse response, String token) throws IOException {
        try {
            Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception ex) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }
        return true;
    }

}