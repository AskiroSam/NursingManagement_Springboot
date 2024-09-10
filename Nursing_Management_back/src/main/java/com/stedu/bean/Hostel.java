package com.stedu.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hostel {
    private Integer hid;

    @NotNull(message = "宿舍名不能为空")
    @Length(max = 4, message = "宿舍名最大长度不能超过4位")
    private String hno;

    private Integer did;

    private Department department;

    private Integer dnumber;

    private Integer cid;


    private Custom custom;


}
