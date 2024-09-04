package com.stedu.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    private Integer did;

    private String dname;

    private String dlocation;

    private Integer personnum;

    //一对多查询出系部的客人
    private List<Custom> customList;

    //一对多查询出系部的员工
    private List<Staff> staffList;

}
