package com.lmm.securityplus.controller;/*
 @author gyh
 @create 2020-12-03 09:50
 */
import com.alibaba.fastjson.JSON;
import com.lmm.securityplus.code.ErrorCode;
import com.lmm.securityplus.config.utils.JWTUtils;
import com.lmm.securityplus.VO.UserVO;
import com.lmm.securityplus.result.CommonResult;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin
public class HelloController {
    @Resource
    AuthenticationManager manager;

    @GetMapping ("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping ("/admin")
    public String admin(){
        return "admin";
    }

    @GetMapping ("/lmm")
    public String lmm(){
        return "lmm";
    }

    @GetMapping ("/low")
    public String low(){
        return "low";
    }

    @GetMapping ("/lm")
    public String lm(){
        return "llm";
    }

    @GetMapping("/test/{test}")
    public String test(@PathVariable("test")String test){
        return  "test"+test;
    }

    @GetMapping("/login")
    public CommonResult<String> login(String username, String password, HttpServletResponse response){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);
        try {
            Authentication authenticate = manager.authenticate(authenticationToken);
            UserVO user = (UserVO) authenticate.getPrincipal();
            String token = JWTUtils.generateToken(user);
//            response.setContentType("application/json");
//            response.setHeader("token",token);
//            response.addHeader("Access-Control-Expose-Headers", "token");
            return new CommonResult<>(token);
        }catch (Exception e){
            System.out.println(e.getMessage()+e.getClass());
                return new CommonResult<>(ErrorCode.LOGIN_FAIL);
        }
    }


    @PostMapping ("/refresh")
    public CommonResult<String> refreshToken(@RequestBody String token){
        String parse = (String) JSON.parseObject(token).get("token");
        if(JWTUtils.isExpire(parse)){
            return new CommonResult<>(ErrorCode.UNAUTHORIZED);
        }
        String username = JWTUtils.getUsername(parse);

        String s = JWTUtils.generateToken(username);
        return new CommonResult<>(s);

    }
}
