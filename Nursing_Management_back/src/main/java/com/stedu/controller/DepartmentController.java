package com.stedu.controller;

import cn.hutool.core.util.StrUtil;
import com.stedu.bean.Department;
import com.stedu.bean.RespBean;
import com.stedu.exception.MyException;
import com.stedu.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/admin/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @PostMapping
    public RespBean insert(@RequestBody Department department) throws MyException {
        String dname = department.getDname();
        System.out.println(dname);
        String dlocation = department.getDlocation();
        System.out.println(dlocation);
        if (StrUtil.isEmpty(dname) || StrUtil.isEmpty(dlocation)) {
            return RespBean.error("参数不符合要求，请重试");
        }

        //添加
        if (departmentService.insert(department)) {
            return RespBean.ok("添加成功");
        } else {
            return RespBean.error("添加失败，请重新添加");
        }
    }

    @DeleteMapping("/{did}")
    public RespBean delete(@PathVariable("did") Integer did) throws MyException {
        if (departmentService.delete(did)) {
            return RespBean.ok("删除成功");
        } else {
            return RespBean.error("删除失败，请重试");
        }
    }

    @PutMapping
    public RespBean update(@RequestBody Department department) throws MyException {
        if (departmentService.update(department)) {
            return RespBean.ok("修改成功");
        } else {
            return RespBean.error("修改失败，请重新修改");
        }
    }

    @GetMapping("/{did}")
    public RespBean selectByDid(@PathVariable("did") Integer did) {
        Department department = departmentService.selectByDid(did);
        return RespBean.ok("", department);
    }

    @GetMapping
    public RespBean selectAll() {
        List<Department> departmentList = departmentService.selectByAll();
        return RespBean.ok("", departmentList);
    }


}
