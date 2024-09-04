package com.stedu.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Custom {
    private Integer cid;

    private String cname;

    private Integer cage;

    private String cgender;

    private String cphone;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date centrydate;

    private Integer cstate;

    private String caddress;

    //院系编号
    private Integer did;
    //院系信息
    private Department department;

    //家属编号
    private Integer fid;
    //家属信息
    private Family family;

    //护理等级
    private Integer eid;
    //护理花费
    private Expend expend;

}
