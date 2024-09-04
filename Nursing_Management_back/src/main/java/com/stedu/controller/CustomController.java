package com.stedu.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stedu.bean.Custom;
import com.stedu.bean.RespBean;
import com.stedu.service.CustomService;
import com.stedu.service.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/admin/custom")
public class CustomController {
    @Autowired
    private CustomService customService;
    @Autowired
    private FamilyService familyService;
    @Value("${pageSize}")
    private Integer pageSize;

    @GetMapping
    public RespBean selectByPage(Integer pageNum, String cname) {
        if (pageNum == null) {
            pageNum = 1;
        }

        PageHelper.startPage(pageNum, pageSize);
        List<Custom> customList = customService.selectByCname(cname);
        //List<Custom> customList = customService.selectAll();
        System.out.println(customList);

        PageInfo<Custom> pageInfo = new PageInfo<>(customList);

        return RespBean.ok("查询成功", pageInfo);
    }

    @PostMapping
    public RespBean insert(@RequestBody Custom custom) {
        boolean result = customService.insert(custom);
        //校准家属表
        if (result) {
            Integer cid = custom.getCid();

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("code", 10000);
            map.put("cid", cid);

            return RespBean.ok("添加成功", map);
        }
        return RespBean.error("添加失败");
    }

    @DeleteMapping("/{cid}")
    public RespBean delete(@PathVariable("cid") Integer cid) {
        customService.delete(cid);
        return RespBean.ok("设置离职状态成功");
    }

    @PutMapping
    public RespBean update(@RequestBody Custom custom){
        customService.update(custom);
        return RespBean.ok("修改成功");
    }

    @GetMapping("/{cid}")
    public RespBean selectByCid(@PathVariable("cid") Integer cid) {
        Custom c = customService.selectByCid(cid);
        return RespBean.ok("", c);
    }

    @PutMapping("/updateFid")
    public RespBean updateFid(@RequestParam Integer cid, @RequestParam Integer fid) {
        boolean success = customService.updateFid(cid, fid);
        if (success) {
            return RespBean.ok("更新成功");
        } else {
            return RespBean.error("更新失败");
        }
    }

}
