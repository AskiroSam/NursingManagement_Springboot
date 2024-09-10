package com.stedu.service;

import com.stedu.bean.Family;
import com.stedu.exception.MyException;

import java.util.List;
import java.util.Map;

public interface FamilyService {
    List<Family> selectByFname(String fname);

    List<Family> selectAll();

    boolean insert(Family f) throws MyException;

    boolean update(Family f) throws MyException;

    boolean delete(Integer fid) throws MyException;

    Family selectByFid(Integer fid);

    // 获取部门映射
    Map<Integer, String> getFamilyMap();

    //Excel导入
    Integer getFamilyId(String family);
}
