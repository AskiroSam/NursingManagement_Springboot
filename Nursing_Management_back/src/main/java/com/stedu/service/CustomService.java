package com.stedu.service;

import com.stedu.bean.Custom;
import com.stedu.exception.MyException;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomService {
    List<Custom> selectByPage(String cname, String cgender, String caddress);

    List<Custom> selectAll();

    boolean insert(Custom c) throws MyException;

    boolean update(Custom c) throws MyException;

    boolean delete(Integer cid);

    Custom selectByCid(Integer cid);

    //根据部门ID查询
    List<Custom> selectByDid(Integer did);

    boolean updateFid(Integer cid, Integer fid);

}
