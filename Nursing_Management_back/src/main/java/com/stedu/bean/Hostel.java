package com.stedu.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hostel {
    private Integer hid;

    private String hno;

    private Integer did;

    private Department department;

    private Integer dnumber;

    private Integer cid;

    private Custom custom;


}
