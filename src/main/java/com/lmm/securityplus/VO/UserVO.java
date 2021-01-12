package com.lmm.securityplus.VO;/*
 @author gyh
 @create 2020-12-11 15:37
 */

import cn.hutool.core.util.ArrayUtil;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
@Data
@Accessors(chain = true)
public class UserVO implements UserDetails {
    private Integer id;
    private String jobNumber;
    private String username;
    private String password;
    private List<String> authority;
    private String department;
    private Integer faced;
    private String phone;
    private Integer sex;
    private Integer departmentId;
    private Integer deleted;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.commaSeparatedStringToAuthorityList(ArrayUtil.join(this.authority.toArray(),","));



    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.deleted==0?true:false;
    }
}
