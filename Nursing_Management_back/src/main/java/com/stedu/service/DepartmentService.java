package com.stedu.service;

import com.stedu.bean.Department;
import com.stedu.exception.MyException;

import java.util.List;

public interface DepartmentService {
    boolean insert(Department d) throws MyException;

    boolean delete(Integer did) throws MyException;

    boolean update(Department d) throws MyException;

    Department selectByDid(Integer did);

    List<Department> selectByAll();


}
