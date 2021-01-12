package com.lmm.securityplus.controller;/*
 @author gyh
 @create 2020-12-29 13:30
 */

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lmm.securityplus.VO.UserVO;
import com.lmm.securityplus.code.ErrorCode;
import com.lmm.securityplus.config.utils.MyPage;
import com.lmm.securityplus.config.utils.PageUtils;
import com.lmm.securityplus.entity.Department;
import com.lmm.securityplus.entity.User;
import com.lmm.securityplus.mapper.DepartmentMapper;
import com.lmm.securityplus.mapper.UserMapper;
import com.lmm.securityplus.result.CommonResult;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/add")
    public CommonResult<Void> addUser(@RequestBody User user){
        System.out.println(user);
        user.setPassword(passwordEncoder.encode("123456"));
        int insert = userMapper.insert(user);
        if(insert != 1){
            return new CommonResult<>(ErrorCode.ADD_USER_FAIL);
        }
        return new CommonResult<>();
    }

    @GetMapping("/list")
    public CommonResult<MyPage<User>> getUserList(Integer departmentId, Integer current, Integer size)  {

       if(departmentId == null){
           departmentId = 99999;
       }

        List<User> user = getUser(departmentId);
        MyPage page = PageUtils.getPage(user, current, size);
        return new CommonResult<>(page);
    }

    @PostMapping("/edit")
    public CommonResult<Void> editUser(@RequestBody User user){
        int i = userMapper.updateById(user);
        if(i != 1){
            return new CommonResult<>(ErrorCode.ADD_USER_FAIL);
        }
        return new CommonResult<>();
    }

    @PostMapping("/delete")
    public CommonResult<Void> deleteUser(@RequestBody JSONObject jsonObject){
       Integer id = jsonObject.getInteger("id");
        int i = userMapper.deleteById(id);
        if(i != 1){
            return new CommonResult<>(ErrorCode.DELETE_USER_FAIL);
        }else {
            return new CommonResult<>();
        }


    }
@GetMapping("/export")
public void export(Integer id, HttpServletResponse response) throws IOException, InvalidFormatException {

    XSSFWorkbook xssfWorkbook = new XSSFWorkbook();

    XSSFSheet sheet = xssfWorkbook.createSheet("用户信息");
    if (id == null) {
        id = 99999;
    }
    Department departmentSrc = departmentMapper.selectById(id==99999?"35":id);
    List<User> user = getUser(id);
    List<UserVO> userVOS = user.stream().map(user1 -> {
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user1, userVO);
        Department department = departmentMapper.selectById(userVO.getDepartmentId());
        userVO.setDepartment(department.getDepartmentName());
        return userVO;
    }).collect(Collectors.toList());
    for (int i = 0; i < userVOS.size() ; i++) {
        XSSFRow row = sheet.createRow(i);
        row.createCell(0).setCellValue(userVOS.get(i).getUsername());
        row.createCell(1).setCellValue(userVOS.get(i).getSex() == 0?"男":"女");
        row.createCell(2).setCellValue(userVOS.get(i).getJobNumber());
        row.createCell(3).setCellValue(userVOS.get(i).getDepartment());
        row.createCell(4).setCellValue(userVOS.get(i).getFaced() == 1 ? "已注册" : "未注册");
        row.createCell(5).setCellValue(userVOS.get(i).getPhone());
    }
    String filename = new String((departmentSrc.getDepartmentName()+System.currentTimeMillis()).getBytes(),"ISO-8859-1");
    response.setHeader("Content-disposition", "attachment;filename="+filename+".xlsx");
    response.setContentType("application/octet-stream");

    xssfWorkbook.write(response.getOutputStream());
    response.flushBuffer();
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
        userQueryWrapper.orderByDesc("modified");
        List<User> users = userMapper.selectList(userQueryWrapper);
        for (User user : users) {
            user.setPassword(null);
        }
        all.addAll(users);
        return all;

    }
}
