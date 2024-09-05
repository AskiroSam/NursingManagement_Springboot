package com.stedu.service;

import com.stedu.bean.Custom;
import com.stedu.bean.Staff;

import java.util.List;

public interface StaffService {
    List<Staff> selectBySname(String sname);

    List<Staff> selectAll();

    List<Staff> selectByDid(Integer did);

    Staff selectBySid(Integer sid);

    boolean insert(Staff staff);

    boolean update(Staff staff);

    boolean delete(Integer sid);
}
