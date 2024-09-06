package com.stedu.controller;

import com.stedu.bean.RespBean;
import com.stedu.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/admin/info")
public class InfoController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/topDepartment")
    public RespBean selectDnameAndCount() {
        List<Map<String,Object>> mapList = adminService.selectDnameAndCount();
        return RespBean.ok("", mapList);
    }

    @GetMapping("/ageCount")
    public RespBean selectAgeCount() {
        List<Map<String, Object>> ageList = adminService.selectAgeCount();
        return RespBean.ok("", ageList);
    }

    @GetMapping("/adminInfo")
    public RespBean getLoginInfo(HttpSession session) {
        //获取session中存放的已登录用户的信息
        Object admin = session.getAttribute("admin");

        //返回
        return RespBean.ok("", admin);
    }
}
