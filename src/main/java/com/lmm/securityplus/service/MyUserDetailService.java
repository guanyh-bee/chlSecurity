package com.lmm.securityplus.service;/*
 @author gyh
 @create 2020-12-03 10:01
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lmm.securityplus.entity.User;
import com.lmm.securityplus.VO.UserVO;
import com.lmm.securityplus.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("username", username);
//        User user = userMapper.selectOne(queryWrapper);
//        if (user == null) {
//            throw new UsernameNotFoundException("用户不存在");
//        }
//        QueryWrapper<UserRole> queryWrapper1 = new QueryWrapper<>();
//        queryWrapper1.eq("userId",user.getId());
//        UserRole userRole = userRoleMapper.selectOne(queryWrapper1);
//        QueryWrapper<RoleMenu> queryWrapper2 = new QueryWrapper<>();
//        queryWrapper2.eq("roleId", userRole.getRoleId());
//        List<RoleMenu> roleMenus = roleMenuMapper.selectList(queryWrapper2);
//        List<Integer> collect = roleMenus.stream().map(roleMenu -> roleMenu.getMenuId()).collect(Collectors.toList());



        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        UserVO user = userMapper.getDetailByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return user;
    }


}
