package com.swimmingcommunityapp.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

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
    public static String createToken(String userName, String key, Long expireTimeMs){
        Claims claims = Jwts.claims(); //일종의 map
        claims.put("userName",userName);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis())) //만든 날짜
                .setExpiration(new Date(System.currentTimeMillis()+expireTimeMs))//끝나는 날짜
                .signWith(SignatureAlgorithm.HS256,key)
                .compact();

    }
}
