package com.stedu.controller;


import com.stedu.bean.Admin;
import com.stedu.bean.RespBean;
import com.stedu.exception.MyException;
import com.stedu.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;

    //登录
    //@ResponseBody
    //@PostMapping("/login")
    //public RespBean login(String username, String password) throws MyException {
    //    Admin admin = adminService.login(username, password);
    //}
}
