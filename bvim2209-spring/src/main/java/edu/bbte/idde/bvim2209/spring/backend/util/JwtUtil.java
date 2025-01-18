package edu.bbte.idde.bvim2209.spring.backend.util;

import edu.bbte.idde.bvim2209.spring.exceptions.AuthenticationException;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    private static final String secret =
            "a6RTEew6dfwFDG7dv732vwveuGRTGfw23742dby32r42fvwefuef3ftv3SADVU32DEEHWFVU"
                    + "34YFV43RVF34VF3UFVFAJSHHbs2y8r723fewfvet3fv4fdsvgfru43byid243yfu4vevferfv";

    public String generateToken(String username, String fullname, String role, Long age) {
        return Jwts.builder()
                .setSubject(username + '|' + fullname + '|' + role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + age))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String extractUsername(String jwtToken) {
        try {
            Jws<Claims> parsedToken = parseToken(jwtToken);
            return parsedToken.getBody().getSubject().split("\\|")[0];
        } catch (JwtException exception) {
            throw new AuthenticationException("Invalid JWT token");
        }
    }

    public Jws<Claims> parseToken(String token) {
        return Jwts.parser().setSigningKey(secret).build().parseClaimsJws(token);
    }
}
