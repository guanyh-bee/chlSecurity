package com.lmm.securityplus.service;/*
 @author gyh
 @create 2020-12-03 10:01
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lmm.securityplus.entity.RoleMenu;
import com.lmm.securityplus.entity.User;
import com.lmm.securityplus.entity.UserRole;
import com.lmm.securityplus.mapper.RoleMapper;
import com.lmm.securityplus.mapper.RoleMenuMapper;
import com.lmm.securityplus.mapper.UserMapper;
import com.lmm.securityplus.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;

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
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        String encode = new BCryptPasswordEncoder().encode(user.getPassword());
        return new org.springframework.security.core.userdetails
                .User(user.getUsername(), user.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_admin"));
    }


}
