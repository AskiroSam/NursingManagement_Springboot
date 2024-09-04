package com.stedu.mapper;

import com.stedu.bean.Expend;

import java.util.List;

public interface ExpendMapper {
    int update(Expend e);

    Expend selectByEid(Integer eid);

    List<Expend> selectAll();

}
