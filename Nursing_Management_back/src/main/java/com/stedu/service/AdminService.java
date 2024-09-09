package com.stedu.service;

import com.stedu.bean.Admin;
import com.stedu.exception.MyException;

import java.util.List;
import java.util.Map;

public interface AdminService {
    Admin login(String username, String password) throws MyException;

    List<Map<String, Object>> selectDnameAndCount();

    List<Map<String, Object>> selectAgeCount();

    Admin selectById(Integer id);
}
