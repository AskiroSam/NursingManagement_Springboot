package com.stedu.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.apache.poi.ss.formula.functions.T;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Custom {
    private Integer cid;

    @NotNull(message = "姓名不能为空")
    @Length(max = 5, message = "姓名最大长度不能超过5位")
    private String cname;

    @NotNull(message = "年龄不能为空")
    @Min(value = 0, message = "年龄必须是非负整数")
    @Max(value = 999, message = "年龄必须是3位整数")
    private Integer cage;

    private String cgender;

    @NotNull(message = "手机号不能为空")
    @Pattern(regexp = "\\d{11}", message = "手机号必须是11位数字")
    private String cphone;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date centrydate;

    private Integer cstate;

    @NotNull(message = "家庭住不能为空")
    @Length(max = 40, message = "住址长度不得超过40")
    private String caddress;

    @NotNull(message = "系部不能为空")
    @Valid
    //院系编号
    private Integer did;
    //院系信息
    private Department department;

    //@NotNull(message = "家属不能为空")
    //@Valid
    //家属编号
    private Integer fid;
    //家属信息
    private Family family;

    //护理等级
    private Integer eid;
    //护理花费
    private Expend expend;

    //宿舍
    private Integer hid;

    private Hostel hostel;

}
