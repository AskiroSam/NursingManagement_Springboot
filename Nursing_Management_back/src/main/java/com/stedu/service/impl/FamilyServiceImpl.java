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
    public boolean insert(Family f) {
        return familyMapper.insert(f) == 1;
    }

    @Override
    public boolean update(Family f) {
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
}
