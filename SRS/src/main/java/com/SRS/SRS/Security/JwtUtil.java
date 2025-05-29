package com.SRS.SRS.Security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {


    private final String SECRETE_KEY_STRING ="rTHINBd9Y0rCPJle66ozCEDt4C2m2Hm4";
    private final SecretKey SECRETE_KEY= Keys.hmacShaKeyFor(SECRETE_KEY_STRING.getBytes());

    public  String generateToken(UserDetails userDetails){


        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+100*60*60))
                .signWith(SECRETE_KEY,Jwts.SIG.HS256)
                .compact();
    }

    public boolean validateToken(String token,UserDetails userDetails){
        return extractUsername(token).equals(userDetails.getUsername());
    }

    public String extractUsername(String token){
         return Jwts.parser()
                .verifyWith(SECRETE_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
