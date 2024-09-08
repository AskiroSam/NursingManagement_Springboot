package com.stedu.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Expend {
    private Integer eid;

    private String egrade;

    private BigDecimal esalary;

    private Integer enumber;

    private Long eincome;

    //一对多查询出客户费用的信息
    private List<Custom> customList;
}
