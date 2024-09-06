package com.stedu.mapper;

import com.stedu.bean.Travel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TravelMapper {
    //查询所有的出行路线
    List<Travel> selectAll();

    Travel selectByTid(Integer tid);

    //获取某条路线的所有客户的cid
    List<Integer> selectCidByTid(Integer tid);
    //获取某条路线的所有员工的sid
    List<Integer> selectSidByTid(Integer tid);

    //删除某条路线和客户的关联关系
    void deleteTidAndCidByTid(Integer tid);

    //删除某条路线和员工的关联关系
    void deleteTidAndSidByTid(Integer tid);

    int delete(Integer tid);

    //添加某条路线和客户的关联关系
    void insertTidAndCid(@Param("tid") Integer tid, @Param("cids") Integer[] cids);

    //添加某条路线和员工的关联关系
    void insertTidAndSid(@Param("tid") Integer tid, @Param("sids") Integer[] sids);

    int update(Travel travel);

    int clearProgress(Travel travel);


}
