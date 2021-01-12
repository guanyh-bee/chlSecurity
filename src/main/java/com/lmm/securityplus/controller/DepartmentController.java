package com.lmm.securityplus.controller;/*
 @author gyh
 @create 2020-12-23 15:58
 */

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lmm.securityplus.VO.DepartmentVO;
import com.lmm.securityplus.VO.DepartmentVO2;
import com.lmm.securityplus.code.ErrorCode;
import com.lmm.securityplus.entity.Department;
import com.lmm.securityplus.entity.User;
import com.lmm.securityplus.mapper.DepartmentMapper;
import com.lmm.securityplus.mapper.UserMapper;
import com.lmm.securityplus.result.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@Slf4j
public class DepartmentController {
    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/department/list")
    public CommonResult<DepartmentVO2> getDepartment(){
        QueryWrapper<Department> departmentQueryWrapper = new QueryWrapper<>();
        departmentQueryWrapper.eq("parent_id",99999);
        List<Department> departments = departmentMapper.selectList(departmentQueryWrapper);
        departmentQueryWrapper.clear();
        List<DepartmentVO> departmentVOs = new ArrayList<>();
        for (Department department : departments) {
            DepartmentVO departmentVO = doList(department);
            departmentVOs.add(departmentVO);
        }
        DepartmentVO2 departmentVO2 = new DepartmentVO2().setDepartments(departmentVOs);
        return new CommonResult<>(departmentVO2);
    }

    @PostMapping("/department/create")
    public CommonResult<Void> create(@RequestBody JSONObject jsonObject){

        Integer father = (Integer)jsonObject.get("father");
        String name = (String)jsonObject.get("name");

        QueryWrapper<Department> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("department_name",name);
        List<Department> departments = departmentMapper.selectList(queryWrapper);
        if(departments.size()==1){
            return new CommonResult<>(ErrorCode.DUPLICATE_ITEM);
        }

        int insert = departmentMapper.insert(new Department().setDepartmentName(name).setParentId(father));
        if(insert != 1){
            log.error("新建部门{}失败",name);
            throw new RuntimeException("新建失败");

        }
        log.info("新建部门{}成功",name);
        return new CommonResult<>();
    }


    @PostMapping("/department/edit")
    public CommonResult<Void> edit(@RequestBody JSONObject jsonObject){
        Integer father = (Integer)jsonObject.get("father");

        String name = (String)jsonObject.get("name");
        Integer id = (Integer)jsonObject.get("value");
        Department department1 = departmentMapper.selectById(id);

        if(department1.getParentId() == 99999){
            father = 99999;
        }
        Department department = new Department().setId(id).setDepartmentName(name).setParentId(father==id?null:father);
        int i = departmentMapper.updateById(department);

        if(i != 1){
            log.error("修改部门{}失败",name);
            throw new RuntimeException("新建失败");

        }

        return new CommonResult<>();
    }

    @PostMapping("/department/delete")
    public CommonResult<Void> delete(@RequestBody JSONObject jsonObject){
        Integer id = jsonObject.getInteger("id");

        Department department = departmentMapper.selectById(id);
        if(department.getParentId() == 99999){
            return new CommonResult<>(ErrorCode.DELETE_ROOT_ITEM);
        }
try {
    doDelete(id);
}catch (RuntimeException e){
    return new CommonResult<>(ErrorCode.DELETE_DEPARTMENT_FAIL);
}


        return new CommonResult<>();
    }








    //递归删除子部门

    public void doDelete(Integer id) {
        Department department = departmentMapper.selectById(id);
        QueryWrapper<Department> departmentQueryWrapper = new QueryWrapper<>();
        departmentQueryWrapper.eq("parent_id", department.getId());
        List<Department> departments = departmentMapper.selectList(departmentQueryWrapper);

        if(departments.size()>0){
            for (Department department1 : departments) {
                departmentQueryWrapper.clear();
                QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
                userQueryWrapper.eq("department_id",department1.getId());
                List<User> users = userMapper.selectList(userQueryWrapper);
                if(users.size()>0){
                    throw new RuntimeException("部门有人员不能删除");
                }else {
                    doDelete(department1.getId());
                }

//                departmentMapper.deleteById(department1.getId());
            }
        }
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("department_id",department.getId());
        List<User> users = userMapper.selectList(userQueryWrapper);
        if(users.size()>0){
            throw new RuntimeException("部门有人员不能删除");
        }else {
            departmentMapper.deleteById(id);
        }


    }



    //递归查询子部门
    public DepartmentVO doList(Department department) {
        QueryWrapper<Department> departmentQueryWrapper = new QueryWrapper<>();
        departmentQueryWrapper.eq("parent_id", department.getId());

        //获取子部门
        List<Department> departments = departmentMapper.selectList(departmentQueryWrapper);

        //遍历子部门
        if (departments.size() > 0) {
            List<DepartmentVO> departmentVOS = new ArrayList<>();
            for (Department department1 : departments) {
                departmentVOS.add(doList(department1));
            }
            return new DepartmentVO().setValue(department.getId()).setLabel(department.getDepartmentName()).setChildren(departmentVOS);
        } else {
            return new DepartmentVO().setValue(department.getId()).setLabel(department.getDepartmentName());
        }
    }
}
