package com.lmm.securityplus.mapper;/*
 @author gyh
 @create 2020-12-03 10:22
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lmm.securityplus.entity.User;
import com.lmm.securityplus.VO.UserVO;
import com.lmm.securityplus.VO.UserVORole;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    UserVO getDetailByName(String name);
    Integer findId(String name);

    IPage<UserVORole> findByName(IPage page);

}
