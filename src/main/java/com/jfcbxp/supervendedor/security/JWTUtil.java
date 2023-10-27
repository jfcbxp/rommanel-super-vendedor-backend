package com.jfcbxp.supervendedor.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTUtil {

    @Value("${springbootwebfluxjjwt.jjwt.secret}")
    private String secret;

    @Value("${springbootwebfluxjjwt.jjwt.expiration}")
    private String expirationTime;

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
        }catch ( ExpiredJwtException ex ) {
            return ex.getClaims();

        }
    }

    public String getUsernameFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public Date getExpirationDateFromToken(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(UserSecurity userSecurity) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", userSecurity.getRoles());
        claims.put("fullName", userSecurity.getFullName());
        claims.put("secret", userSecurity.getPassword());
        return doGenerateToken(claims, userSecurity.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String username) {
        var expirationTimeLong = Long.parseLong(expirationTime);
        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + expirationTimeLong * 1000);

        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(createdDate)
                .expiration(expirationDate)
                .signWith(secretKey)
                .compact();
    }

    public String updateToken(String token) {
        return doGenerateToken(getAllClaimsFromToken(token),getAllClaimsFromToken(token).getSubject());
    }

    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

}
