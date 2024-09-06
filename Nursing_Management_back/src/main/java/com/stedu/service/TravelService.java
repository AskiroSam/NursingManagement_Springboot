package com.stedu.service;


import com.stedu.bean.Travel;
import com.stedu.bean.TravelProgressDTO;
import com.stedu.exception.MyException;
import org.apache.ibatis.annotations.Param;

import java.text.ParseException;
import java.util.List;

public interface TravelService {
    boolean delete(Integer tid) throws MyException;

    Travel selectByTid(Integer tid);
    //查询所有的出行路线
    List<Travel> selectAll();

    //获取某条路线的所有客户的cid
    List<Integer> selectCidByTid(Integer tid);

    //获取某条路线的所有员工的sid
    List<Integer> selectSidByTid(Integer tid);

    ////添加某条路线和客户的关联关系
    void insertTidAndCid(Integer tid, Integer[] cids);

    //添加某条路线和员工的关联关系
    void insertTidAndSid(Integer tid, Integer[] sids);

    // 根据旅行 ID 获取单个路线的进度
    TravelProgressDTO calculateProgress(Travel travel) throws ParseException;

    boolean update(Travel travel);

    boolean clearProgress(Travel travel);

}
