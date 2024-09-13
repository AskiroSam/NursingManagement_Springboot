package com.stedu.controller;


import cn.hutool.captcha.LineCaptcha;
import com.stedu.Util.JwtUtils;
import com.stedu.Util.RedisUtil;
import com.stedu.bean.Admin;
import com.stedu.bean.RespBean;
import com.stedu.exception.MyException;
import com.stedu.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

@CrossOrigin
@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private RedisUtil redisUtil;

    //登录
    @PostMapping("/login")
    public RespBean login(@Valid String username, String password, String captchaId, String captcha) throws MyException {
        //判断验证码 - 和Redis中保存验证码的文本对比
        String captchaRedis = (String)redisUtil.get(captchaId);
        if (captchaRedis == null || (captchaRedis != null && !captchaRedis.equalsIgnoreCase(captcha))) {
            return RespBean.error("验证码错误，请刷新后重新输入");
        } else {

        }

        Admin admin = adminService.login(username, password);
        //登录成功之后，生成JWT发送给前端
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", admin.getId());
        map.put("username", admin.getUsername());
        String jwt = JwtUtils.generateJwt(map);
        return RespBean.ok("登录成功", jwt);
    }

    //注销

    @GetMapping("/logout")
    public RespBean logout(HttpSession session) {
        //让session失效
        session.invalidate();

        //返回注销成功的消息
        //RespBean -> JSON -> 返回给了浏览器
        return RespBean.ok("退出成功");
    }

    //验证码
    @GetMapping("/captcha")
    public RespBean captcha() throws IOException {
        //生成验证码对象
        LineCaptcha captcha = new LineCaptcha(120, 38, 4, 20);
        //获取验证码的文本
        String code = captcha.getCode();
        //生成唯一的ID
        String captchaId = UUID.randomUUID().toString().replace("-", "");
        //获取验证码的文本保存在Redis中
        redisUtil.set(captchaId, code, 60);

        //Base64图片 - 字符串(前端拿到后可以转换为图片)
        String captchaImageBase64Data = captcha.getImageBase64Data();
        //将captchaId和验证码的图片发送给浏览器
        HashMap<String, Object> map = new HashMap<>();
        map.put("captchaId", captchaId);
        map.put("captchaImageBase64Data", captchaImageBase64Data);

        return  RespBean.ok("", map);

    }
}
