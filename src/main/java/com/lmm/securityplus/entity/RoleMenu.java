package com.lmm.securityplus.entity;/*
 @author gyh
 @create 2020-12-11 16:44
 */

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RoleMenu {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer roleId;
    private Integer menuId;

}
