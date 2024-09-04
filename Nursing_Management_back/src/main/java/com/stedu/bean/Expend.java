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

    private List<Custom> customList;
}
