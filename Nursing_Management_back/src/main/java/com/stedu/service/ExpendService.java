package com.stedu.service;

import com.stedu.bean.Expend;

import java.util.List;

public interface ExpendService {

    boolean update(Expend e);

    Expend selectByEid(Integer eid);

    List<Expend> selectAll();
}
