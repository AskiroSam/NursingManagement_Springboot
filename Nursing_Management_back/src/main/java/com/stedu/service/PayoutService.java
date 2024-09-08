package com.stedu.service;

import com.stedu.bean.Expend;
import com.stedu.bean.Payout;

import java.util.List;
import java.util.Map;

public interface PayoutService {

    Payout selectByPid(Integer pid);

    List<Payout> selectAll();

}
