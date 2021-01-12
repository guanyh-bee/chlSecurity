package com.lmm.securityplus.config.utils;/*
 @author gyh
 @create 2020-12-10 13:02
 */

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

public class JWTUtils {

    public static String generateToken(UserDetails details){
        String token = Jwts.builder().claim("username", details.getUsername()).claim("role", details.getAuthorities())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 3600)).signWith(SignatureAlgorithm.HS256, "xxxxxbbbbb")
                .compact();
        return token;
    }
    public static String generateToken(String username){
        String token = Jwts.builder().claim("username", username).claim("role", SecurityContextHolder.getContext().getAuthentication().getAuthorities())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 3600)).signWith(SignatureAlgorithm.HS256, "xxxxxbbbbb")
                .compact();
        return token;
    }


    public static String getUsername(String token){
        String username = (String)Jwts.parser().setSigningKey("xxxxxbbbbb").parseClaimsJws(token).getBody().get("username");
        return username;
    }

    public static boolean isExpire(String token){

        try {
            Date date = Jwts.parser().setSigningKey("xxxxxbbbbb").parseClaimsJws(token).getBody().getExpiration();
            if(!date.after(new Date())){
                return true;
            }else {
                return false;
            }
        }catch (ExpiredJwtException e){
            return true;
        }


    }
}
