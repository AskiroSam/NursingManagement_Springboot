package com.stedu.service;

import com.stedu.bean.Expend;

import java.util.List;
import java.util.Map;

public interface ExpendService {

    boolean update(Expend e);

    Expend selectByEid(Integer eid);

    List<Expend> selectAll();

    // 获取部门映射
    Map<Integer, String> getExpendMap();

    //Excel导入
    Integer getExpendId(String expend);
}
