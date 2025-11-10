package com.shopzy.user_service.utils;

import com.shopzy.user_service.entity.ShopzyUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
public class JwtUtils
{
    @Value("${jwt.secret-key}")
    private String secretKey;

    public String generateJwt(ShopzyUser user)
    {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder().subject("Joe").signWith(key).compact();
    }

//    public ShopzyUser validateJwt(String jwt)
//    {
//        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
//        Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt);
//    }
}
