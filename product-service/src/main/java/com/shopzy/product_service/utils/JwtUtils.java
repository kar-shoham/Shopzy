package com.shopzy.product_service.utils;

import com.shopzy.product_service.enums.UserType;
import com.shopzy.user_service.dto.JwtResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
public class JwtUtils
{
    @Value("${jwt.secret-key}")
    private String secretKey;

    private SecretKey key;

    @PostConstruct
    public void init()
    {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public JwtResponse validateJwt(String jwt)
    {
        Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt).getPayload();
        return JwtResponse.builder()
                .id(Long.parseLong(claims.getSubject()))
                .role(UserType.valueOf(claims.get("role").toString()))
                .build();
    }
}
