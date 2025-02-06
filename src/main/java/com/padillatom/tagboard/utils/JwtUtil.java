package com.padillatom.tagboard.utils;

import com.padillatom.tagboard.config.AppConstants;
import com.padillatom.tagboard.model.UserEntity;
import com.padillatom.tagboard.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class JwtUtil {

    public final UserRepository userRepository;

    SecretKey secretKey = Keys.hmacShaKeyFor(AppConstants.SECRET.getBytes(StandardCharsets.UTF_8));

    public String generate(String username) {
        return Jwts.builder()
                .subject(username)
                .issuer(AppConstants.ISSUER)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + AppConstants.EXPIRATION_TIME))
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

    public String getContextUsernameWithJWT() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserEntity userEntity = userRepository.findByUsername(((User) authentication.getPrincipal()).getUsername())
                .orElseThrow(() -> new NoSuchElementException("No se encuentra el usuario"));

        return userEntity.getUsername();
    }
}
