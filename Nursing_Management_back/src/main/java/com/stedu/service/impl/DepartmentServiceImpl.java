package com.stedu.service.impl;

import com.stedu.bean.Custom;
import com.stedu.bean.Department;
import com.stedu.bean.Staff;
import com.stedu.exception.MyException;
import com.stedu.mapper.DepartmentMapper;
import com.stedu.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public boolean insert(Department d) throws MyException {
        if (departmentMapper.selectByDname(d.getDname()) != null) {
            throw new MyException("该部门已经存在，无法重复添加");
        }

        return departmentMapper.insert(d) == 1;
    }

    @Override
    public boolean delete(Integer did) throws MyException {
        //if (departmentMapper.selectByDid(did) == null) {
        //    throw new MyException("该部门不存在，无法删除");
        //}
        Department department = departmentMapper.selectByDid(did);
        if (department.getPersonnum() != 0) {
            throw new MyException("该系部下还有客户，无法删除");
        }
        return departmentMapper.delete(did) == 1;
    }

    @Override
    public boolean update(Department d) throws MyException {
        if (departmentMapper.selectByDid(d.getDid()) == null) {
            throw new MyException("该部门不存在，无法修改");
        }

        //修改之后的名字不能和其他的部门名字重复
        Department department = departmentMapper.selectByDname(d.getDname());
        if (department != null && department.getDid() != d.getDid()) {
            throw new MyException("修改之后的名字和其他部门的名字重复，请重新修改");
        }

        return departmentMapper.update(d) == 1;
    }

    @Override
    public Department selectByDid(Integer did) {
        return departmentMapper.selectByDid(did);
    }

    @Override
    public List<Department> selectByAll() {

        List<Department> departments = departmentMapper.selectAll();


        //来判断各个部门的人数并更新数据
        for (Department department : departments) {

            if (department.getCustomList() == null) {
                department.setCustomList(new ArrayList<>());
            }
            if (department.getStaffList() == null) {
                department.setStaffList(new ArrayList<>());
            }

            // 过滤掉状态为2的客户和员工(离院或离职后不再计算人数)
            List<Custom> activeCustomers = department.getCustomList().stream()
                    .filter(customer -> customer.getCstate() != 2)
                    .collect(Collectors.toList());

            List<Staff> activeStaff = department.getStaffList().stream()
                    .filter(staff -> staff.getSstate() != 2)
                    .collect(Collectors.toList());

            // 计算过滤后的总人数
            int size1 = activeCustomers.size();
            int size2 = activeStaff.size();
            Integer totalPersonnum = size1 + size2;
            department.setPersonnum(totalPersonnum);

            // 更新数据库
            departmentMapper.updateDepartment(department.getPersonnum(), department.getDid());
        }


        return departments;
    }

    //Excel
    @Override
    public Map<Integer, String> getDepartmentMap() {
        List<Department> departments = departmentMapper.selectAll();
        return departments.stream()
                .collect(Collectors.toMap(Department::getDid, Department::getDname));
    }

}
