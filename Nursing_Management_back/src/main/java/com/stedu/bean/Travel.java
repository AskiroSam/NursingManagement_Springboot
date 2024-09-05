package com.stedu.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Travel {
    private Integer tid;

    private String tlocation;

    @JsonFormat(pattern = "HH:mm", timezone = "GMT+8")
    private Date tstart;

    @JsonFormat(pattern = "HH:mm", timezone = "GMT+8")
    private Date tend;

    private Integer tprogress;

    private String tdexcription;
}
