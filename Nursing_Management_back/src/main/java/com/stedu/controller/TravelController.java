package com.stedu.controller;

import cn.hutool.core.util.StrUtil;
import com.stedu.bean.RespBean;
import com.stedu.bean.Travel;
import com.stedu.bean.TravelProgressDTO;
import com.stedu.exception.MyException;
import com.stedu.service.CustomService;
import com.stedu.service.StaffService;
import com.stedu.service.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.*;
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

    @PostMapping
    public RespBean insert(@RequestBody Travel travel) throws MyException {

        String tlocation = travel.getTlocation();
        if (StrUtil.isEmpty(tlocation)) {
            throw new MyException("路线不能为空，请重新输入");
        }

        if (travel.getTstart() == null) {
            throw new MyException("开始时间不能为空，请重新输入");
        }

        if (travel.getTend() == null) {
            travel.setTprogress(0);
            throw new MyException("结束时间不能为空，请重新输入");
        }

        if (travelService.insert(travel)) {
            return RespBean.ok("添加成功");
        } else {
            return RespBean.error("添加失败");
        }
    }


    @GetMapping("/allCustom/{tid}")
    //获取所有客户信息
    public RespBean allCustom(@PathVariable("tid") Integer tid) {
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

        //获取已经分配完的信息
        List<Integer> cids = travelService.selectCidByTid(tid);

        //设置返回两个值
        Map<Object, Object> map = new HashMap<>();
        map.put("allCustom", customMapList);
        map.put("selectCids", cids);

        return RespBean.ok("", map);
    }

    @GetMapping("/allStaff/{tid}")
    //获取所有客户信息
    public RespBean allStaff(@PathVariable("tid") Integer tid) {
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

        //获取已经分配完的信息
        List<Integer> sids = travelService.selectSidByTid(tid);

        //返回两个值
        Map<Object, Object> map = new HashMap<>();
        map.put("allStaff", staffMapList);
        map.put("selectSids", sids);

        return RespBean.ok("", map);
    }

    //分配客户
    @PostMapping("/tidAndCid")
    public RespBean insertTidAndCid(@RequestBody Map<String, Object> map) {

        Integer tid = (Integer)map.get("tid");
        //需要将集合转换为数组(若为空，要进行初始化)
        List<Integer> cidsList = (List<Integer>) map.get("cids");
        if (cidsList == null || cidsList.isEmpty()) {
            travelService.insertTidAndCid(tid, new Integer[0]);
        } else {
            Integer[] cids = new Integer[cidsList.size()];
            for (int i = 0; i < cidsList.size(); i++) {
                int cid = cidsList.get(i);
                cids[i] = cid;
            }
            travelService.insertTidAndCid(tid, cids);
        }

        return RespBean.ok("分配客户成功");
    }

    //分配员工
    @PostMapping("/tidAnSid")
    public RespBean insertTidAndSid(@RequestBody Map<String, Object> map) {

        Integer tid = (Integer)map.get("tid");
        //需要将集合转换为数组(若为空，要进行初始化)
       List<Integer> sidsList = (List<Integer>) map.get("sids");
       if (sidsList == null || sidsList.isEmpty()) {
           travelService.insertTidAndSid(tid, new Integer[0]);
       } else {
           Integer[] sids = new Integer[sidsList.size()];
           for (int i = 0; i < sidsList.size(); i++) {
               int sid = sidsList.get(i);
               sids[i] = sid;
           }
           travelService.insertTidAndSid(tid, sids);
       }

        return RespBean.ok("分配员工成功");
    }

    //删除线路
    @DeleteMapping("/{tid}")
    public RespBean delete(@PathVariable("tid") Integer tid) throws MyException {
        if (travelService.delete(tid)) {
            return RespBean.ok("删除成功");
        } else {
            return RespBean.error("删除失败，请重试");
        }
    }

    //进度条
    @GetMapping("/progress/{tid}")
    public TravelProgressDTO getTravelProgress(@PathVariable("tid") Integer tid) throws ParseException {
        //Travel对象
        Travel travel = travelService.selectByTid(tid);

        // 计算进度
        return travelService.calculateProgress(travel);

    }

    @PutMapping
    public RespBean update(@RequestBody Travel travel) {
        if (travelService.update(travel)) {
            return RespBean.ok("修改成功");
        } else {
            return RespBean.error("修改失败");
        }
    }
    @PutMapping("/progress")
    public  RespBean clearProgress(@RequestBody Travel travel) {
        if (travelService.clearProgress(travel)) {
            return RespBean.ok("更新成功");
        } else {
            return RespBean.error("更新失败");
        }
    }

}
