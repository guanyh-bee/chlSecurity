package com.lmm.securityplus.VO;/*
 @author gyh
 @create 2020-12-24 09:51
 */

import com.lmm.securityplus.entity.Department;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class DepartmentVO2 {

    private List<DepartmentVO> departments;
}
