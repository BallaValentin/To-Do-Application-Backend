package edu.bbte.idde.bvim2209.spring.backend.util;

import edu.bbte.idde.bvim2209.spring.exceptions.AuthenticationException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {
    private static final String secret1 =
            "a6RTEew6dfwFDG7dv732vwveuGRTGfw23742dby32r42fvwefuef3ftv3SADVU32DEEHWFVU"
                    + "34YFV43RVF34VF3UFVFAJSHHbs2y8r723fewfvet3fv4fdsvgfru43byid243yfu4vevferfv";

    private static final String secret2 =
            "dsjnvjvndSNADJBDADB1243724GEHBFqewqef4sgwv7DWGqXGEWFWVHSB32YR23ndewbufbw"
                    + "A27rasdhsadvVWVDGWce23rbcewwbhbDW7238CBSHCDjsdcsjbi23bfqhkfbefwgwBDWYFQhd";

    public String generateAccessToken(String username, String fullname, String role) {
        return Jwts.builder()
                .subject(username + '|' + fullname + '|' + role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 600000L))
                .signWith(Keys.hmacShaKeyFor(secret1.getBytes()))
                .compact();
    }

    public String generateRefreshToken(String username, String fullname, String role) {
        return Jwts.builder()
                .subject(username + '|' + fullname + '|' + role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 30L))
                .signWith(Keys.hmacShaKeyFor(secret2.getBytes()))
                .compact();
    }

    public String extractUsername(String jwtToken) {
        try {
            Jws<Claims> parsedToken = parseAccessToken(jwtToken);
            return parsedToken.getPayload().getSubject();
        } catch (JwtException exception) {
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
        SecretKey key = Keys.hmacShaKeyFor(secret1.getBytes(StandardCharsets.UTF_8));
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
    }

    public Jws<Claims> parseRefreshToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(secret2.getBytes(StandardCharsets.UTF_8));
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
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
