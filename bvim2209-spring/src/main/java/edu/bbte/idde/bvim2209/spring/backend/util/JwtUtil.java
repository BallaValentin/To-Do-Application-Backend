package edu.bbte.idde.bvim2209.spring.backend.util;

import edu.bbte.idde.bvim2209.spring.exceptions.AuthenticationException;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

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
                .setSubject(username + '|' + fullname + '|' + role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 60000L))
                .signWith(SignatureAlgorithm.HS512, secret1)
                .compact();
    }

    public String generateRefreshToken(String username, String fullname, String role) {
        return Jwts.builder()
                .setSubject(username + '|' + fullname + '|' + role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 30L))
                .signWith(SignatureAlgorithm.HS512, secret2)
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
        return Jwts.parser().setSigningKey(secret1).build().parseClaimsJws(token);
    }

    public Jws<Claims> parseRefreshToken(String token) {
        return Jwts.parser().setSigningKey(secret2).build().parseClaimsJws(token);
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
