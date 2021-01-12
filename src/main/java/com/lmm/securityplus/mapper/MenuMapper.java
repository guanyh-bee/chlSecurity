package com.lmm.securityplus.mapper;/*
 @author gyh
 @create 2020-12-11 16:41
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lmm.securityplus.entity.Menu;
import com.lmm.securityplus.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
    List<Role> getRoles(Integer id);
}
