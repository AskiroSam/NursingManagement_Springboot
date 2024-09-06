package com.stedu.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.stedu.bean.Admin;
import com.stedu.exception.MyException;
import com.stedu.mapper.AdminMapper;
import com.stedu.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
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

        //密码验证 - 进行两次MD5加密
        String md5Pwd = SecureUtil.md5(SecureUtil.md5(password));
        if (!md5Pwd.equals(admin.getPassword())) {
            throw new MyException("密码错误,请确认后重新登录");
        }

        return admin;
    }

    //部门人数图表
    @Override
    public List<Map<String, Object>> selectDnameAndCount() {
        return adminMapper.selectDnameAndCount();
    }

    //年龄分布图表
    @Override
    public List<Map<String, Object>> selectAgeCount() {
        List<Map<String, Object>> ageList = adminMapper.selectAgeCount();
        Integer ageCount56 = 0;
        Integer ageCount67 = 0;
        Integer ageCount78 = 0;
        Integer ageCount89 = 0;
        Integer other = 0;

        for (Map<String, Object> ageMap : ageList) {
            Integer age = (Integer) ageMap.get("cage");

            if (age != null) {
                if (age >= 50 && age <= 60) {
                    ageCount56++;
                } else if (age > 60 && age <= 70) {
                    ageCount67++;
                } else if (age > 70 && age <= 80) {
                    ageCount78++;
                } else if (age > 80 && age <= 90) {
                    ageCount89++;
                } else {
                    other++;
                }
            }
        }

        // 创建每个年龄段的 Map
        Map<String, Object> map56 = new HashMap<>();
        map56.put("ageRange", "ageCount56");
        map56.put("count", ageCount56);

        Map<String, Object> map67 = new HashMap<>();
        map67.put("ageRange", "ageCount67");
        map67.put("count", ageCount67);

        Map<String, Object> map78 = new HashMap<>();
        map78.put("ageRange", "ageCount78");
        map78.put("count", ageCount78);

        Map<String, Object> map89 = new HashMap<>();
        map89.put("ageRange", "ageCount89");
        map89.put("count", ageCount89);

        Map<String, Object> mapOther = new HashMap<>();
        mapOther.put("ageRange", "Other");
        mapOther.put("count", other);

        // 创建包含多个 Map 的列表
        List<Map<String, Object>> resultList = new ArrayList<>();
        resultList.add(map56);
        resultList.add(map67);
        resultList.add(map78);
        resultList.add(map89);
        resultList.add(mapOther);

        return resultList;
    }

    //年龄分布图表
    //@Override
    //public List<Map<String, Object>> selectAgeCount() {
    //    List<Integer> ages = adminMapper.selectAgeCount();
    //    Integer ageCount56 = 0;
    //    Integer ageCount67 = 0;
    //    Integer ageCount78 = 0;
    //    Integer ageCount89 = 0;
    //    Integer other = 0;
    //    for (Integer age : ages) {
    //        if (age >= 50 && age <=60) {
    //            ageCount56++;
    //        } else if (age > 60 && age <= 70) {
    //            ageCount67++;
    //        } else if (age > 70 && age <= 80) {
    //            ageCount78++;
    //        } else if (age >80 && age <= 90) {
    //            ageCount89++;
    //        } else {
    //            other++;
    //        }
    //    }
    //
    //    Map<String, Object> ageMap = new HashMap<>();
    //    ageMap.put("ageCount56", ageCount56);
    //    ageMap.put("ageCount67", ageCount67);
    //    ageMap.put("ageCount78", ageCount78);
    //    ageMap.put("ageCount89", ageCount89);
    //    ageMap.put("other", other);
    //
    //    List<Map<String, Object>> ageList = new ArrayList<>();
    //    ageList.add(ageMap);
    //
    //    return ageList;
    //}
}
