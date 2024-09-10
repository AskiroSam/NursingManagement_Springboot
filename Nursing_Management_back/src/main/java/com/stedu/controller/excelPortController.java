package com.stedu.controller;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.stedu.bean.*;
import com.stedu.service.*;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
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
            dto.setHid(custom.getHid());
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
            dto.setSavatar(custom.getSavatar());
            dto.setDepartment(departmentMap.getOrDefault(custom.getDid(), "未知"));
            //dto.setFamily(familyMap.getOrDefault(custom.getFid(), "未知"));
            //dto.setExpend(expendMap.getOrDefault(custom.getEid(), "未知"));
            return dto;
        }).collect(Collectors.toList());

        // 将数据写入Excel
        writer.write(customDTOList, true);

        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=staffs.xlsx");

        // 获取输出流
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            // 将Excel内容写入输出流
            writer.flush(outputStream, true);
        } finally {
            // 确保writer关闭
            writer.close();
        }
    }

    //导出员工工资表
    @GetMapping("/exportStaffSalary")
    public void exportDataStaffSalary(@RequestParam(required = false) String sname,@RequestParam(required = false) String sgender,@RequestParam(required = false) String ssalary, HttpServletResponse response) throws IOException {
        // 创建ExcelWriter
        ExcelWriter writer = ExcelUtil.getWriter();

        // 查询主表数据
        List<Staff> staffList;
        if (StringUtils.isEmpty(sname) && StringUtils.isEmpty(sgender) && StringUtils.isEmpty(ssalary)) {
            staffList = staffService.selectAll();
        } else {
            staffList = staffService.selectByPage(sname, sgender, ssalary);
        }

        // 过滤掉离职员工（sstate = 2）
        staffList = staffList.stream().filter(staff -> staff.getSstate() != 2).collect(Collectors.toList());

        // 补充数据
        List<StaffDTO> customDTOList = staffList.stream().map(custom -> {
            StaffDTO dto = new StaffDTO();
            dto.setSid(custom.getSid());
            dto.setSno(custom.getSno());
            dto.setSname(custom.getSname());
            dto.setSsalary(String.valueOf(custom.getSsalary()));
            return dto;
        }).collect(Collectors.toList());

        // 过滤不需要的列，只保留指定的字段
        writer.addHeaderAlias("sid", "员工编号");   // 设置别名
        writer.addHeaderAlias("sno", "工号");      // 设置别名
        writer.addHeaderAlias("sname", "姓名");     // 设置别名
        writer.addHeaderAlias("ssalary", "薪资");   // 设置别名

        // 设置只输出设置了别名的字段
        writer.setOnlyAlias(true);

        // 将数据写入Excel
        writer.write(customDTOList, true);

        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=staffs.xlsx");

        // 获取输出流
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            // 将Excel内容写入输出流
            writer.flush(outputStream, true);
        } finally {
            // 确保writer关闭
            writer.close();
        }
    }

    //导入Custom
    @PostMapping("/importCustom")
    public ResponseEntity<String> importDataCustom(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("上传的文件为空");
        }

        // 输出文件信息用于调试
        System.out.println("文件名: " + file.getOriginalFilename());
        System.out.println("文件大小: " + file.getSize());
        System.out.println("内容类型: " + file.getContentType());

        try (InputStream inputStream = file.getInputStream()) {
            // 使用 Hutool 解析 Excel 文件
            List<CustomDTO> customDTOList = ExcelUtil.getReader(inputStream).readAll(CustomDTO.class);

            // 将 CustomDTO 列表转换为 Custom 实体类并保存
            List<Custom> customList = customDTOList.stream().map(dto -> {
                Custom custom = new Custom();
                custom.setCid(dto.getCid());
                custom.setCname(dto.getCname());
                custom.setCage(dto.getCage());
                custom.setCgender(dto.getCgender());
                custom.setCphone(dto.getCphone());
                custom.setCentrydate(dto.getCentrydate());
                custom.setCstate(dto.getCstate());
                custom.setCaddress(dto.getCaddress());
                custom.setDid(departmentService.getDepartmentId(dto.getDepartment())); // 将部门名称转换为 ID
                custom.setFid(familyService.getFamilyId(dto.getFamily())); // 将家属名称转换为 ID
                custom.setEid(expendService.getExpendId(dto.getExpend())); // 将消费等级名称转换为 ID
                custom.setHid(dto.getHid());
                return custom;
            }).collect(Collectors.toList());
            System.out.println(customList);

            customService.saveAll(customList); // 批量保存到数据库


            return ResponseEntity.ok("文件导入成功！");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("文件导入失败: 文件读取错误");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("文件导入失败: " + e.getMessage());
        }
    }

}
