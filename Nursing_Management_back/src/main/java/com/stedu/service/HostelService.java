package com.stedu.service;

import com.stedu.bean.Custom;
import com.stedu.bean.Hostel;
import com.stedu.exception.MyException;

import java.util.List;

public interface HostelService {

    List<Hostel> selectByPage(String cname, String dname);

    boolean insert(Hostel hostel) throws MyException;

    boolean delete(Integer hid) throws MyException;

    Hostel selectByHid(Integer hid);

    List<Hostel> selectAll();

    //获取某个宿舍的所有客户的id
    List<Integer> selectCidByHid(Integer hid);

    //添加某个宿舍和客户的关联关系
    void insertHidAndCid(Integer hid, Integer[] cids) throws MyException;

    boolean update(Hostel hostel);

}
