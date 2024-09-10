package com.stedu.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Staff {
    private Integer sid;

    @NotNull(message = "工号不能为空")
    @Pattern(regexp = "\\d{5}", message = "工号必须是5位数字")
    private String sno;

    @NotNull(message = "姓名不能为空")
    @Length(max = 5, message = "姓名最大长度不能超过5位")
    private String sname;

    @NotNull(message = "年龄不能为空")
    @Min(value = 0, message = "年龄必须是非负整数")
    @Max(value = 999, message = "年龄必须是3位整数")
    private Integer sage;

    @NotNull(message = "性别不能为空")
    private String sgender;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @NotNull(message = "入职日期不能为空")
    private Date sentrydate;


    private BigDecimal ssalary;

    private Integer sstate;

    @NotNull(message = "院系不能为空")
    //部门编号
    private Integer did;
    //部门信息
    private Department department;

    @NotNull(message = "头像不能为空")
    private String savatar;
}
