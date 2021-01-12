package com.lmm.securityplus.entity;/*
 @author gyh
 @create 2020-12-03 10:22
 */

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

@Data
@Accessors(chain = true)
public class User  {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String jobNumber;
    private  String username;
    private String password;
    private Integer sex;
    private Integer departmentId;
    private String phone;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime created;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime modified;
    private Integer faced;
    @Version
    private Integer version;
    private String image;

}
