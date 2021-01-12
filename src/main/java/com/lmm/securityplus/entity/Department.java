package com.lmm.securityplus.entity;/*
 @author gyh
 @create 2020-12-23 13:51
 */

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Department {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String departmentName;
    private Integer parentId;
}
