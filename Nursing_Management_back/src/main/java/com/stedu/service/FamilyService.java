package com.stedu.service;

import com.stedu.bean.Family;
import com.stedu.exception.MyException;

import java.util.List;

public interface FamilyService {
    List<Family> selectByFname(String fname);

    List<Family> selectAll();

    boolean insert(Family f);

    boolean update(Family f);

    boolean delete(Integer fid) throws MyException;

    Family selectByFid(Integer fid);
}
