package com.lmm.securityplus;

import cn.hutool.core.util.ArrayUtil;
import com.lmm.securityplus.entity.User;
import com.lmm.securityplus.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class SecurityPlusApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void contextLoads() {

        List<String> strings = new ArrayList<>();
        strings.add("w");
        strings.add("r");
        strings.add("t");
        System.out.println(ArrayUtil.join(strings.toArray(), ","));


    }

}
