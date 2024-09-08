package com.stedu.service;

import com.stedu.bean.Custom;
import com.stedu.bean.Staff;
import com.stedu.exception.MyException;

import java.util.List;

public interface StaffService {
    List<Staff> selectByPage(String sname, String sgender, String ssalary);

    List<Staff> selectAll();

    List<Staff> selectByDid(Integer did);

    Staff selectBySid(Integer sid);

    boolean insert(Staff staff) throws MyException;

    boolean update(Staff staff);

    boolean delete(Integer sid);
}
