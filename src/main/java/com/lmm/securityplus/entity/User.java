package com.lmm.securityplus.entity;/*
 @author gyh
 @create 2020-12-03 10:22
 */

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Data
@Accessors(chain = true)
public class User  {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private  String username;
    private String password;
}
