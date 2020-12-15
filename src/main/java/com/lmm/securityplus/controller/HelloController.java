package com.lmm.securityplus.controller;/*
 @author gyh
 @create 2020-12-03 09:50
 */

import com.lmm.securityplus.config.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@RestController

public class HelloController {
    @Resource
    AuthenticationManager manager;

    @GetMapping ("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/test/{test}")
    public String test(@PathVariable("test")String test){
        return  "test"+test;
    }

    @GetMapping("/login")
    public String login(String username, String password, HttpServletResponse response){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);
        try {
            Authentication authenticate = manager.authenticate(authenticationToken);
            User user = (User) authenticate.getPrincipal();

            String token = JWTUtils.generateToken(user);
            response.setContentType("application/json");
            response.setHeader("token",token);

            return "woca";


        }catch (Exception e){

                return "rilegou";

        }







    }
}
