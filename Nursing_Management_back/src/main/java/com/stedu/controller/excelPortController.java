package com.stedu.controller;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.stedu.bean.*;
import com.stedu.service.*;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/excel")
public class excelPortController {
    //Excel导入导出

    @Autowired
    private CustomService customService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private FamilyService familyService;

    @Autowired
    private ExpendService expendService;
    @Autowired
    private StaffService staffService;

    @GetMapping("/exportCustom")
    public void exportDataCustom(@RequestParam(required = false) String cname,@RequestParam(required = false) String cgender,@RequestParam(required = false) String caddress, HttpServletResponse response) throws IOException {
        // 创建ExcelWriter
        ExcelWriter writer = ExcelUtil.getWriter();

        // 查询主表数据
        List<Custom> customList;
        if (StringUtils.isEmpty(cname) && StringUtils.isEmpty(cgender) && StringUtils.isEmpty(caddress)) {
            customList = customService.selectAll();
        } else {
            customList = customService.selectByPage(cname, cgender, caddress);
        }

        // 加载关联表数据
        Map<Integer, String> departmentMap = departmentService.getDepartmentMap();
        Map<Integer, String> familyMap = familyService.getFamilyMap();
        Map<Integer, String> expendMap = expendService.getExpendMap();

        // 补充数据
        List<CustomDTO> customDTOList = customList.stream().map(custom -> {
            CustomDTO dto = new CustomDTO();
            dto.setCid(custom.getCid());
            dto.setCname(custom.getCname());
            dto.setCage(custom.getCage());
            dto.setCgender(custom.getCgender());
            dto.setCphone(custom.getCphone());
            dto.setCentrydate(custom.getCentrydate());
            dto.setCstate(custom.getCstate());
            dto.setCaddress(custom.getCaddress());
            dto.setDepartment(departmentMap.getOrDefault(custom.getDid(), "未知"));
            dto.setFamily(familyMap.getOrDefault(custom.getFid(), "未知"));
            dto.setExpend(expendMap.getOrDefault(custom.getEid(), "未知"));
            return dto;
        }).collect(Collectors.toList());

        // 将数据写入Excel
        writer.write(customDTOList, true);

        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=customs.xlsx");

        // 获取输出流
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            // 将Excel内容写入输出流
            writer.flush(outputStream, true);
        } finally {
            // 确保writer关闭
            writer.close();
        }
    }

    @GetMapping("/exportStaff")
    public void exportDataStaff(@RequestParam(required = false) String sname,@RequestParam(required = false) String sgender,@RequestParam(required = false) String ssalary, HttpServletResponse response) throws IOException {
        // 创建ExcelWriter
        ExcelWriter writer = ExcelUtil.getWriter();

        // 查询主表数据
        List<Staff> staffList;
        if (StringUtils.isEmpty(sname) && StringUtils.isEmpty(sgender) && StringUtils.isEmpty(ssalary)) {
            staffList = staffService.selectAll();
        } else {
            staffList = staffService.selectByPage(sname, sgender, ssalary);
        }

        // 加载关联表数据
        Map<Integer, String> departmentMap = departmentService.getDepartmentMap();
        //Map<Integer, String> familyMap = familyService.getFamilyMap();
        //Map<Integer, String> expendMap = expendService.getExpendMap();

        // 补充数据
        List<StaffDTO> customDTOList = staffList.stream().map(custom -> {
            StaffDTO dto = new StaffDTO();
            dto.setSid(custom.getSid());
            dto.setSno(custom.getSno());
            dto.setSname(custom.getSname());
            dto.setSage(custom.getSage());
            dto.setSgender(custom.getSgender());
            dto.setSentrydate(custom.getSentrydate());
            dto.setSsalary(String.valueOf(custom.getSsalary()));
            dto.setSstate(custom.getSstate());
            dto.setDepartment(departmentMap.getOrDefault(custom.getDid(), "未知"));
            //dto.setFamily(familyMap.getOrDefault(custom.getFid(), "未知"));
            //dto.setExpend(expendMap.getOrDefault(custom.getEid(), "未知"));
            return dto;
        }).collect(Collectors.toList());

        // 将数据写入Excel
        writer.write(customDTOList, true);

        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=customs.xlsx");

        // 获取输出流
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            // 将Excel内容写入输出流
            writer.flush(outputStream, true);
        } finally {
            // 确保writer关闭
            writer.close();
        }
    }
}
