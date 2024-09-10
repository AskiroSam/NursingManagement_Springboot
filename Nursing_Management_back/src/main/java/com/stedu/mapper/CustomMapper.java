package com.stedu.mapper;

import com.stedu.bean.Custom;
import com.stedu.bean.Department;
import com.stedu.bean.Hostel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomMapper {
    List<Custom> selectAll();
    List<Custom> selectByPage(@Param("cname") String cname,@Param("cgender") String cgender, @Param("caddress") String caddress);

    Custom selectByCid(Integer cid);

    Custom selectByFid(Integer fid);

    Custom selectByEid(Integer eid);

    int insert(Custom c);

    int update(Custom c);

    int setCstate(@Param("cid") Integer cid, @Param("cstate") Integer cstate);

    List<Custom> sclectByDid(Integer did);

    int updateFid(@Param("cid") Integer cid, @Param("fid") Integer fid);

    List<Custom> selectByHid(Integer hid);

    void saveAll(@Param("customList") List<Custom> customList);
}
