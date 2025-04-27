package com.datajoy.admin_builder.apibuilder.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

public class JwtToken {
    private final Key key;

    public JwtToken(String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(AuthenticatedUser user, long expireTime) {
        Claims claims = Jwts.claims();
        claims.put("userId", user.getUserId());
        claims.put("userName", user.getUserName());

        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime tokenValidity = now.plusSeconds(expireTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now.toInstant()))
                .setExpiration(Date.from(tokenValidity.toInstant()))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public AuthenticatedUser parseToken(String token) {
        Claims claims = parseClaims(token);

        return AuthenticatedUser.builder()
                .userId((Long) claims.get("userId"))
                .userName((String) claims.get("userName"))
                .lastLoginDatetime((LocalDateTime) claims.get("lastLoginDatetime"))
                .build();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }
        catch (SecurityException | MalformedJwtException e) {
            //TODO log.info("Invalid JWT Token", e);
        }
        catch (ExpiredJwtException e) {
            //TODO log.info("Expired JWT Token", e);
        }
        catch (UnsupportedJwtException e) {
            //TODO log.info("Unsupported JWT Token", e);
        }
        catch (IllegalArgumentException e) {
            //TODO log.info("JWT claims string is empty.", e);
        }
        return false;
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
