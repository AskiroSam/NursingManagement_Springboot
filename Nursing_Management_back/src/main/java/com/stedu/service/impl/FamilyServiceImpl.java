package com.stedu.service.impl;

import com.stedu.bean.Custom;
import com.stedu.bean.Department;
import com.stedu.bean.Family;
import com.stedu.exception.MyException;
import com.stedu.mapper.CustomMapper;
import com.stedu.mapper.FamilyMapper;
import com.stedu.service.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FamilyServiceImpl implements FamilyService {
    @Autowired
    private FamilyMapper familyMapper;
    @Autowired
    private CustomMapper customMapper;

    @Override
    public List<Family> selectByFname(String fname) {
        return familyMapper.selectByFname(fname);
    }

    @Override
    public List<Family> selectAll() {
        return familyMapper.selectAll();
    }

    @Override
    public boolean insert(Family f) throws MyException {
        if (f.getFname() == null) {
            throw new MyException("家属名不能为空");
        }
        if (f.getFage() == null) {
            throw new MyException("家属年龄不能为空");
        }
        if (f.getFphone() == null ||!f.getFphone().matches("\\d{11}")) {
            throw new MyException("手机号必须是11位数字");
        }
        return familyMapper.insert(f) == 1;
    }

    @Override
    public boolean update(Family f) throws MyException {
        // 验证手机号是否为11位
        if (f.getFphone() == null ||!f.getFphone().matches("\\d{11}")) {
            throw new MyException("手机号必须是11位数字");
        }
        // 验证年龄是否为最多4位整数
        if (f.getFage() < 0 || f.getFage() > 999) {
            throw new MyException("年龄必须是3位整数");
        }
        return familyMapper.update(f) == 1;
    }

    @Override
    public boolean delete(Integer fid) throws MyException {
        Custom custom = customMapper.selectByFid(fid);
        if (custom != null && custom.getCstate() == 1) {
            throw new MyException("老人还未离院，无法删除");
        }
        return familyMapper.delete(fid) == 1;
    }

    @Override
    public Family selectByFid(Integer fid) {
        return familyMapper.selectByFid(fid);
    }

    //Excel
    @Override
    public Map<Integer, String> getFamilyMap() {
        List<Family> familys = familyMapper.selectAll();
        return familys.stream()
                .collect(Collectors.toMap(Family::getFid, Family::getFname));
    }

    @Override
    public Integer getFamilyId(String family) {
        return familyMapper.getFamilyId(family);
    }
}
