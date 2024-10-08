package com.stedu;

import com.stedu.bean.Custom;
import com.stedu.bean.Department;
import com.stedu.bean.Staff;
import com.stedu.mapper.*;
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
    @Autowired
    private TravelMapper travelMapper;
    @Autowired
    private StaffMapper staffMapper;
    @Autowired
    private HostelMapper hostelMapper;

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
        List<Staff> staffs = staffMapper.selectByPage(null, null, "3500");
        System.out.println(staffs);
    }

    @Test
    public void test05() {
        Custom custom = customMapper.selectByCid(1);
        System.out.println(custom);
    }

    @Test
    public void test06() {
        Integer[] cids = {1, 2, 3};
        travelMapper.insertTidAndCid(1, cids);
    }

    @Test
    public void test07() {
        Integer[] sids = {1, 2, 3};
        travelMapper.insertTidAndSid(2, sids);
    }

    @Test
    public void test08() {
        travelMapper.deleteTidAndSidByTid(2);
    }

    @Test
    public void test09() {
        Integer[] cids = {1, 2, 3};
        hostelMapper.insertHidAndCid(2, cids);
    }

    @Test
    public void test10() {
        hostelMapper.deleteCidAndHidByHid(2);
    }

}
