package com.stedu.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    private Integer did;

    @NotEmpty(message = "部门名称不能为空")
    @Length(max = 10, message = "系部名称最大长度不得超过10")
    private String dname;

    @NotEmpty
    @Length(max = 40, message = "系部位置长度不得超过40")
    private String dlocation;

    private Integer personnum;

    //一对多查询出系部的客人
    private List<Custom> customList;

    //一对多查询出系部的员工
    private List<Staff> staffList;

}
