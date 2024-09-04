package com.stedu.service;

import com.stedu.bean.Custom;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomService {
    List<Custom> selectByCname(String cname);

    List<Custom> selectAll();

    boolean insert(Custom c);

    boolean update(Custom c);

    boolean delete(Integer cid);

    Custom selectByCid(Integer cid);

    //根据部门ID查询
    List<Custom> selectByDid(Integer did);

    boolean updateFid(Integer cid, Integer fid);

}
