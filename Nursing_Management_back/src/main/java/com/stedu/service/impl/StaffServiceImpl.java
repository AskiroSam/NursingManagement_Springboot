package com.stedu.service.impl;

import com.stedu.bean.Staff;
import com.stedu.exception.MyException;
import com.stedu.mapper.StaffMapper;
import com.stedu.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.math.BigDecimal;
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
    public boolean insert(@Valid Staff staff) throws MyException {
        staff.setSstate(1);
        // 验证手机号是否为11位
        if (staff.getSno() == null ||!staff.getSno().matches("\\d{5}")) {
            throw new MyException("工号必须是5位数字");
        }
        if (staffMapper.selectBySno(staff.getSno()) != null) {
            throw new MyException("该员工已经存在，请修改工号");
        }
        // 验证年龄是否为最多4位整数
        if (staff.getSage() < 0 || staff.getSage() > 999) {
            throw new MyException("年龄必须是3位整数");
        }

        // 验证薪资不能超过20000
        BigDecimal minAge = BigDecimal.ZERO;
        BigDecimal maxAge = new BigDecimal("20000");
        if (staff.getSsalary() == null || staff.getSsalary().compareTo(minAge) < 0 || staff.getSsalary().compareTo(maxAge) > 0) {
            throw new MyException("薪资不得大于20000");
        }
        return staffMapper.insert(staff) == 1;
    }

    @Override
    public boolean update(@Valid Staff staff) throws MyException {
        // 验证年龄是否为最多4位整数
        if (staff.getSage() < 0 || staff.getSage() > 999) {
            throw new MyException("年龄必须是3位整数");
        }
        // 验证薪资不能超过20000
        BigDecimal minAge = BigDecimal.ZERO;
        BigDecimal maxAge = new BigDecimal("20000");
        if (staff.getSsalary() == null || staff.getSsalary().compareTo(minAge) < 0 || staff.getSsalary().compareTo(maxAge) > 0) {
            throw new MyException("薪资不得大于20000");
        }
        return staffMapper.update(staff) == 1;
    }

    @Override
    public boolean delete(Integer sid) {
        return staffMapper.setEstate(sid, 2) == 1;
    }
}
