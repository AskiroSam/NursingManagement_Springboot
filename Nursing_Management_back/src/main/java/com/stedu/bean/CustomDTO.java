package com.stedu.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomDTO {
    private Integer cid;
    private String cname;
    private Integer cage;
    private String cgender;
    private String cphone;
    private Date centrydate;
    private Integer cstate;
    private String caddress;
    private String department;
    private String family;
    private String expend;
    private Integer hid;
}
