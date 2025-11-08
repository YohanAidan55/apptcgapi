package com.aidan.apptcg.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
@Slf4j
public class JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    private static final String SECRET_KEY = "your-very-long-secret-key-of-at-least-32-bytes-length-1234567890";
    private static final String TOKEN_TYPE_CLAIM = "token_type";
    private static final String ACCESS_TYPE = "ACCESS";
    private static final String EMAIL_TYPE = "EMAIL";

    @Value("${jwt.expiration-ms:604800000}") // 7 days par défaut
    private long jwtExpirationMs;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(String username) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .claim(TOKEN_TYPE_CLAIM, ACCESS_TYPE)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateTokenEmail(String email, long expiredTime) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expiredTime * 60 * 1000);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .claim(TOKEN_TYPE_CLAIM, EMAIL_TYPE)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isAccessTokenValid(String token, String username) {
        try {
            String tokenUsername = extractUsername(token);
            String type = extractTokenType(token);
            return (tokenUsername != null && tokenUsername.equals(username) && ACCESS_TYPE.equals(type) && !isTokenExpired(token));
        } catch (JwtException | IllegalArgumentException ex) {
            log.debug("Invalid JWT access token: {}", ex.getMessage());
            return false;
        }
    }

    public boolean isEmailTokenValid(String token, String email) {
        try {
            String tokenEmail = extractUsername(token);
            String type = extractTokenType(token);
            return (tokenEmail != null && tokenEmail.equals(email) && EMAIL_TYPE.equals(type) && !isTokenExpired(token));
        } catch (JwtException | IllegalArgumentException ex) {
            log.debug("Invalid JWT email token: {}", ex.getMessage());
            return false;
        }
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private String extractTokenType(String token) {
        return extractClaim(token, claims -> claims.get(TOKEN_TYPE_CLAIM, String.class));
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = parseClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenExpired(String token) {
        final Date expiration = extractExpiration(token);
        return expiration.before(new Date());
    }
}
