package com.stedu.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffDTO {
    private Integer sid;
    private String sno;
    private String sname;
    private Integer sage;
    private String sgender;
    private Date sentrydate;
    private String ssalary;
    private Integer sstate;
    private String department;
    private String savatar;
}
