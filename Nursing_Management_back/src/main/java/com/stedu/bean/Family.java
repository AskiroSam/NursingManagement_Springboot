package com.stedu.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Family {
    private Integer fid;

    private String fname;

    private Integer fage;

    private String fgender;

    private String fphone;

    private Integer cid;
    private Custom custom;
}
