package com.stedu.controller;

import com.stedu.bean.Expend;
import com.stedu.bean.Payout;
import com.stedu.bean.RespBean;
import com.stedu.service.PayoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/admin/payout")
public class PayoutController {
    @Autowired
    private PayoutService payoutService;

    @GetMapping
    public RespBean selectAll() {
        List<Payout> payoutList = payoutService.selectAll();
        return RespBean.ok("", payoutList);
    }

    @GetMapping("/{pid}")
    public RespBean selectByPid(@PathVariable("pid") Integer pid) {
        Payout payout = payoutService.selectByPid(pid);
        return RespBean.ok("", payout);
    }
}
