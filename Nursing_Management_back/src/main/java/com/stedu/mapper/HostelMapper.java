package com.stedu.mapper;

import com.stedu.bean.Custom;
import com.stedu.bean.Hostel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HostelMapper {
    int insert(Hostel hostel);

    List<Hostel> selectAll();

    List<Hostel> selectByPage(@Param("hno") String hno,@Param("dname") String dname);

    Hostel selectByHid(Integer hid);

    Hostel selectByHno(String hno);

    //获取某个宿舍下的所有客户
    List<Integer> selectCidByHid(Integer hid);

    //删除某个宿舍和客户的关联关系
    void deleteCidAndHidByHid(Integer hid);

    int delete(Integer hid);

    //添加某个宿舍和客户的关联关系
    void insertHidAndCid(@Param("hid") Integer hid, @Param("cids") Integer[] cids);

    int update(Hostel hostel);

    void updateDnumber(@Param("dnumber") Integer dnumber, @Param("hid") Integer hid);

    List<Hostel> sclectByCid(Integer cid);

}
