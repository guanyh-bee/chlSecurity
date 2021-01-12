package com.lmm.securityplus.VO;/*
 @author gyh
 @create 2020-12-23 16:01
 */

import com.lmm.securityplus.entity.Department;
import com.lmm.securityplus.mapper.DepartmentMapper;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class DepartmentVO {
    private String label;
    private Integer value;
    private List<DepartmentVO> children;
//    private List<List<List<Department>>> departments;

}
