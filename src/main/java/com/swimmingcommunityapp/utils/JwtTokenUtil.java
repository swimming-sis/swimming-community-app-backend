package com.swimmingcommunityapp.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import java.time.Duration;
import java.util.Date;


public class JwtTokenUtil {

    public static Claims extractClaims(String token, String key){
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
    }
    public static String getUserName(String token,String key){
        return extractClaims(token, key).get("userName").toString();
    }
    public static boolean isExpired(String token, String secretKey){
        Date expireDate = extractClaims(token, secretKey).getExpiration();
        return expireDate.before(new Date());
    }

    public static String createAccessToken( String userName,String key) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + Duration.ofHours(2).toMillis());
        return Jwts.builder()
                .claim("userName", userName)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }
    public static String createRefreshToken(String userName,String key) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + Duration.ofDays(30).toMillis());
        return Jwts.builder()
                .claim("userName", userName)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

}
