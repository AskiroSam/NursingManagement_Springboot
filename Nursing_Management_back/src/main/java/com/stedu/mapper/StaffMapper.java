package com.stedu.mapper;

import com.stedu.bean.Custom;
import com.stedu.bean.Expend;
import com.stedu.bean.Family;
import com.stedu.bean.Staff;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.hpsf.Decimal;

import java.util.List;

public interface StaffMapper {
    int insert(Staff s);

    int setEstate(@Param("sid") Integer sid, @Param("sstate") Integer sstate);

    int update(Staff s);

    List<Staff> selectAll();

    List<Staff> selectByPage(@Param("sname") String sname, @Param("sgender") String sgender, @Param("ssalary") String ssalary);

    Staff selectBySid(Integer sid);

    List<Staff> selectByDid(Integer did);


    Long selectTotal();
}
