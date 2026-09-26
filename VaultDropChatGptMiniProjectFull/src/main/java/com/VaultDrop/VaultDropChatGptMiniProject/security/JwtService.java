package com.VaultDrop.VaultDropChatGptMiniProject.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey secretKey;

    public JwtService( @Value("${jwt.secret}") String secret) {

        byte[] keyBytes= Decoders.BASE64.decode(secret);

        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }




    public String generateToken(String username,String role){


        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(
                        new Date(
                                System.currentTimeMillis() +  1000 * 60 * 15
                        )
                )
                .signWith(secretKey)
                .compact();

    }



    public String extractUsername(String token){
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }




    public boolean isTokenValid(String token, String username){
        String tokenusername=extractUsername(token);

        return tokenusername.equals(username);
    }


}
