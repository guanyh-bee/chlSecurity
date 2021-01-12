package com.lmm.securityplus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lmm.securityplus.VO.DepartmentVO;
import com.lmm.securityplus.VO.DepartmentVO2;
import com.lmm.securityplus.entity.Department;
import com.lmm.securityplus.entity.Role;
import com.lmm.securityplus.VO.UserVO;
import com.lmm.securityplus.entity.User;
import com.lmm.securityplus.mapper.DepartmentMapper;
import com.lmm.securityplus.mapper.MenuMapper;
import com.lmm.securityplus.mapper.UserMapper;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@SpringBootTest
public class SecurityPlusApplicationTests {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Test
    public void contextLoads1() {
        List<Role> roles = menuMapper.getRoles(1);
        System.out.println(roles.toString());
    }

    @Test
    public void contextLoads2() {
        UserVO lmm = userMapper.getDetailByName("lmm");
        System.out.println(lmm);
    }

    @Test
    public void contextLoads() {

//        List<String> strings = new ArrayList<>();
//        strings.add("w");
//        strings.add("r");
//        strings.add("t");
//        System.out.println(ArrayUtil.join(strings.toArray(), ","));
        UserVO lmm = userMapper.getDetailByName("lmm");
        System.out.println(lmm);
//        System.out.println("============================================================");
//        Integer lmm1 = userMapper.findId("lmm");
//        System.out.println(lmm1);

//        Page<User> page = new Page<>(1,2);
//        IPage<User> page1 = userMapper.selectPage(page, null);
//
//        System.out.println("Records:"+page1.getRecords());
//        System.out.println("Total:"+page1.getTotal());
//        System.out.println("Current:"+page1.getCurrent());
//        System.out.println("CountId:"+page1.getSize());
//        System.out.println("Pages:"+page1.getPages());
//
//        Page<UserVORole> page = new Page<>(1,2);
//        IPage<UserVORole> page1 = userMapper.findByName(page);
//
//        System.out.println("Records:"+page1.getRecords());
//        System.out.println("Total:"+page1.getTotal());
//        System.out.println("Current:"+page1.getCurrent());
//        System.out.println("CountId:"+page1.getSize());
//        System.out.println("Pages:"+page1.getPages());

    }

    @Test
    public void contextLoads3() {
        QueryWrapper<Department> departmentQueryWrapper = new QueryWrapper<>();
        departmentQueryWrapper.eq("parent_id",99999);
        List<Department> departments = departmentMapper.selectList(departmentQueryWrapper);
        List<DepartmentVO> departmentVOs = new ArrayList<>();
        for (Department department : departments) {
            DepartmentVO departmentVO = doList(department);
            departmentVOs.add(departmentVO);
        }

    }

    @Test
    public void contextLoads4() {
        QueryWrapper<Department> departmentQueryWrapper = new QueryWrapper<>();
        departmentQueryWrapper.eq("parent_id",99999);
        List<Department> departments = departmentMapper.selectList(departmentQueryWrapper);
        departmentQueryWrapper.clear();





    }


    @Test
    public void contextLoads5() {
        DepartmentVO departmentVO = doList(new Department().setId(2).setDepartmentName("测试1").setParentId(99999));
        System.out.println(departmentVO);
    }
    @Test
    public void contextLoads6() {
        doDelete(22);
    }

    @Test
    public void contextLoads7() {
        User user = userMapper.selectById(23);
        userMapper.updateById(user.setUsername("ping"));
    }

    @Test
    public void contextLoads8() {
        System.out.println(getUser(99999).size());
        System.out.println(userMapper.selectList(new QueryWrapper<User>()).size());
    }

    @Test
    public void contextLoads9() {
        for (int i = 0; i < 500; i++) {
            userMapper.insert(new User().setUsername("lxp"+i).setDepartmentId(39).setPassword(passwordEncoder.encode("lxp520"+i)).setPhone("1988888888"+i).setSex(1));
        }
    }


    //递归获取部门下的所有用户
    public List<User> getUser(Integer id){
        QueryWrapper<Department> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",id);
        List<Department> departments = departmentMapper.selectList(queryWrapper);
        List<User> all = new ArrayList<>();
        if(departments.size()>0){
            for (Department department : departments) {
                List<User> user = getUser(department.getId());
                all.addAll(user);
            }
        }
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("department_id",id);
        List<User> users = userMapper.selectList(userQueryWrapper);
        all.addAll(users);
        return all;

    }



    public void doDelete(Integer id) {
        Department department = departmentMapper.selectById(id);
        QueryWrapper<Department> departmentQueryWrapper = new QueryWrapper<>();
        departmentQueryWrapper.eq("parent_id", department.getId());
        List<Department> departments = departmentMapper.selectList(departmentQueryWrapper);

        if(departments.size()>0){
            for (Department department1 : departments) {
                departmentQueryWrapper.clear();
                doDelete(department1.getId());
//                departmentMapper.deleteById(department1.getId());
            }
        }
        departmentMapper.deleteById(id);

    }



    public DepartmentVO doList(Department department){
        QueryWrapper<Department> departmentQueryWrapper = new QueryWrapper<>();
        departmentQueryWrapper.eq("parent_id", department.getId());

        //获取子部门
        List<Department> departments = departmentMapper.selectList(departmentQueryWrapper);

        //遍历子部门
        if (departments.size()>0){
            List<DepartmentVO> departmentVOS = new ArrayList<>();
            for (Department department1 : departments) {
               departmentVOS.add(doList(department1));
            }
           return new DepartmentVO().setValue(department.getId()).setLabel(department.getDepartmentName()).setChildren(departmentVOS);
        }else {
          return   new DepartmentVO().setValue(department.getId()).setLabel(department.getDepartmentName());
        }







    }

}
