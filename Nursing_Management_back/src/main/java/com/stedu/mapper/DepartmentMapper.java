package com.stedu.mapper;

import com.stedu.bean.Custom;
import com.stedu.bean.Department;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DepartmentMapper {
    int insert(Department d);

    int delete(Integer did);

    int update(Department d);

    //查询所有
    List<Department> selectAll();

    //根据ID查询
    Department selectByDid(Integer did);

    //根据名称查询
    Department selectByDname(String dname);

    void updateDepartment(@Param("personnum") Integer personnum, @Param("did") Integer did);

    Integer getDepartmentId(String department);
}
