package com.stedu.controller;

import com.stedu.bean.Department;
import com.stedu.bean.Expend;
import com.stedu.bean.RespBean;
import com.stedu.service.ExpendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/admin/expend")
public class ExpendController {
    @Autowired
    private ExpendService expendService;

    @PutMapping
    public RespBean update(@RequestBody Expend expend) {
        if (expendService.update(expend)) {
            return RespBean.ok("修改成功");
        } else {
            return RespBean.error("修改失败");
        }
    }

    @GetMapping("/{eid}")
    public RespBean selectByEid(@PathVariable("eid") Integer eid) {
        Expend expend = expendService.selectByEid(eid);
        return RespBean.ok("", expend);
    }

    @GetMapping
    public RespBean selectAll() {
        List<Expend> departmentList = expendService.selectAll();
        return RespBean.ok("", departmentList);
    }

}
