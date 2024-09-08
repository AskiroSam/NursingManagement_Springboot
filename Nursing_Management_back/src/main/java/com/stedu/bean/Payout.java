package com.stedu.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payout {
    private Integer pid;

    private Integer pnumber;

    private Long pout;

    //一对多查询出员工费用的信息
    private List<Staff> staffList;

}
