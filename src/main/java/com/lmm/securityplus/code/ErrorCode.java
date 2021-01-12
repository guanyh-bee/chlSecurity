package com.lmm.securityplus.code;/*
 @author gyh
 @create 2020-12-18 10:43
 */

import lombok.Data;

public enum ErrorCode {
    NOT_FOUND(404,"没有资源"),
    FORBIDDEN(403,"禁止访问"),
    UNAUTHORIZED(401,"授权失败,登录已过期或token验证失败"),
    WITHOUT_LOGIN(400,"还没有登录"),
    LOGIN_FAIL(406,"登录失败"),
    DUPLICATE_ITEM(801,"名称已经被使用"),
    DELETE_ROOT_ITEM(802,"不能删除根目录"),
    ADD_USER_FAIL(803,"插入用户失败"),
    DELETE_DEPARTMENT_FAIL(804,"删除部门失败，部门下有用户"),
    DELETE_USER_FAIL(805,"删除用户失败，未知错误"),;

    private Integer code;
    private String message;

    ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
