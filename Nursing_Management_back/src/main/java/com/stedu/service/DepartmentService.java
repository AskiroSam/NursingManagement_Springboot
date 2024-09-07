package com.stedu.service;

import com.stedu.bean.Department;
import com.stedu.exception.MyException;

import java.util.List;
import java.util.Map;

public interface DepartmentService {
    boolean insert(Department d) throws MyException;

    boolean delete(Integer did) throws MyException;

    boolean update(Department d) throws MyException;

    Department selectByDid(Integer did);

    List<Department> selectByAll();

    // 获取部门映射
    Map<Integer, String> getDepartmentMap();


}
