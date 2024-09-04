package com.stedu.mapper;

import com.stedu.bean.Staff;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StaffMapper {
    int insert(Staff s);

    int setEstate(@Param("sid") Integer sid, @Param("sstate") Integer sstate);

    int update(Staff s);

    List<Staff> selectAll();

    Staff selectBySid(Integer sid);

    List<Staff> selectByDid(Integer did);


}
