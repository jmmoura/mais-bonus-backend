package com.josiel.maisbonus.authentication.utils;

import com.josiel.maisbonus.enums.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class JWTUtils {

    public static final SecretKey TOKEN_SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    public static final String TOKEN_PREFIX = "Bearer";
    public static final String TOKEN_CLAIM_USERNAME = "username";
    public static final String TOKEN_CLAIM_ROLES = "roles";
    public static final long TOKEN_EXPIRATION = 3_600_000;

    public static String generateToken(final Long userId, final String username, final Role userRole) {
        final Date now = new Date();
        final Date exp = new Date(now.getTime() + TOKEN_EXPIRATION);

        String jwtToken =
                Jwts.builder()
                        .setSubject(userId.toString())
                        .claim(TOKEN_CLAIM_USERNAME, username)
                        .claim(TOKEN_CLAIM_ROLES, userRole)
                        .setIssuedAt(now)
                        .setNotBefore(now)
//                        .setExpiration(exp)
                        .signWith(TOKEN_SECRET_KEY)
                        .compact();

        return TOKEN_PREFIX + " " + jwtToken;
    }

    public static Claims parseToken(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(TOKEN_SECRET_KEY)
                .build()
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody();
    }
}
