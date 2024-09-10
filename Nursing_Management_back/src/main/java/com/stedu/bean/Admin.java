package com.stedu.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    private Integer id;

    @NotEmpty(message = "用户名不能为空")
    private String username;

    @JsonIgnore
    @NotEmpty(message = "密码不能为空")
    private String password;
}
