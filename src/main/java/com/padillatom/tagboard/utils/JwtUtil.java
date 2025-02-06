package com.padillatom.tagboard.utils;

import com.padillatom.tagboard.config.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtUtil {

    SecretKey secretKey = Keys.hmacShaKeyFor(SecurityConstants.SECRET.getBytes(StandardCharsets.UTF_8));

    public String generate(String username) {
        return Jwts.builder()
                .subject(username)
                .issuer(SecurityConstants.ISSUER)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .signWith(secretKey)
                .compact();
    }

    public boolean validate(String token) {
        return (getUsername(token) != null && isExpired(token));
    }

    public String getUsername(String token) {
        Claims claims = getClaims(token);
        return claims.getSubject();
    }

    public boolean isExpired(String token) {
        Claims claims = getClaims(token);
        return claims.getExpiration().after(new Date(System.currentTimeMillis()));
    }

    private Claims getClaims(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
    }
}
