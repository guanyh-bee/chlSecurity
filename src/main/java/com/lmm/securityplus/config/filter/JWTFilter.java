package com.lmm.securityplus.config.filter;/*
 @author gyh
 @create 2020-12-10 12:41
 */

import com.alibaba.fastjson.JSON;
import com.lmm.securityplus.code.ErrorCode;
import com.lmm.securityplus.config.utils.JWTUtils;
import com.lmm.securityplus.result.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JWTFilter extends OncePerRequestFilter {
    @Resource
    private UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("token");

//        try {
            if(token!=null && !JWTUtils.isExpire(token)){
                String username = JWTUtils.getUsername(token);
                if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }else{

            }
            filterChain.doFilter(request,response);
//    }
//        }catch (Exception e){
//            response.setCharacterEncoding("utf-8");
//            CommonResult<Void> commonResult = new CommonResult<>(ErrorCode.UNAUTHORIZED);
//            response.getWriter().write(JSON.toJSONString(commonResult));
//        }

    }
}
