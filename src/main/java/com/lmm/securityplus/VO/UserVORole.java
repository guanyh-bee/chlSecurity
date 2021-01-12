package com.lmm.securityplus.VO;/*
 @author gyh
 @create 2020-12-16 13:34
 */

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserVORole {
    private Integer id;
    private String username;
    private String password;
    private Integer roleId;
}
