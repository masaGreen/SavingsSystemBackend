package com.masaGreen.presta.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Service
@Slf4j
public class JwtService {

    private static final String secret = "114B3D543843D146CB3FDF411395C5ABCAF1654D6D";


     public String getEmailFromJWT(String token) {
        log.info("getEmailFromJWT: token: {}", token);
        return getClaimFromJWT(token, Claims::getSubject);
    }

    private String getClaimFromJWT(String token, Function<Claims, String> claimsResolver) {
        Claims claims = Jwts.parserBuilder().setSigningKey(secret.getBytes()).build().parseClaimsJws(token).getBody();
        return claimsResolver.apply(claims);
    }

    public String generateToken(String idNumber) {
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + 720000000);
        return Jwts.builder()
                .setSubject(idNumber)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, io.jsonwebtoken.security.SignatureException, IllegalArgumentException {
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
        return true;
    }

    // public String generateToken(Authentication authentication){
    //     return Jwts.builder()
    //             .setSubject(authentication.getName())
    //             .setIssuedAt(new Date())
    //             .setExpiration(new Date(new Date().getTime()+720000))
    //             .signWith(getKey(),SignatureAlgorithm.ES256)
    //             .compact();
    // }

    // public boolean validateToken(String token) throws ExpiredJwtException {
    //     Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJwt(token);
    //     return true;
    // }

    // public String extractSubject(String token){
    //     return extractClaim(token, Claims::getSubject);
    // }

    // public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
    //    Claims claims = Jwts.parserBuilder().setSigningKey(secret.getBytes()).build().parseClaimsJws(token).getBody();
    //     return claimsResolver.apply(claims);

    // }

    // private SecretKey getKey(){
    //     return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    // }
}
