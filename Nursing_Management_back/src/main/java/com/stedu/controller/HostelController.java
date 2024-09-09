package com.stedu.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stedu.bean.Custom;
import com.stedu.bean.Hostel;
import com.stedu.bean.RespBean;
import com.stedu.exception.MyException;
import com.stedu.service.CustomService;
import com.stedu.service.HostelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/admin/hostel")
public class HostelController {
    @Autowired
    private HostelService hostelService;
    @Autowired
    private CustomService customService;
    @Value("${pageSize}")
    private Integer pageSize;

    //@GetMapping
    //public RespBean selectAll() {
    //    List<Hostel> hostelList = hostelService.selectAll();
    //    return RespBean.ok("", hostelList);
    //}

    @GetMapping
    public RespBean selectByPage(Integer pageNum, String hno, String dname) {
        if (pageNum == null) {
            pageNum = 1;
        }

        PageHelper.startPage(pageNum, pageSize);
        List<Hostel> hostelList = hostelService.selectByPage(hno, dname);
        System.out.println(hostelList);

        PageInfo<Hostel> pageInfo = new PageInfo<>(hostelList);

        return RespBean.ok("查询成功", pageInfo);
    }

    @PostMapping
    public RespBean insert(@RequestBody Hostel hostel) throws MyException {
        hostelService.insert(hostel);
        return RespBean.ok("添加成功");
    }

    @GetMapping("/allCustom/{hid}")
    public RespBean allCustom(@PathVariable("hid") Integer hid) {
        List<Map<String, Object>> customList = customService.selectByPage(null, null, null)
                .stream()
                .filter(e -> e.getCstate() == 1)
                .map(e -> {
                    Map<String, Object> customMap = new HashMap<>();
                    customMap.put("key", e.getCid());
                    customMap.put("label", e.getDepartment().getDname() + "-" + e.getCname() + "-" + e.getCaddress());

                    return customMap;
                }).collect(Collectors.toList());

        //获取已经分配完的信息
        List<Integer> cids = hostelService.selectCidByHid(hid);

        //// 过滤掉已分配的客户
        //List<Map<String, Object>> availableCustomList = customList.stream()
        //        .filter(e -> !cids.contains(e.get("key")))
        //        .collect(Collectors.toList());

        //设置返回两个值
        Map<Object, Object> map = new HashMap<>();
        map.put("allCustom", customList);
        map.put("selectCids", cids);

        return RespBean.ok("", map);

    }

    @PostMapping("/hidAndCid")
    public RespBean insertHidAndCid(@RequestBody Map<String, Object> map) throws MyException {

        Integer hid = (Integer)map.get("hid");
        //需要将集合转换为数组(若为空，要进行初始化)
        List<Integer> cidsList = (List<Integer>)map.get("cids");
        if (cidsList == null || cidsList.isEmpty()) {
            hostelService.insertHidAndCid(hid, new Integer[0]);
        } else {
            Integer[] cids = new Integer[cidsList.size()];
            for (int i = 0; i < cidsList.size(); i++) {
                int cid = cidsList.get(i);
                cids[i] = cid;
            }
            hostelService.insertHidAndCid(hid, cids);
        }

        return RespBean.ok("分配客户成功");
    }

    @DeleteMapping("/{hid}")
    public RespBean delete(@PathVariable("hid") Integer hid) throws MyException {
        if (hostelService.delete(hid)) {
            return RespBean.ok("删除成功");
        } else {
            return RespBean.error("删除失败，请重试");
        }
    }

    @PutMapping
    public RespBean update(@RequestBody Hostel hostel) {
        if (hostelService.update(hostel)) {
            return RespBean.ok("修改成功");
        } else {
            return RespBean.error("修改失败");
        }
    }

    @GetMapping("/{hid}")
    public RespBean selectByHid(@PathVariable("hid") Integer hid) {
        Hostel hostel = hostelService.selectByHid(hid);
        return RespBean.ok("", hostel);
    }

    @GetMapping("/hno/{hno}")
    public RespBean selectByHno(@PathVariable("hno") String hno) {
        Hostel hostel = hostelService.selectByHno(hno);
        return RespBean.ok("", hostel);
    }
}
