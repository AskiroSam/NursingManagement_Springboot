package com.stedu.controller;

import com.stedu.bean.RespBean;
import com.stedu.bean.Travel;
import com.stedu.service.CustomService;
import com.stedu.service.StaffService;
import com.stedu.service.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/admin/travel")
public class TravelController {
    @Autowired
    private CustomService customService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private TravelService travelService;

    @GetMapping
    //查询所有路线
    public RespBean selectAll() {
        List<Travel> travelList = travelService.selectAll();
        return RespBean.ok("", travelList);
    }

    @GetMapping("/allCustom")
    //获取所有客户信息
    public RespBean allCustom() {
        //{{key: 客户的cid, label: 客户的系部信息 - 客户姓名}}
        List<Map<String, Object>> customMapList = customService.selectByCname(null)
                .stream()
                .filter(e -> e.getCstate() == 1)
                .map(e -> {
                    Map<String, Object> customMap = new HashMap<>();
                    customMap.put("key", e.getCid());
                    customMap.put("label", e.getDepartment().getDname() + "-" + e.getCname());

                    return customMap;
                }).collect(Collectors.toList());
        return RespBean.ok("", customMapList);
    }

    @GetMapping("/allStaff")
    //获取所有客户信息
    public RespBean allStaff() {
        //{{key: 客户的cid, label: 客户的系部信息 - 客户姓名}}
        List<Map<String, Object>> staffMapList = staffService.selectBySname(null)
                .stream()
                .filter(e -> e.getSstate() == 1)
                .map(e -> {
                    Map<String, Object> staffMap = new HashMap<>();
                    staffMap.put("key", e.getSid());
                    staffMap.put("label", e.getDepartment().getDname() + "-" + e.getSno() + e.getSname());

                    return staffMap;
                }).collect(Collectors.toList());
        return RespBean.ok("", staffMapList);
    }
}
