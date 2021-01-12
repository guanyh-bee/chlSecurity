package com.lmm.securityplus.config.mybatis_plus;/*
 @author gyh
 @create 2020-12-29 14:56
 */

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "created", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "modified", LocalDateTime.class, LocalDateTime.now());

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "modified", LocalDateTime.class, LocalDateTime.now());
    }

}
