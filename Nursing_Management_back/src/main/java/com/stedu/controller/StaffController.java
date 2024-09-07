package com.stedu.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stedu.bean.Custom;
import com.stedu.bean.RespBean;
import com.stedu.bean.Staff;
import com.stedu.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/admin/staff")
public class StaffController {
    @Autowired
    private StaffService staffService;
    @Value("${pageSize}")
    private Integer pageSize;

    @GetMapping
    public RespBean selectByPage(Integer pageNum, String sname, String sgender, String ssalary) {
        if (pageNum == null) {
            pageNum = 1;
        }

        PageHelper.startPage(pageNum, pageSize);
        List<Staff> staffList = staffService.selectByPage(sname, sgender, ssalary);
        //List<Custom> customList = customService.selectAll();

        PageInfo<Staff> pageInfo = new PageInfo<>(staffList);

        return RespBean.ok("查询成功", pageInfo);
    }

    @GetMapping("/{sid}")
    public RespBean selectBySid(@PathVariable("sid") Integer sid) {
        Staff s = staffService.selectBySid(sid);
        return RespBean.ok("", s);
    }

    @PostMapping
    public RespBean insert(@RequestBody Staff staff) {
        staffService.insert(staff);
        return RespBean.ok("添加成功");
    }

    @DeleteMapping("/{sid}")
    public RespBean delete(@PathVariable("sid") Integer sid) {
        staffService.delete(sid);
        return RespBean.ok("设置离职状态成功");
    }

    @PutMapping
    public RespBean update(@RequestBody Staff staff){
        staffService.update(staff);
        return RespBean.ok("修改成功");
    }
}
