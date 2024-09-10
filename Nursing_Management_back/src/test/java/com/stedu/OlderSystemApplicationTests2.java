package com.stedu;


import com.stedu.Util.JwtUtils;
import com.stedu.bean.Custom;
import com.stedu.service.AdminService;
import com.stedu.service.CustomService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
class OlderSystemApplicationTests2 {
    @Autowired
    private AdminService adminService;
    @Autowired
    private CustomService customService;

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
        Map<String, Object> map = JwtUtils.parseJwtToMap("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwYXlsb2FkIjp7ImFnZSI6MjAsInVzZXJuYW1lIjoiYWRtaW4ifSwiZXhwIjoxNzI1NzA2Mjc5fQ.B31AWILoFoF9BRDOk2sDSzwjGPO6z7YCCEQ0jc9wvFg");
        System.out.println(map.get("username"));
        System.out.println(map.get("age"));
    }

    @Test
    public void test03() {
        adminService.selectAgeCount();
    }

    @Test
    public void testSaveSingleData() {
        Custom custom = new Custom();
        custom.setCid(69);
        custom.setCname("张三");
        custom.setCage(30);
        custom.setCgender("男");
        custom.setCphone("1234567890");
        custom.setCentrydate(new Date());
        custom.setCstate(1);
        custom.setCaddress("某地");
        custom.setDid(1);
        custom.setFid(2);
        custom.setEid(3);

        List<Custom> customList = Collections.singletonList(custom);
        customService.saveAll(customList);
    }

}
