package edu.bbte.idde.bvim2209.spring.web.util;

import io.jsonwebtoken.*;

import java.util.Date;

public class JwtUtil {
    private static final String secret =
            "a6RTEew6dfwFDG7dv732vwveuGRTGfw23742dby32r42fvwefuef3ftv3SADVU32DEEHWFVU"
                    + "34YFV43RVF34VF3UFVFAJSHHbs2y8r723fewfvet3fv4fdsvgfru43byid243yfu4vevferfv";

    public String generateToken(String username, String fullname) {
        return Jwts.builder()
                .setSubject(username + '-' + fullname)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Jws<Claims> parseToken(String token) {
        return Jwts.parser().setSigningKey(secret).build().parseClaimsJws(token);
    }
}
