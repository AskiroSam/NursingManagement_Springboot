package com.stedu.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Staff {
    private Integer sid;

    private String sno;

    private String sname;

    private Integer sage;

    private String sgender;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date sentrydate;

    private BigDecimal ssalary;

    private Integer sstate;

    //部门编号
    private Integer did;
    //部门信息
    private Department department;

    private String savatar;
}
