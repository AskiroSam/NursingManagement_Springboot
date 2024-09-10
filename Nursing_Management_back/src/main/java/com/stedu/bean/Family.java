package com.stedu.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Family {
    private Integer fid;

    @NotNull(message = "家属名不能为空")
    @Length(max = 5, message = "姓名最大长度不能超过5位")
    private String fname;

    @NotNull(message = "年龄不能为空")
    @Min(value = 0, message = "年龄必须是非负整数")
    @Max(value = 999, message = "年龄必须是3位整数")
    private Integer fage;

    private String fgender;

    @NotNull(message = "手机号不能为空")
    @Pattern(regexp = "\\d{11}", message = "手机号必须是11位数字")
    private String fphone;

    private Integer cid;

    //@NotNull(message = "对应的亲人不能为空")
    //@Valid
    private Custom custom;
}
