package com.SRS.SRS.Security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtUtil {


    private final String SECRETE_KEY_STRING = "rTHINBd9Y0rCPJle66ozCEDt4C2m2Hm4";
    private final SecretKey SECRETE_KEY = Keys.hmacShaKeyFor(SECRETE_KEY_STRING.getBytes());

    //    public String generateToken(UserDetails userDetails) {
//
//
//        return Jwts.builder()
//                .subject(userDetails.getUsername())
//                .issuedAt(new Date())
//                .expiration(new Date(System.currentTimeMillis() + 100 * 60 * 60))
//                .signWith(SECRETE_KEY, Jwts.SIG.HS256)
//                .compact();
//    }
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userDetails.getUsername());
        claims.put("authorities", userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority) // Extract role names
                .collect(Collectors.toList())); // Collect roles into a list

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 100 * 60 * 60))
                .signWith(SECRETE_KEY, Jwts.SIG.HS256)
                .compact();
    }


    public boolean validateToken(String token, UserDetails userDetails) {
        return extractUsername(token).equals(userDetails.getUsername());
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(SECRETE_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
