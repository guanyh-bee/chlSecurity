package com.lmm.securityplus.config.security;/*
 @author gyh
 @create 2020-12-03 09:55
 */

import com.alibaba.fastjson.JSON;
import com.lmm.securityplus.code.ErrorCode;
import com.lmm.securityplus.config.filter.JWTFilter;
import com.lmm.securityplus.result.CommonResult;
import com.lmm.securityplus.service.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;


@Configuration
@CrossOrigin
public class MyConfig  extends WebSecurityConfigurerAdapter{
    @Autowired
    private MyUserDetailService userDetailService;
    @Autowired
    private JWTFilter jwtFilter;


    @Autowired
    private MyFilterInvocationSecurityMetadataSource filterMetadataSource; //权限过滤器（当前url所需要的访问权限）
    @Autowired
    private MyAccessDecisionManager myAccessDecisionManager;//权限决策器



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login","/user/export").permitAll()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {

                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setSecurityMetadataSource(filterMetadataSource);
                        o.setAccessDecisionManager(myAccessDecisionManager);
                        return o;
                    }
                })
                .anyRequest().authenticated()
                .and().addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class).exceptionHandling()
                .accessDeniedHandler((request,response,exception)->{
                    response.setCharacterEncoding("utf-8");response.getWriter().write(JSON.toJSONString(new CommonResult<Void>(ErrorCode.FORBIDDEN)));
                   })
                .authenticationEntryPoint((request,response,exception)->{
                    response.setCharacterEncoding("utf-8");response.getWriter().write(JSON.toJSONString(new CommonResult<Void>(ErrorCode.WITHOUT_LOGIN)));
                }).and()
                .cors().and()
                .csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);


    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
