package com.trkpo.course.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class JWTUtil {
    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static String extractId(String token){
        return extractClaim(token, Claims::getSubject);
    }
    public static <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        System.out.println(JWTUtil.generateToken());
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private static Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }


    public static String generateToken() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", null);
        return createToken(claims, "1");
    }
    public static String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }
}
