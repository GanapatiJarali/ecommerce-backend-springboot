package org.ganapati.project.ecommerce.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.ganapati.project.ecommerce.entity.Roles;
import org.ganapati.project.ecommerce.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtUtil {

    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.expire}")
    private long expireTime;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
    }

    public String generateToken(User user, List<Roles> rolesList) {
        log.info("generateToken user: Roles: {}", rolesList.stream().map(Roles::getRole).collect(Collectors.toList()));
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", user.getEmail());
        claims.put("mobileNo", user.getMobileNo());
        claims.put("roles", rolesList.stream().map(Roles::getRole).collect(Collectors.toList()));
        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("ecommerce-auth-service")
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token) {
        Date expiration = extractAllClaims(token).getExpiration();
        Date now = new Date();
        return now.after(expiration);
    }


}
