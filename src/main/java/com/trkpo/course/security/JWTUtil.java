package com.trkpo.course.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTUtil {
    private static JWTConfiguration jwtConfiguration;

    @Autowired
    private JWTUtil(JWTConfiguration jwtConfiguration) {
        JWTUtil.jwtConfiguration = jwtConfiguration;
    }

    public static String extractId(String token){
        return extractClaim(token, Claims::getSubject);
    }
    public static <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        System.out.println(JWTUtil.generateToken());
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private static Claims extractAllClaims(String token){
        SecretKey secretKey = new SecretKeySpec(jwtConfiguration.getKey().getBytes(), 0, jwtConfiguration.getKey().length(), "HmacSHA256");
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }


    public static String generateToken() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", null);
        return createToken(claims, "1");
    }
    public static String createToken(Map<String, Object> claims, String subject) {
        SecretKey secretKey = new SecretKeySpec(jwtConfiguration.getKey().getBytes(), 0, jwtConfiguration.getKey().length(), "HmacSHA256");
        return Jwts.builder().setClaims(claims).setSubject(subject)
                .signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }
}
