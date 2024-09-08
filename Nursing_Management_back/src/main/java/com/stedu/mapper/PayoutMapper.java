package com.stedu.mapper;

import com.stedu.bean.Expend;
import com.stedu.bean.Payout;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PayoutMapper {

    Long selectTotal(Payout p);

    Payout selectByPid(Integer pid);

    List<Payout> selectAll();

    void updatePayout(@Param("pnumber") Integer pnumber, @Param("pid") Integer pid, @Param("pout") Long pout);

}
