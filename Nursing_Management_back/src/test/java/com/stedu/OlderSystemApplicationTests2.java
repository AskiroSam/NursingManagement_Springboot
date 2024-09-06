package com.stedu;


import com.stedu.Util.JwtUtils;
import com.stedu.service.AdminService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class OlderSystemApplicationTests2 {
    @Autowired
    private AdminService adminService;
    @Test
    public void test01() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("username", "admin");
        map.put("age", 20);

        //使用JWT工具类生成JWT String   JwtUtils.generateJwt（）
        String jwtStr = JwtUtils.generateJwt(map);
        System.out.println(jwtStr);
    }

    @Test
    public void test02() {
        //解析JWT   JwtUtils.parseJwtToMap（）
        Map<String, Object> map = JwtUtils.parseJwtToMap("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwYXlsb2FkIjp7ImFnZSI6MjAsInVzZXJuYW1lIjoiYWRtaW4ifSwiZXhwIjoxNzI1NjI1MDgxfQ.g3mO-LsjfqLN3ZCehJTdk66i27FDQxmu_NkcBapiA2g");
        System.out.println(map.get("username"));
        System.out.println(map.get("age"));
    }

    @Test
    public void test03() {
        adminService.selectAgeCount();
    }

}
