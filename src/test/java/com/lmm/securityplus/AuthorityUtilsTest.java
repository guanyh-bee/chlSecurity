package com.lmm.securityplus;/*
 @author gyh
 @create 2020-12-16 14:29
 */

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.List;

public class AuthorityUtilsTest {

    @Test
    public void test1(){
        List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList("admin","user","black");
        System.out.println(authorityList.size());
        System.out.println("---------------------------------");
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("admin,user");
        System.out.println(grantedAuthorities.size());
    }
}
