package com.stedu.service.impl;

import com.stedu.bean.Admin;
import com.stedu.exception.MyException;
import com.stedu.mapper.AdminMapper;
import com.stedu.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    public AdminMapper adminMapper;

    @Override
    public Admin login(String username, String password) throws MyException {
        Admin admin = adminMapper.SelectByUsername(username);
        //用户名怕非空判断
        if (admin == null) {
            throw new MyException("用户名错误，请重试");
        }
        //密码验证
        if (!password.equals(admin.getPassword())) {
            throw new MyException("密码错误，请重试");
        }

        return admin;
    }

    @Override
    public List<Map<String, Object>> selectDnameAndCount() {
        return adminMapper.selectDnameAndCount();
    }
}
