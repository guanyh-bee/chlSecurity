package com.lmm.securityplus.config.utils;/*
 @author gyh
 @create 2020-12-30 12:19
 */

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
@Data
@Accessors(chain = true)
public class MyPage<T> {
    private Integer total;
    private List<T> data;
    private List<T> allData;
}
