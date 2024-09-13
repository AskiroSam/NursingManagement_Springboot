package com.stedu.controller;


import com.stedu.Util.JwtUtils;
import com.stedu.bean.Admin;
import com.stedu.bean.RespBean;
import com.stedu.exception.MyException;
import com.stedu.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;

@CrossOrigin
@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;

    //登录
    @ResponseBody
    @PostMapping("/login")
    public RespBean login(@Valid String username, String password) throws MyException {
        Admin admin = adminService.login(username, password);
        //登录成功之后，生成JWT发送给前端
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", admin.getId());
        map.put("username", admin.getUsername());
        String jwt = JwtUtils.generateJwt(map);
        return RespBean.ok("登录成功", jwt);
    }

    //注销
    @ResponseBody
    @GetMapping("/logout")
    public RespBean logout(HttpSession session) {
        //让session失效
        session.invalidate();

        //返回注销成功的消息
        //RespBean -> JSON -> 返回给了浏览器
        return RespBean.ok("退出成功");
    }


}
