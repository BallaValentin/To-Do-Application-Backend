package edu.bbte.idde.bvim2209.spring.backend.util;

import edu.bbte.idde.bvim2209.spring.exceptions.AuthenticationException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
@Slf4j
public class JwtUtil {
    @Value("${jwt.secret1}")
    private static String secret1;

    @Value("${jwt.secret2}")
    private static String secret2;

    public String generateAccessToken(String subject) {
        return Jwts.builder()
                .subject(subject)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 600000L))
                .signWith(SignatureAlgorithm.HS512, secret1)
                .compact();
    }

    public String generateRefreshToken(String subject) {
        return Jwts.builder()
                .subject(subject)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 30L))
                .signWith(SignatureAlgorithm.HS512, secret2)
                .compact();
    }

    public String extractAccessToken(String jwtToken) {
        try {
            Jws<Claims> parsedToken = parseAccessToken(jwtToken);
            return parsedToken.getPayload().getSubject();
        } catch (JwtException exception) {
            log.error(exception.getMessage());
            throw new AuthenticationException("Invalid JWT token");
        }
    }

    public String extractRefreshToken(String jwtToken) {
        try {
            Jws<Claims> parsedToken = parseRefreshToken(jwtToken);
            return parsedToken.getPayload().getSubject();
        } catch (JwtException exception) {
            throw new AuthenticationException("Invalid JWT token");
        }
    }

    public Jws<Claims> parseAccessToken(String token) {
        return Jwts.parser().setSigningKey(secret1).build().parseSignedClaims(token);
    }

    public Jws<Claims> parseRefreshToken(String token) {
        return Jwts.parser().setSigningKey(secret2).build().parseSignedClaims(token);
    }

    public Boolean validateRefreshToken(String token) {
        try {
            parseRefreshToken(token);
            return true;
        } catch (JwtException exception) {
            return false;
        }
    }
}
