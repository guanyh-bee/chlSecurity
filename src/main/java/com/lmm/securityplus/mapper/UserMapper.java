package com.lmm.securityplus.mapper;/*
 @author gyh
 @create 2020-12-03 10:22   
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.lmm.securityplus.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
