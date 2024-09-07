package com.stedu.controller;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.stedu.bean.Custom;
import com.stedu.service.CustomService;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/excel")
public class excelPortController {
    @Autowired
    private CustomService customService;

    @GetMapping("/export")
    public void exportData(@RequestParam(required = false) String cname, HttpServletResponse response) throws IOException {
        // 创建ExcelWriter
        ExcelWriter writer = ExcelUtil.getWriter();
        // 存储导出的数据
        List<Custom> list;

        // 查询数据
        if (StringUtils.isEmpty(cname)) {
            // cname为空时，查询所有数据
            list = customService.selectAll();
        } else {
            // 根据cname查询数据
            list = customService.selectByPage(cname, null, null);
        }

        // 将数据写入Excel
        writer.write(list, true);

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
