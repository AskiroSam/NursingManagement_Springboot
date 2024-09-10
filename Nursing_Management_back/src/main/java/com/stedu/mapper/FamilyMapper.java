package com.stedu.mapper;

import com.stedu.bean.Custom;
import com.stedu.bean.Family;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FamilyMapper {
    List<Family> selectAll();

    List<Family> selectByFname(String fname);

    Family selectByFid(Integer fid);

    int insert(Family f);

    int update(Family f);

    int delete(Integer fid);

    List<Family> sclectByCid(Integer cid);

    Integer getFamilyId(String family);
}
