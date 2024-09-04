package com.stedu;

import com.stedu.bean.Custom;
import com.stedu.bean.Department;
import com.stedu.mapper.CustomMapper;
import com.stedu.mapper.DepartmentMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class OlderSystemApplicationTests {
    @Autowired
    private CustomMapper customMapper;
    @Autowired
    private DepartmentMapper departmentMapper;

    @Test
    public void test01() {
        List<Custom> customs = customMapper.selectAll();
        System.out.println(customs);
    }

    @Test
    public void test02() {
        Custom custom = customMapper.selectByCid(1);
        System.out.println(custom);
    }

    @Test
    public void test03() {
        departmentMapper.selectAll()
                .stream()
                .forEach(System.out::println);
    }

    @Test
    public void test04() {
        List<Custom> customs = customMapper.selectByCname("李姥姥");
        System.out.println(customs);
    }

    @Test
    public void test05() {
        Custom custom = customMapper.selectByCid(1);
        System.out.println(custom);
    }

}
