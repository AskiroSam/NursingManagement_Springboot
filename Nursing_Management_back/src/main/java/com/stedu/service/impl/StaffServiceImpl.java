package com.stedu.service.impl;

import com.stedu.bean.Staff;
import com.stedu.exception.MyException;
import com.stedu.mapper.StaffMapper;
import com.stedu.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffServiceImpl implements StaffService {
    @Autowired
    private StaffMapper staffMapper;

    @Override
    public List<Staff> selectByPage(String sname, String sgender, String ssalary) {
        return staffMapper.selectByPage(sname, sgender, ssalary);
    }

    @Override
    public List<Staff> selectAll() {
        return staffMapper.selectAll();
    }

    @Override
    public List<Staff> selectByDid(Integer did) {
        return staffMapper.selectByDid(did);
    }

    @Override
    public Staff selectBySid(Integer sid) {
        return staffMapper.selectBySid(sid);
    }

    @Override
    public boolean insert(Staff staff) throws MyException {
        staff.setSstate(1);
        // 验证手机号是否为11位
        if (staff.getSno() == null ||!staff.getSno().matches("\\d{5}")) {
            throw new MyException("工号必须是5位数字");
        }
        return staffMapper.insert(staff) == 1;
    }

    @Override
    public boolean update(Staff staff) {
        return staffMapper.update(staff) == 1;
    }

    @Override
    public boolean delete(Integer sid) {
        return staffMapper.setEstate(sid, 2) == 1;
    }
}
