package com.stedu.service.impl;

import com.stedu.bean.Custom;
import com.stedu.bean.Expend;
import com.stedu.bean.Payout;
import com.stedu.bean.Staff;
import com.stedu.mapper.PayoutMapper;
import com.stedu.mapper.StaffMapper;
import com.stedu.service.PayoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PayoutServiceImpl implements PayoutService {
    @Autowired
    private PayoutMapper payoutMapper;
    @Autowired
    private StaffMapper staffMapper;

    @Override
    public Payout selectByPid(Integer pid) {
        return payoutMapper.selectByPid(pid);
    }

    @Override
    public List<Payout> selectAll() {
        List<Payout> payouts = payoutMapper.selectAll();

        for (Payout payout : payouts) {
            List<Staff> staffList = staffMapper.selectAll();

            List<Staff> activeStaff = staffList.stream()
                    .filter(staff -> staff.getSstate() != 2)
                    .collect(Collectors.toList());

            //计算出在职员工人数
            int number = activeStaff.size();
            payout.setPnumber(number);

            //计算出工资总和
            Long l = staffMapper.selectTotal();
            payout.setPout(l);

            payoutMapper.updatePayout(payout.getPnumber(), payout.getPid(), payout.getPout());
        }


        return payouts;
    }
}
