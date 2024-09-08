package com.stedu.mapper;

import com.stedu.bean.Expend;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExpendMapper {
    int update(Expend e);

    Long selectTotal(Expend e);

    Expend selectByEid(Integer eid);

    List<Expend> selectAll();

    void updateExpend(@Param("enumber") Integer enumber, @Param("eid") Integer eid, @Param("eincome") Long eincome);

}
