package com.lmm.securityplus.result;/*
 @author gyh
 @create 2020-12-18 10:02
 */

import com.lmm.securityplus.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
@Data
@Accessors(chain = true)
@AllArgsConstructor
public class CommonResult<T> implements Serializable {
    private Integer code;
    private String message;
    private T data;

    public CommonResult(){
        this.code = 200;
        this.message = "成功";
    }
    public CommonResult(T data){
        this.code = 200;
        this.message = "成功";
        this.data = data;
    }

    public CommonResult(ErrorCode errorCode){
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

}
