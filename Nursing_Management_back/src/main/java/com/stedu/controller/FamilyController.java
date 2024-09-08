package com.stedu.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stedu.bean.Custom;
import com.stedu.bean.Family;
import com.stedu.bean.RespBean;
import com.stedu.exception.MyException;
import com.stedu.service.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/admin/family")
public class FamilyController {
    @Autowired
    private FamilyService familyService;
    @Value("${pageSize}")
    private Integer pageSize;

    @GetMapping
    public RespBean selectByPage(Integer pageNum, String fname) {
        if (pageNum == null) {
            pageNum = 1;
        }

        PageHelper.startPage(pageNum, pageSize);
        List<Family> familyList = familyService.selectByFname(fname);

        PageInfo<Family> pageInfo = new PageInfo<>(familyList);
        return RespBean.ok("查询成功", pageInfo);
    }

    @PostMapping
    public RespBean insert(@RequestBody Family family) {
        boolean result = familyService.insert(family);
        //校准客户表
        if (result) {
            Integer fid = family.getFid();

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("code", 10000);
            map.put("fid", fid);

            return RespBean.ok("添加成功", map);
        }
        return RespBean.error("添加失败");
    }

    @DeleteMapping("/{fid}")
    public RespBean delete(@PathVariable("fid") Integer fid) throws MyException {
        familyService.delete(fid);
        return RespBean.ok("删除成功");
    }

    @PutMapping
    public RespBean update(@RequestBody Family family) throws MyException {
        familyService.update(family);
        return RespBean.ok("修改成功");
    }

    @GetMapping("/{fid}")
    public RespBean selectByFid(@PathVariable("fid") Integer fid) {
        Family f = familyService.selectByFid(fid);
        return RespBean.ok("", f);
    }

}
