package com.stedu.mapper;

import com.stedu.bean.Admin;

import java.util.List;
import java.util.Map;

public interface AdminMapper {
    //根据用户名和密码查询
    //Admin SelectByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    Admin SelectByUsername(String username);

    //查询部门名称和客户人数，按照降序排序，只查询5个
    List<Map<String,Object>> selectDnameAndCount();

    List<Map<String,Object>> selectAgeCount();

    Admin selectById(Integer id);
}
